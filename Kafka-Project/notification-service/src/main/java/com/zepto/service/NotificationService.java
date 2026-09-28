package com.zepto.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.zepto.event.request.DeliveryCreated;
import com.zepto.event.request.OrderCreated;
import com.zepto.event.request.PaymentFailed;
import com.zepto.event.request.PaymentSuccess;

import tools.jackson.databind.ObjectMapper;

@Service
public class NotificationService {

	@Autowired
	ObjectMapper objectMapper;
	
	@KafkaListener(topics ="order-placed", groupId = "notification-service-group")
	public synchronized  void orderCreated(ConsumerRecord<String, String>consumerRecord)
	{
		String message = consumerRecord.value();
		OrderCreated value = objectMapper.readValue(message, OrderCreated.class);
		
		System.out.println("Order Created");
		System.out.println("----------------------------");
		System.out.println("SMS SENT");
		System.out.println("Customer: " + value.getCustomerId());
		System.out.println("1001" );
		System.out.println("Your order has been created successfully.");
	}
	
	@KafkaListener(topics ="Payment-success", groupId = "notification-service-group")
	public synchronized void paymentSuccess(ConsumerRecord<String, String>consumerRecord)
	{
		String message = consumerRecord.value();
		PaymentSuccess value = objectMapper.readValue(message, PaymentSuccess.class);
		
		System.out.println("Payment Success");
		System.out.println("----------------------------");
		System.out.println("SMS SENT");
		System.out.println("Customer: " + value.getCustomerId());
		System.out.println("Order: " + value.getOrderId());
		System.out.println("Your payment of Rs." + value.getAmount() + " was successful.");
	}
	
	@KafkaListener(topics ="Payment-failer", groupId = "notification-service-group")
	public synchronized void paymentFailed(ConsumerRecord<String, String>consumerRecord)
	{
		String message = consumerRecord.value();
		PaymentFailed value = objectMapper.readValue(message, PaymentFailed.class);
		
		System.out.println("Payment Failed");
		System.out.println("----------------------------");
		System.out.println("SMS SENT");
		System.out.println("Customer: " + value.getCustomerId());
		System.out.println("Order: " + value.getOrderId());
		System.out.println("Your payment of Rs." + value.getAmount() + " was successful.");
	}
	
	@KafkaListener(topics ="delivery-created", groupId = "notification-service-group")
	public synchronized void DeliveryCreated(ConsumerRecord<String, String>consumerRecord)
	{
		String message = consumerRecord.value();
		DeliveryCreated value = objectMapper.readValue(message, DeliveryCreated.class);
		
		System.out.println("Delivery Created");
		System.out.println("----------------------------");
		System.out.println("SMS SENT");
		System.out.println("Customer: " + value.getCustomerId());
		System.out.println("Order: " + value.getOrderId());
		System.out.println("Your delivery has been created successfully.");
	}
}