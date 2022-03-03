package com.fallframework.platform.test.starters.data;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@RestController
@RequestMapping("/data")
public class DataController {

//	@Autowired
//	private DataSource dataSource;

	@RequestMapping("/test01")
	public void test01() {
		// 数据源
//		System.out.println(dataSource);
		System.out.println("test01");
	}

}
