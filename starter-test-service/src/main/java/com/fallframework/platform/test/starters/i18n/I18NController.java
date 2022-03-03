package com.fallframework.platform.test.starters.i18n;

import com.fallframework.platform.starter.api.response.ResponseResult;
import com.fallframework.platform.starter.i18n.entity.I18nResource;
import com.fallframework.platform.starter.i18n.model.I18nResourceRequest;
import com.fallframework.platform.starter.i18n.service.I18nResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@RestController
@RequestMapping("/i18n")
public class I18NController {

	@Autowired
	private I18nResourceService i18nResourceService;

	@RequestMapping("/refresh")
	public ResponseResult refresh() {
		ResponseResult responseResult = i18nResourceService.refreshI18nResourceCache();
		return responseResult;
	}

	@RequestMapping("/save")
	public ResponseResult save(@RequestBody I18nResource i18nResource) {
		return i18nResourceService.insert(i18nResource);
	}

	@RequestMapping("/list")
	public ResponseResult list(@RequestBody I18nResourceRequest request) {
		return i18nResourceService.list(request);
	}

}
