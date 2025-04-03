package com.sunkz.starters.sidecar;

import com.sunkz.common.util.FileUtil;
import com.sunkz.common.util.ShellUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sidecar")
@RestController
@Configuration
public class SidecarAutoConfiguration {

    @PostMapping("/run")
    public void run(@RequestParam("url") String url,
                    @RequestParam("name") String name) {
        FileUtil.downloadUrl(url, name);
        ShellUtil.runJar(name);
    }

    @PostMapping("/stop")
    public void stop(@RequestParam("name") String name) {
        ShellUtil.stopJar(name);
        cn.hutool.core.io.FileUtil.del(name);
    }

}
