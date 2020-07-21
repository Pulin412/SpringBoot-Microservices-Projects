/**
 * 
 */
package com.ecom.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.order.dao.OrderDAO;
import com.ecom.order.entity.Order;

/**
 * @author Pulin
 *
 */
@Service
public class OrderService {

	@Autowired
	private OrderDAO orderdao;
	
	public List<Order> getOrders() {
		return orderdao.findAll();
	}
	
	public Order postOrder(Order order) {
		return orderdao.save(order);
	}

	public Optional<Order> getOrderById(Long id) {
		return orderdao.findById(id);
	}

	public void deleteOrder(Order orderFromDb) {
		orderdao.delete(orderFromDb);
	}

}
