package com.fallframework.platform.test.starters.ehcache;

import com.fallframework.platform.starter.api.response.ResponseResult;
import com.fallframework.platform.starter.cache.ehcache.util.EHCacheUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhuangpf
 */
@RestController
@RequestMapping("/ehcache")
public class EhCacheController {

	private final static EHCacheUtil ehcacheUtil = new EHCacheUtil("ehcache-01.xml");

	@RequestMapping("/savecache")
	public ResponseResult saveCache(String name, String key) {
		ehcacheUtil.put(name, key, 1);
		return ResponseResult.success();
	}

	@RequestMapping("/get")
	public ResponseResult get(String name, String key) {
		Object o = ehcacheUtil.get(name, key);
		return ResponseResult.success(o);
	}

	@RequestMapping("/getname")
	public ResponseResult getname(String name, String key) {
		Map<Object, Object> map = ehcacheUtil.get(name);
		return ResponseResult.success(map);
	}
	
	@RequestMapping("/remove")
	public ResponseResult remove(String name, String key) {
		ehcacheUtil.remove(name, key);
		return ResponseResult.success();
	}

	@RequestMapping("/clearcache")
	public ResponseResult clearCache(String name, String key) {
		ehcacheUtil.clearCache(name);
		return ResponseResult.success();
	}

}
