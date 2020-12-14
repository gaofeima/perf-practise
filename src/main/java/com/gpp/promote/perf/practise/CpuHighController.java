package com.gpp.promote.perf.practise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ppgao
 * @date 2020-12-14 13:49
 */
@Slf4j
@RestController
@RequestMapping(value = "v1")
public class CpuHighController {

    @GetMapping(value = "cpu/high")
    public String index() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    log.info("the thread is running");
                }
            }
        }.start();
        return "SUCCESS";
    }
}
