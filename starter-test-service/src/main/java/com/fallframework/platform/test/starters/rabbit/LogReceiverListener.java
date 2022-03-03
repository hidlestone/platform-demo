package com.fallframework.platform.test.starters.rabbit;

import com.alibaba.fastjson.JSON;
import com.fallframework.platform.starter.mq.entity.MqTraceLog;
import com.fallframework.platform.starter.mq.model.StageEnum;
import com.fallframework.platform.starter.mq.service.MqTraceLogService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhuangpf
 */
@Component
public class LogReceiverListener implements ChannelAwareMessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogReceiverListener.class);

	@Autowired
	private MqTraceLogService mqTraceLogService;

	/**
	 * 发送消息的生产者在测试包中 MsgProducerTest
	 * （1）手动确认模式，消息手动拒绝中如果requeue为true会重新放入队列，但是如果消费者在处理过程中一直抛出异常，会导致入队-》拒绝-》入队的循环，该怎么处理呢？
	 * <p>
	 * 第一种方法是根据异常类型来选择是否重新放入队列。
	 * 第二种方法是先成功确认，然后通过**channel.basicPublish()**重新发布这个消息。重新发布的消息网上说会放到队列后面，进而不会影响已经进入队列的消息处理。
	 * <p>
	 * 4.开启重试机制
	 * 在接收者项目的配置文件里添加2个参数
	 * <p>
	 * #开启重试
	 * spring.rabbitmq.listener.simple.retry.enabled=true
	 * #重试次数，默认为3 次
	 * spring.rabbitmq.listener.simple.retry.max-attempts=3
	 */
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
		String msg = new String(message.getBody());
		System.out.println("收到的消息为 " + msg);
		Thread.sleep(500);
		try {
//			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			// 拒绝消息。
			// 第2个参数如果设为true，则表示批量拒绝当前通道中所有deliveryTag小于当前消息的所有消息。
			// 第3个参数如果设为true，则表示当前消息再次回到队列中等待被再次消费。
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//			channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
		}
		MqTraceLog mqTraceLogTmp = JSON.parseObject(msg, MqTraceLog.class);
		// 更新该记录的状态
		MqTraceLog mqTraceLog = mqTraceLogService.select(mqTraceLogTmp.getId()).getData();
		if (null == mqTraceLog) {
			LOGGER.error("mqTraceLog is not exist, id : {}", mqTraceLogTmp.getId());
		}
		mqTraceLog.setStage(StageEnum.CONSUMED_SUCCESS);
		mqTraceLogService.update(mqTraceLog);
	}

}
