package com.lbs.nettyserver.utils.sysutils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lbs.nettyserver.handler.ChannelCommunicateHandler;
/**
 * 并发测试工具类
 * @author will
 *
 */
public class ExecutorServiceUtils {
    private static final Log LOGGER = LogFactory.getLog(ChannelCommunicateHandler.class);
    private static final int POOL_SIZE = 10;
    private static final ExecutorService EXECUTOR_SERVICE;

    public ExecutorServiceUtils() {
    }

    public static void execute(Runnable runnable) {
        EXECUTOR_SERVICE.execute(runnable);
    }

    public static void shutdown() {
        if(EXECUTOR_SERVICE != null) {
            EXECUTOR_SERVICE.shutdown();
            LOGGER.info("ExecutorServiceUtils# 等待任务完成后关闭所有线程!");
        }

    }

    public static void shutdownNow() {
        if(EXECUTOR_SERVICE != null) {
            EXECUTOR_SERVICE.shutdownNow();
            LOGGER.info("ExecutorServiceUtils# 关闭所有线程!");
        }

    }

    static {
        int cpuNums = Runtime.getRuntime().availableProcessors();
        int nThreads = cpuNums * 5;
//        LOGGER.info("当前系统的CPU核数为:{},单核开启线程数为:{},线程池总大小为:{}", new Object[]{Integer.valueOf(cpuNums), Integer.valueOf(5), Integer.valueOf(nThreads)});
        EXECUTOR_SERVICE = Executors.newFixedThreadPool(nThreads);
    }
}