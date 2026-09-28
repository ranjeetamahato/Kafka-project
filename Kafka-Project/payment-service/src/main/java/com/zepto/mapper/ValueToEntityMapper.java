package com.zepto.mapper;

import com.zepto.entity.PaymentEntity;
import com.zepto.service.Event;

public class ValueToEntityMapper {

	public PaymentEntity valueToEntity(Event value) {
		
		PaymentEntity paymentEntity = new PaymentEntity();
		
		paymentEntity.setEventId(value.getEventId());
		paymentEntity.setOrderId(value.getOrderId());
		paymentEntity.setCustomerId(value.getCustomerId());
		paymentEntity.setAmount(value.getAmount());
		paymentEntity.setPaymentId("TXN-90002");
		paymentEntity.setPaymentStatus("SUCCESS");
		
		return paymentEntity;
	}
}
