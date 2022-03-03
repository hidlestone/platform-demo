package com.fallframework.platform.test.starters.config;

import com.alibaba.fastjson.JSON;
import com.fallframework.platform.starter.api.response.ResponseResult;
import com.fallframework.platform.starter.config.entity.SysParamGroup;
import com.fallframework.platform.starter.config.entity.SysParamItem;
import com.fallframework.platform.starter.config.model.SysParamGroupRequest;
import com.fallframework.platform.starter.config.service.PlatformSysParamUtil;
import com.fallframework.platform.starter.config.service.SysParamGroupService;
import com.fallframework.platform.starter.config.service.SysParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhuangpf
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

	@Autowired
	private PlatformSysParamUtil platformSysParamUtil;
	@Autowired
	private SysParamGroupService sysParamGroupService;
	@Autowired
	private SysParamItemService sysParamItemService;

	@RequestMapping("/refreshsysparamcache")
	public ResponseResult refreshSysParam() {
		ResponseResult responseResult = platformSysParamUtil.refreshSysParamCache();
		return responseResult;
	}

	@PostMapping("savesysparamgroup")
	public ResponseResult saveSysParamGroup(@RequestBody SysParamGroupRequest request) {
		SysParamGroup sysParamGroup = JSON.parseObject(JSON.toJSONString(request), SysParamGroup.class);
		boolean a = sysParamGroupService.save(sysParamGroup);
		List<SysParamItem> sysParamItemList = JSON.parseArray(JSON.toJSONString(request.getSysParamItemList()), SysParamItem.class);
		boolean b = sysParamItemService.saveBatch(sysParamItemList);
		return ResponseResult.success();
	}

	@PostMapping("deletesysparamgroup")
	public ResponseResult deleteSysParamGroup(@RequestBody String code) {
		ResponseResult delete = sysParamGroupService.delete(code);
		return ResponseResult.success();
	}

	@PostMapping("selectgroup")
	public ResponseResult selectgroup(@RequestBody String code) {
		return sysParamGroupService.select(code);
	}
}
