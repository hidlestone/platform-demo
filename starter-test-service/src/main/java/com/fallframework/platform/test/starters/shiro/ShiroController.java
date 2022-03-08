//package com.fallframework.platform.test.starters.shiro;
//
//import com.fallframework.platform.starter.api.response.ResponseResult;
//import com.fallframework.platform.starter.cache.redis.util.RedisUtil;
//import com.fallframework.platform.starter.core.util.EncryptionUtil;
//import com.fallframework.platform.starter.rbac.entity.User;
//import com.fallframework.platform.starter.rbac.model.TokenTypeEnum;
//import com.fallframework.platform.starter.shiro.constant.ShiroStarterConstant;
//import com.fallframework.platform.starter.shiro.util.JWTUtil;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.LockedAccountException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authz.annotation.RequiresAuthentication;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author zhuangpf
// */
//@RestController
//@RequestMapping("/shiro")
//public class ShiroController {
//
//	@Autowired
//	private JWTUtil jwtUtill;
//	@Autowired
//	private RedisUtil redisUtil;
//
//	@GetMapping("/login")
//	public ResponseResult login(User user, HttpServletResponse response) {
//		Subject currentUser = SecurityUtils.getSubject();
//		String md5pwd = EncryptionUtil.encryptMD5(user.getPassword());
//		try {
//			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getAccount(), md5pwd);
//			currentUser.login(usernamePasswordToken);
//		} catch (UnknownAccountException e) {
//			return ResponseResult.fail("account or passwordis not correct.");
//		} catch (LockedAccountException e) {
//			return ResponseResult.fail("account is disable, contact the admin.");
//		}
//		User curUser = (User) currentUser.getPrincipal();
//		String accesstoken = jwtUtill.createToken(curUser, TokenTypeEnum.ACCESSTOKEN);
//		String refreshtoken = jwtUtill.createToken(curUser, TokenTypeEnum.REFRESHTOKEN);
//		redisUtil.set(ShiroStarterConstant.CACHE_KEY_SHIRO_ACCESSTOKEN + curUser.getId(), accesstoken);
//		redisUtil.set(ShiroStarterConstant.CACHE_KEY_SHIRO_REFRESHTOKEN + curUser.getId(), refreshtoken);
//		Map map = new HashMap();
//		map.put("accesstoken", accesstoken);
//		map.put("refreshtoken", refreshtoken);
//		response.setHeader("accesstoken", accesstoken);
//		response.setHeader("refreshtoken", refreshtoken);
//		return ResponseResult.success(map);
//	}
//
////	@RequiresPermissions("shiro:test01")
//	@GetMapping("/test01")
////	@RequiresAuthentication
//	public ResponseResult test01() {
//		System.out.println("test01");
//		Subject currentUser = SecurityUtils.getSubject();
//		System.out.println("test01");
//		return ResponseResult.success();
//	}
//}
