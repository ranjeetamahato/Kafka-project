package com.zepto.service;

public class Event {

	private String eventId;

	private String eventType;

	private int orderId;

	private int customerId;

	private int amount;

	private String deliveryAddress;	

	public String getEventId() {

		return eventId;

	}

	public void setEventId(String eventId) {

		this.eventId = eventId;

	}

	public String getEventType() {

		return eventType;

	}

	public void setEventType(String eventType) {

		this.eventType = eventType;

	}

	public int getOrderId() {

		return orderId;

	}

	public void setOrderId(int orderId) {

		this.orderId = orderId;

	}

	public int getCustomerId() {

		return customerId;

	}

	public void setCustomerId(int customerId) {

		this.customerId = customerId;

	}

	public int getAmount() {

		return amount;

	}

	public void setAmount(int amount) {

		this.amount = amount;

	}

	public String getDeliveryAddress() {

		return deliveryAddress;

	}

	public void setDeliveryAddress(String deliveryAddress) {

		this.deliveryAddress = deliveryAddress;

	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", eventType=" + eventType + ", orderId=" + orderId + ", customerId="
				+ customerId + ", amount=" + amount + ", deliveryAddress=" + deliveryAddress + "]";
	}



}