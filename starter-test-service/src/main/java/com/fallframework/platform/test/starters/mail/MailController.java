package com.fallframework.platform.test.starters.mail;

import com.fallframework.platform.starter.api.response.ResponseResult;
import com.fallframework.platform.starter.mail.entity.MailSenderConfig;
import com.fallframework.platform.starter.mail.entity.MailTemplate;
import com.fallframework.platform.starter.mail.model.MailSendInfoRequest;
import com.fallframework.platform.starter.mail.service.MailSenderConfigService;
import com.fallframework.platform.starter.mail.service.MailTemplateService;
import com.fallframework.platform.starter.mail.service.PlatformMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@RestController
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private PlatformMailSender platformMailSender;
	@Autowired
	private MailTemplateService mailTemplateService;
	@Autowired
	private MailSenderConfigService mailSenderConfigService;

	/**
	 * 简单邮件
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/sendsimpleemail")
	public ResponseResult sendSimpleEmail(@RequestBody MailSendInfoRequest request) {
		return platformMailSender.sendSimpleEmail(request);
	}

	/**
	 * 带附件邮件
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/sendmimemsgemail")
	public ResponseResult sendMimeMsgEmail(@RequestBody MailSendInfoRequest request) {
		return platformMailSender.sendMimeMsgEmail(request);
	}

	@RequestMapping("/sendinlinemail")
	public ResponseResult sendInlineMail(@RequestBody MailSendInfoRequest request){
		return platformMailSender.sendInlineMail(request);
	}

	@RequestMapping("/sendtemplateemail")
	public ResponseResult sendTemplateEmail(@RequestBody MailSendInfoRequest request){
		return platformMailSender.sendTemplateEmail(request);
	}

	@RequestMapping("/savetemplate")
	public ResponseResult saveTemplate(@RequestBody MailTemplate mailTemplate) {
		return mailTemplateService.insert(mailTemplate);
	}

	@RequestMapping("/saveconfig")
	public ResponseResult saveConfig(@RequestBody MailSenderConfig config) {
		return mailSenderConfigService.insert(config);
	}

}
