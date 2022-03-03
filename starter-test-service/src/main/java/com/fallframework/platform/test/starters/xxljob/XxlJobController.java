//package com.fallframework.platform.test.starters.xxljob;
//
//import com.fallframework.platform.starter.api.response.ResponseResult;
//import com.fallframework.platform.starter.task.xxljob.entity.XxlJobInfo;
//import com.fallframework.platform.starter.task.xxljob.service.JobXxlService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author zhuangpf
// */
//@RestController
//@RequestMapping("/xxljob")
//public class XxlJobController {
//
//	@Autowired
//	private JobXxlService jobXxlService;
//
//	@PostMapping("/savejob")
//	public ResponseResult saveJob(@RequestBody XxlJobInfo xxlJobInfo) {
//		return jobXxlService.saveJob(xxlJobInfo);
//	}
//
//
//}
