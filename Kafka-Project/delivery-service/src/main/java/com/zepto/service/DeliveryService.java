package com.zepto.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;

import com.zepto.response.DeliveryResponse;

import tools.jackson.databind.ObjectMapper;

@Service

public class DeliveryService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	KafkaTemplate<String, String>kafkaTemplate;


	@KafkaListener(topics = "Payment-success", groupId = "PS2")
	public void receiveMessage(ConsumerRecord<String, String>consumerRecord)
	{
		String message = consumerRecord.value();
		
		Event value = objectMapper.readValue(message, Event.class);
		
		DeliveryResponse deliveryResponse = new DeliveryResponse();
		deliveryResponse.setEventId(value.getEventId());
		deliveryResponse.setEventType(value.getEventType());
		deliveryResponse.setOrderId(value.getOrderId());
		deliveryResponse.setCustomerId(value.getCustomerId());
		deliveryResponse.setTrackingNumber("TRK-50001");
		deliveryResponse.setDeliveryAddress("Bangalore");
		deliveryResponse.setDeliveryStatus("CREATED");
		String writeValueAsString = objectMapper.writeValueAsString(deliveryResponse);
		System.out.println(deliveryResponse);
		kafkaTemplate.send("delivery-created", writeValueAsString);
		
	}

}