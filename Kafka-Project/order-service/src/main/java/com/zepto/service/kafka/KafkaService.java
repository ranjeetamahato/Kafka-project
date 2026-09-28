package com.zepto.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String Topic, String key, String Message) {

		kafkaTemplate.send(Topic, key, Message);
	}
}