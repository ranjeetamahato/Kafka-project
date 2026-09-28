package com.zepto.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.zepto.PaymentRepository;
import com.zepto.entity.PaymentEntity;
import com.zepto.mapper.ValueToEntityMapper;
import com.zepto.response.PaymentFailed;
import com.zepto.response.PaymentSuccess;

import tools.jackson.databind.ObjectMapper;

@Service
public class PaymentService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	ObjectMapper objmapper;

	@Autowired
	PaymentRepository paymentRepository;

	/**
	 * If exception occurs, Kafka will retry the message.
	 * After retry attempts are exhausted, message will go to DLT.
	 */
	@RetryableTopic(attempts = "4")
	@KafkaListener(topics = "order-placed", groupId = "PS1")
	public void receiveMessage(ConsumerRecord<String, String> consumerRecord) {

		String message = consumerRecord.value();

		Event value = objectMapper.readValue(message, Event.class);

		System.out.println("Delivery Address = [" + value.getDeliveryAddress() + "]");

		/*
		 * Duplicate event checking
		 */
		Boolean paymentExist = paymentRepository.existsByOrderId(value.getOrderId());

		if (paymentExist) {

			System.out.println(" :::::::::::::::::::::::::::::::::: ");
			System.out.println("Duplicate Event ... " + value);
			System.out.println("Payment already processed. Skipping.");
			System.out.println(" :::::::::::::::::::::::::::::::::: ");

			return;
		}

		/*
		 * Address validation
		 *
		 * Bangalore -> continue
		 * Mumbai/Delhi/Chennai/etc. -> exception
		 *
		 * Exception will trigger retry and finally DLT.
		 */
		if (!"Bangalore".equals(value.getDeliveryAddress())) {

			throw new RuntimeException("address not equal to Bangalore");
		}

		/*
		 * Payment Success
		 */
		if (value.getAmount() > 10000) {

			ValueToEntityMapper valueToEntityMapper =
					new ValueToEntityMapper();

			PaymentEntity paymentEntity =
					valueToEntityMapper.valueToEntity(value);

			/*
			 * Store payment event in database
			 */
			PaymentEntity newPaymentEntity =
					paymentRepository.save(paymentEntity);

			PaymentSuccess paymentSuccess = new PaymentSuccess();

			paymentSuccess.setEventId(value.getEventId());
			paymentSuccess.setEventType(value.getEventType());
			paymentSuccess.setOrderId(value.getOrderId());
			paymentSuccess.setCustomerId(value.getCustomerId());
			paymentSuccess.setAmount(value.getAmount());
			paymentSuccess.setPaymentId("TXN-90002");
			paymentSuccess.setPaymentMethod("UPI");
			paymentSuccess.setPaymentStatus("SUCCESS");

			String writeValueAsString =
					objmapper.writeValueAsString(paymentSuccess);

			System.out.println(paymentSuccess);

			kafkaTemplate.send(
					"Payment-success",
					writeValueAsString);
		}

		/*
		 * Payment Failed
		 */
		else {

			ValueToEntityMapper valueToEntityMapper =
					new ValueToEntityMapper();

			PaymentEntity paymentEntity =
					valueToEntityMapper.valueToEntity(value);

			/*
			 * Store payment event in database
			 */
			PaymentEntity newPaymentEntity =
					paymentRepository.save(paymentEntity);

			PaymentFailed paymentFailed = new PaymentFailed();

			paymentFailed.setEventId(value.getEventId());
			paymentFailed.setEventType(value.getEventType());
			paymentFailed.setOrderId(value.getOrderId());
			paymentFailed.setCustomerId(value.getCustomerId());
			paymentFailed.setAmount(value.getAmount());
			paymentFailed.setPaymentId("TXN-90002");
			paymentFailed.setPaymentStatus("FAILED");
			paymentFailed.setReason("INSUFFICIENT_FUNDS");

			String writeValueAsString =
					objmapper.writeValueAsString(paymentFailed);

			System.out.println(paymentFailed);

			/*
			 * Sending message using orderId as Kafka key.
			 */
			String key = String.valueOf(value.getOrderId());

			kafkaTemplate.send(
					"Payment-failer",
					key,
					writeValueAsString);
		}
	}

	/**
	 * This method will be called when the message
	 * finally goes to the Dead Letter Topic.
	 */
	@DltHandler
	public void dltHandler(
			ConsumerRecord<String, String> consumerRecord) {

		System.out.println("======================================");
		System.out.println("Message received in DLT");
		System.out.println("======================================");

		System.out.println("Topic: "
				+ consumerRecord.topic());

		System.out.println("Partition: "
				+ consumerRecord.partition());

		System.out.println("Offset: "
				+ consumerRecord.offset());

		System.out.println("Key: "
				+ consumerRecord.key());

		System.out.println("Message: "
				+ consumerRecord.value());

		System.out.println("======================================");
	}
}