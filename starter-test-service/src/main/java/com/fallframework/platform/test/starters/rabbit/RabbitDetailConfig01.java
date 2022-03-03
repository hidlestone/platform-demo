package com.fallframework.platform.test.starters.rabbit;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuangpf
 */
@Configuration
public class RabbitDetailConfig01 {

	// 添加选课任务交换机
	public static final String EX_LEARNING_ADDCHOOSECOURSE = "ex_learning_addchoosecourse";

	//完成添加选课消息队列
	public static final String XC_LEARNING_FINISHADDCHOOSECOURSE = "xc_learning_finishaddchoosecourse";

	//添加选课消息队列
	public static final String XC_LEARNING_ADDCHOOSECOURSE = "xc_learning_addchoosecourse";

	//添加选课路由key
	public static final String XC_LEARNING_ADDCHOOSECOURSE_KEY = "addchoosecourse";
	//完成添加选课路由key
	public static final String XC_LEARNING_FINISHADDCHOOSECOURSE_KEY = "finishaddchoosecourse";

	/**
	 * 交换机配置
	 *
	 * @return the exchange
	 */
	@Bean(EX_LEARNING_ADDCHOOSECOURSE)
	public Exchange EX_DECLARE() {
		return ExchangeBuilder.directExchange(EX_LEARNING_ADDCHOOSECOURSE).durable(true).build();
	}

	//声明队列完成添加选课队列
	@Bean(XC_LEARNING_FINISHADDCHOOSECOURSE)
	public Queue QUEUE_DECLARE() {
		Queue queue = new Queue(XC_LEARNING_FINISHADDCHOOSECOURSE, true, false, true);
		return queue;
	}

	//声明队列 添加选课队列
	@Bean(XC_LEARNING_ADDCHOOSECOURSE)
	public Queue QUEUE_DECLARE_2() {
		Queue queue = new Queue(XC_LEARNING_ADDCHOOSECOURSE, true, false, true);
		return queue;
	}

	/**
	 * 绑定完成添加选课队列到交换机 .
	 *
	 * @param queue    the queue
	 * @param exchange the exchange
	 * @return the binding
	 */
	@Bean
	public Binding binding_finishaddchoose_processtask(@Qualifier("xc_learning_finishaddchoosecourse") Queue queue, @Qualifier(EX_LEARNING_ADDCHOOSECOURSE) Exchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(XC_LEARNING_FINISHADDCHOOSECOURSE_KEY).noargs();
	}

	/**
	 * 绑定添加选课队列到交换机 .
	 *
	 * @param queue    the queue
	 * @param exchange the exchange
	 * @return the binding
	 */
	@Bean
	public Binding binding_addchoose_processtask(@Qualifier("xc_learning_addchoosecourse") Queue queue, @Qualifier(EX_LEARNING_ADDCHOOSECOURSE) Exchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(XC_LEARNING_ADDCHOOSECOURSE_KEY).noargs();
	}

	// ====> declare connectionFactorys <===
	/*@Bean("msgConnectionFactory")
	public ConnectionFactory msgConnectionFactory(
			@Value("${spring.rabbitmq.host}") String host,
			@Value("${spring.rabbitmq.port}") int port,
			@Value("${spring.rabbitmq.username}") String username,
			@Value("${spring.rabbitmq.password}") String password,
			@Value("${spring.rabbitmq.virtual-host}") String vhost) {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
		connectionFactory.setPort(port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(vhost);
		connectionFactory.setPublisherConfirms(true);
		connectionFactory.setPublisherReturns(true);
		return connectionFactory;
	}*/

	// ====> declare containers <===
	@Bean
	public SimpleMessageListenerContainer container(
			@Autowired ConnectionFactory connectionFactory,
			@Qualifier(RabbitDetailConfig01.XC_LEARNING_FINISHADDCHOOSECOURSE) Queue q1,
			LogReceiverListener logReceiverListener) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueues(q1);
		container.setMaxConcurrentConsumers(15);
		container.setConcurrentConsumers(15);
		// 手动确认
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setMessageListener(logReceiverListener);
		return container;
	}

}
