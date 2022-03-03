//package com.fallframework.platform.test.starters.quartz;
//
//import com.fallframework.platform.starter.api.response.ResponseResult;
//import com.fallframework.platform.starter.task.quartz.model.JobRequest;
//import com.fallframework.platform.starter.task.quartz.service.JobService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author zhuangpf
// */
//@RestController
//@RequestMapping("/quartz")
//public class QuartzController {
//
//	@Autowired
//	private JobService jobService;
//
//	@RequestMapping("/savejob")
//	public ResponseResult saveJob(@RequestBody JobRequest request) {
//		return jobService.saveJob(request);
//	}
//
//	@RequestMapping("/deletejob")
//	public ResponseResult deleteJob(@RequestBody JobRequest request) {
//		return jobService.deleteJob(request);
//	}
//
//}
