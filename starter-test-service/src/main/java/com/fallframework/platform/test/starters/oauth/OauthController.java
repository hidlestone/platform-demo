package com.fallframework.platform.test.starters.oauth;

import com.fallframework.platform.starter.api.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {

	//	@PreAuthorize("hasAnyRole('ADMIN','DBA','USER')")    //三个都行
//	@Secured("ROLE_ADMIN")//访问此方法需要ADMIN角色
	@GetMapping("/admin/hello")
	public String hello2() {
		return "admin";
	}

	//	@PreAuthorize("hasRole('ADMIN') and hasRole('DBA')")  //访问此方法需要ADMIN且DBA
	@GetMapping("/db/hello")
	public String hello3() {
		return "db";
	}

	//	@PreAuthorize("hasAnyRole('ADMIN','DBA','USER')")    //三个都行
	@GetMapping("/user/hello")
	public String hello4() {
		return "user";
	}

	@GetMapping("/test")
	public ResponseResult test() {
		System.out.println("test---");
		return ResponseResult.success();
	}

}
