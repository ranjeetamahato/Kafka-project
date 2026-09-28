package com.zepto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.entity.OrderEntity;
import com.zepto.event.response.OrderEvent;
import com.zepto.repository.OrderRepository;
import com.zepto.request.OrderRequest;
import com.zepto.response.OrderResponse;
import com.zepto.service.kafka.KafkaService;

import tools.jackson.databind.ObjectMapper;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	KafkaService kafkaService;

	public OrderResponse acceptOrder(OrderRequest orderRequest) {

		// 1. Create entity
		OrderEntity entity = new OrderEntity();

		entity.setCustomerId(orderRequest.getCustomerId());
		entity.setCustomerName(orderRequest.getCustomerName());
		entity.setProductId(orderRequest.getProductId());
		entity.setProductName(orderRequest.getProductName());
		entity.setQuantity(orderRequest.getQuantity());
		entity.setAmount(orderRequest.getAmount());
		entity.setDeliveryAddress(orderRequest.getDeliveryAddress());

		// 2. Save order into database
		OrderEntity responseEntity = orderRepository.save(entity);

		// 3. Create API response
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setOrderId(1001);
		orderResponse.setStatus("CREATED");

		// 4. Create Kafka event
		OrderEvent event = new OrderEvent();
		event.setEventId("EVT-10001");
		event.setEventType("ORDER_CREATED");
		event.setOrderId(1002);
		event.setCustomerId(orderRequest.getCustomerId());
		event.setAmount(orderRequest.getAmount());
		event.setDeliveryAddress(orderRequest.getDeliveryAddress());

		// 5. Convert Kafka event to JSON
		String json = objToJson(event);

		// 6. Send JSON to Kafka
		String str = String.valueOf(event.getOrderId());
		kafkaService.sendMessage("order-placed", str, json);

		return orderResponse;
	}

	private String objToJson(OrderEvent event) {

		ObjectMapper objectMapper = new ObjectMapper();

		String json = objectMapper.writeValueAsString(event);

		return json;
	}
}