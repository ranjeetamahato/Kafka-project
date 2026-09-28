package com.zepto.event.request;

public class OrderCreated {


	private int customerId;

    private String customerName;

    private int productId;

    private String productName;

    private int quantity;

    private double amount;

    private String deliveryAddress;


	public int getCustomerId() {

		return customerId;

	}

	public void setCustomerId(int customerId) {

		this.customerId = customerId;

	}

	public String getCustomerName() {

		return customerName;

	}

	public void setCustomerName(String customerName) {

		this.customerName = customerName;

	}

	public int getProductId() {

		return productId;

	}

	public void setProductId(int productId) {

		this.productId = productId;

	}

	public String getProductName() {

		return productName;

	}

	public void setProductName(String productName) {

		this.productName = productName;

	}

	public int getQuantity() {

		return quantity;

	}

	public void setQuantity(int quantity) {

		this.quantity = quantity;

	}

	public double getAmount() {

		return amount;

	}

	public void setAmount(double amount) {

		this.amount = amount;

	}

	public String getDeliveryAddress() {

		return deliveryAddress;

	}

	public void setDeliveryAddress(String deliveryAddress) {

		this.deliveryAddress = deliveryAddress;

	}

}