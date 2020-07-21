/**
 * 
 */
package com.ecom.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.order.entity.Order;

/**
 * @author Pulin
 *
 */
@Repository
public interface OrderDAO extends JpaRepository<Order, Long>{

}
