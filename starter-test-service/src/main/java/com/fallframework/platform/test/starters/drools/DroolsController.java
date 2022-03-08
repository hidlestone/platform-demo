package com.fallframework.platform.test.starters.drools;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@RestController
@RequestMapping("/drools")
public class DroolsController {

	@Autowired
	private KieSession session;

	@RequestMapping("/test01")
	public void test01() {
		People people = new People();
		people.setName("YC");
		people.setSex(1);
		people.setDrlType("people");
		session.insert(people);//插入
		session.fireAllRules();//执行规则
		System.out.println("test01");
	}

}
