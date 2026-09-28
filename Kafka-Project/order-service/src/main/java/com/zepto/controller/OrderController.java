package com.zepto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zepto.request.OrderRequest;
import com.zepto.response.OrderResponse;
import com.zepto.service.OrderService;

@RestController
@RequestMapping("order/")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping("place")
	public OrderResponse placeOrder(@RequestBody OrderRequest orderRequest) {

		OrderResponse orderResponse = orderService.acceptOrder(orderRequest);

		return orderResponse;
	}
}