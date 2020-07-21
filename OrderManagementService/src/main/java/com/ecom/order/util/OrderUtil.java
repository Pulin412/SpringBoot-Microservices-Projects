/**
 * 
 */
package com.ecom.order.util;

import com.ecom.order.entity.Order;

/**
 * @author Pulin
 *
 */
public class OrderUtil {

	public static Order updateOrder(Order orderFromDb, Order updatedOrder) {
		
		orderFromDb.setOrderAmount(updatedOrder.getOrderAmount());
		orderFromDb.setOrderStatus(updatedOrder.getOrderStatus());
		return orderFromDb;
	}
}
