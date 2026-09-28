package com.zepto.response;

public class PaymentFailed {

	private String eventId;

	private String eventType;

	private int orderId;

	private int customerId;

	private double amount;

	private String paymentId;

	private String paymentStatus;

	private String reason;

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

	public double getAmount() {

		return amount;

	}

	public void setAmount(double amount) {

		this.amount = amount;

	}

	public String getPaymentId() {

		return paymentId;

	}

	public void setPaymentId(String paymentId) {

		this.paymentId = paymentId;

	}

	public String getPaymentStatus() {

		return paymentStatus;

	}

	public void setPaymentStatus(String paymentStatus) {

		this.paymentStatus = paymentStatus;

	}

	public String getReason() {

		return reason;

	}

	public void setReason(String reason) {

		this.reason = reason;

	}

	@Override

	public String toString() {

		return "PaymentFailed [eventId=" + eventId + ", eventType=" + eventType + ", orderId=" + orderId

				+ ", customerId=" + customerId + ", amount=" + amount + ", paymentId=" + paymentId + ", paymentStatus="

				+ paymentStatus + ", reason=" + reason + "]";

	}



}