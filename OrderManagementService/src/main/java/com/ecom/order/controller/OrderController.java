/**
 * 
 */
package com.ecom.order.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.order.entity.Order;
import com.ecom.order.service.OrderService;
import com.ecom.order.util.OrderUtil;

/**
 * @author Pulin
 *
 */
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public List<Order> getOrders() {
		return orderService.getOrders();
	}

	@PostMapping("/order")
	public Order postOrder(@Valid @RequestBody Order order) {
		return orderService.postOrder(order);
	}

	@GetMapping("/order/{id}")
	public Order getOrderById(@PathVariable(value = "id") Long id) {
		return orderService.getOrderById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
	}

	@PutMapping("/order/{id}")
	public Order updateOrder(@PathVariable(value = "id") Long id, @Valid @RequestBody Order updatedOrder) {

		Order orderFromDb = orderService.getOrderById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
		Order persistedOrder = orderService.postOrder(OrderUtil.updateOrder(orderFromDb, updatedOrder));
		return persistedOrder;
	}
	
	@DeleteMapping("/order/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable(value = "id") Long id){
		
		Order orderFromDb = orderService.getOrderById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
		
		orderService.deleteOrder(orderFromDb);
		
		return ResponseEntity.ok().build();
	}

}
