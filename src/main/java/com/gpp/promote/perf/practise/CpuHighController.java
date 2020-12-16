package com.gpp.promote.perf.practise;

import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.util.*;

/**
 * @author ppgao
 * @date 2020-12-14 13:49
 */
@Slf4j
@RestController
@RequestMapping(value = "v1")
public class CpuHighController {

    @GetMapping(value = "cpu/high")
    public String cpu() {
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

    @GetMapping(value = "mem/high")
    public String mem(@RequestParam(value = "size", required = true) int size) {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    List<Map> list = new ArrayList<>();
                    while (true) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("aaa", "nnnn");
                        list.add(map);
                        if (list.size() > size) {
                            log.info("the thread clear memory");
                            break;
                        } else {
                            log.info("the thread is running, obj size: {}", list.size());
                        }
                    }
                }
            }
        }.start();
        return "SUCCESS";
    }

    public static void main(String[] args) {
        // 虚拟机级内存情况查询
        long vmFree = 0;
        long vmUse = 0;
        long vmTotal = 0;
        long vmMax = 0;
        int byteToMb = 1024 * 1024;
        Runtime rt = Runtime.getRuntime();
        vmTotal = rt.totalMemory() / byteToMb;
        vmFree = rt.freeMemory() / byteToMb;
        vmMax = rt.maxMemory() / byteToMb;
        vmUse = vmTotal - vmFree;
        System.out.println("JVM内存已用的空间为：" + vmUse + " MB");
        System.out.println("JVM内存的空闲空间为：" + vmFree + " MB");
        System.out.println("JVM总内存空间为：" + vmTotal + " MB");
        System.out.println("JVM总内存空间为：" + vmMax + " MB");

        System.out.println("======================================");

        System.out.println("+++++++++++++++++测试HashMap开始++++++++++++++++++");
        List<Map> list = new ArrayList<>();
        while (true) {
            HashMap<String, String> map = new HashMap<>();
            map.put("aaa", "nnnn");
            list.add(map);
            if (list.size() > 10000000) {
                break;
            } else {
            }
        }
        rt = Runtime.getRuntime();
        vmTotal = rt.totalMemory() / byteToMb;
        vmFree = rt.freeMemory() / byteToMb;
        vmMax = rt.maxMemory() / byteToMb;
        vmUse = vmTotal - vmFree;
        System.out.println("JVM内存已用的空间为：" + vmUse + " MB");
        System.out.println("JVM内存的空闲空间为：" + vmFree + " MB");
        System.out.println("JVM总内存空间为：" + vmTotal + " MB");
        System.out.println("JVM总内存空间为：" + vmMax + " MB");


        System.out.println("======================================");
        // 操作系统级内存情况查询
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        String os = System.getProperty("os.name");
        long physicalFree = osmxb.getFreePhysicalMemorySize() / byteToMb;
        long physicalTotal = osmxb.getTotalPhysicalMemorySize() / byteToMb;
        long physicalUse = physicalTotal - physicalFree;
        System.out.println("操作系统的版本：" + os);
        System.out.println("操作系统物理内存已用的空间为：" + physicalFree + " MB");
        System.out.println("操作系统物理内存的空闲空间为：" + physicalUse + " MB");
        System.out.println("操作系统总物理内存：" + physicalTotal + " MB");

        // 获得线程总数
        ThreadGroup parentThread;
        int totalThread = 0;
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
                .getParent() != null; parentThread = parentThread.getParent()) {
            totalThread = parentThread.activeCount();
        }
        System.out.println("获得线程总数:" + totalThread);
    }
}
