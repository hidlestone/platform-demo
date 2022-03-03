package com.fallframework.platform.test.starters.file;

import com.fallframework.platform.starter.api.response.ResponseResult;
import com.fallframework.platform.starter.file.entity.FileGroup;
import com.fallframework.platform.starter.file.model.FileGroupRequest;
import com.fallframework.platform.starter.file.model.FileInfoRequest;
import com.fallframework.platform.starter.file.service.FileGroupService;
import com.fallframework.platform.starter.file.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileGroupService fileGroupService;
	@Autowired
	private FileInfoService fileInfoService;

	/**
	 * 保存文件组及明细
	 */
	@RequestMapping("/savegroupandinfolist")
	public ResponseResult saveGroupAndInfoList(@RequestBody FileGroupRequest fileGroup) {
		return fileGroupService.saveGroupAndInfoList(fileGroup);
	}
	
	@RequestMapping("/groupsave")
	public ResponseResult saveGroup(@RequestBody FileGroup fileGroup) {
		return fileGroupService.insert(fileGroup);
	}

	@RequestMapping("/grouplist")
	public ResponseResult grouplist(@RequestBody FileGroupRequest request) {
		return fileGroupService.list(request);
	}

	@RequestMapping("/fileInfoList")
	public ResponseResult fileInfoList(@RequestBody FileInfoRequest request) {
		return fileInfoService.list(request);
	}

}
