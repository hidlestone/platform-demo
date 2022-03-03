package com.fallframework.platform.test.starters.file;

import com.fallframework.platform.starter.api.response.ResponseResult;
import com.fallframework.platform.starter.file.model.FileGroupUploadRequest;
import com.fallframework.platform.starter.file.service.FileProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhuangpf
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {

	@Autowired
	private FileProcessService fileProcessService;

	@RequestMapping("/uploadfilegroup")
	public ResponseResult uploadFilegroup(FileGroupUploadRequest request) {
		fileProcessService.uploadFileGroup(request);
		return ResponseResult.success();
	}

	@RequestMapping("/download")
	public ResponseResult download(@RequestParam("id") Long id, HttpServletResponse response) {
		fileProcessService.downloadFile(id, response);
		return ResponseResult.success();
	}

}
