package com.shallowan.seckill.rabbitmq;

import com.shallowan.seckill.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ShallowAn
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendSeckillMessage(SeckillMessage message) {
        String msg = RedisService.beanToString(message);
        log.info("send message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, msg);
    }


//    public void send(Object message) {
//        String msg = RedisService.beanToString(message);
//        log.info("send message:" + msg);
//        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
//    }
//
//    public void sendTopic(Object message) {
//        String msg = RedisService.beanToString(message);
//        log.info("send message:" + msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg + "1");
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg + "2");
//    }
//
//    public void sendFanout(Object message) {
//        String msg = RedisService.beanToString(message);
//        log.info("send fanout message:" + msg);
//        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
//    }
//
//    public void sendHeaders(Object message) {
//        String msg = RedisService.beanToString(message);
//        log.info("send fanout message:" + msg);
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setHeader("header1", "value1");
//        messageProperties.setHeader("header2", "value2");
//        Message obj = new Message(msg.getBytes(), messageProperties);
//        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
//    }
//
//
}
