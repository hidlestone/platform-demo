package com.fallframework.platform.test.starters.rabbit;

import com.alibaba.fastjson.JSON;
import com.fallframework.platform.starter.api.response.ResponseResult;
import com.fallframework.platform.starter.i18n.entity.I18nResource;
import com.fallframework.platform.starter.mq.entity.MqTraceLog;
import com.fallframework.platform.starter.mq.model.StageEnum;
import com.fallframework.platform.starter.mq.service.MqTraceLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author zhuangpf
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitController.class);
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private MqTraceLogService mqTraceLogService;

	/**
	 * 测试发送
	 */
	@RequestMapping("/testsendmqracelog")
	public ResponseResult testsendmqracelog(@RequestBody MqTraceLog mqTraceLog) {
		LOGGER.info("请求的数据：" + JSON.toJSONString(mqTraceLog));
		// 记录mq消息到数据库
		mqTraceLog.setStage(StageEnum.DELIVERING);
		mqTraceLog.setPublishTime(new Date());
		mqTraceLogService.insert(mqTraceLog);
		CorrelationData correlationData = new CorrelationData();
		correlationData.setId(mqTraceLog.getId().toString());
		rabbitTemplate.convertAndSend(RabbitDetailConfig01.EX_LEARNING_ADDCHOOSECOURSE, RabbitDetailConfig01.XC_LEARNING_FINISHADDCHOOSECOURSE_KEY, mqTraceLog, correlationData);
		return ResponseResult.success();
	}

	/**
	 * 测试发送
	 */
	@RequestMapping("/testsend")
	public ResponseResult receiveChoosecourseTask(@RequestBody I18nResource i18nResource) {
		LOGGER.info("请求的数据：" + JSON.toJSONString(i18nResource));

		CorrelationData correlationData = new CorrelationData();
		correlationData.setId("123456");
		rabbitTemplate.convertAndSend(RabbitDetailConfig01.EX_LEARNING_ADDCHOOSECOURSE, RabbitDetailConfig01.XC_LEARNING_FINISHADDCHOOSECOURSE_KEY, i18nResource, correlationData);
		return ResponseResult.success();
	}

	/**
	 * 监听某个队列
	 */
//	@RabbitListener(queues = RabbitDetailConfig01.XC_LEARNING_FINISHADDCHOOSECOURSE)
	public void testReceive(I18nResource i18nResource) {
		LOGGER.info("接收到的消息：" + JSON.toJSONString(i18nResource));
		System.out.println("--------------------");
	}

}
