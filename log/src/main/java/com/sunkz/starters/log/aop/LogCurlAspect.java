package com.sunkz.starters.log.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Component
@Slf4j
public class LogCurlAspect {

    @Around("@annotation(com.sunkz.starters.log.annotation.LogCurl)")
    public Object logCurl(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取 HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 构建 cURL 命令
        StringBuilder curlCommand = new StringBuilder("curl ");

        // 请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            curlCommand.append("-H '").append(headerName).append(": ").append(headerValue).append("' ");
        }

        // 请求方法
        curlCommand.append("-X ").append(request.getMethod()).append(" ");

        // 请求参数（GET 参数附加到 URL，POST 参数放在 body）
        String queryString = request.getQueryString();
        String url = request.getRequestURL().toString();
        if (!StringUtils.isEmpty(queryString)) {
            url += "?" + queryString;
        }
        curlCommand.append("'").append(url).append("'");

        // 请求体（仅 POST/PUT 等有 body 的请求）
        if (isBodyMethod(request.getMethod())) {
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof String) {
                    curlCommand.append(" -d '").append(escapeShellChars((String) arg)).append("'");
                }
            }
        }

        // 打印日志
        log.info("Generated cURL command: {}", curlCommand);

        // 继续执行原方法
        return joinPoint.proceed();
    }

    private boolean isBodyMethod(String method) {
        return HttpMethod.POST.matches(method) ||
                HttpMethod.PUT.matches(method) ||
                HttpMethod.PATCH.matches(method);
    }

    // 处理 shell 特殊字符转义（如单引号）
    private String escapeShellChars(String str) {
        return str.replace("'", "'\\''");
    }

}
