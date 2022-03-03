//package com.fallframework.platform.test.starters.quartz;
//
//import lombok.extern.slf4j.Slf4j;
//import org.quartz.DisallowConcurrentExecution;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//
//import java.util.Date;
//
///**
// * @author zhuangpf
// */
//@DisallowConcurrentExecution
//@Slf4j
//public class TestJob implements Job {
//
//	@Override
//	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//		log.info("【定时任务】：" + new Date() + "");
//	}
//	
//}
