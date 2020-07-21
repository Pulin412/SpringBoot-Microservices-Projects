package com.ecom.order.controller.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.ecom.order.entity.Order;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

	@LocalServerPort
	int randomServerPort;

	private String hostName = "localhost:";

	RestTemplate restTemplate = new RestTemplate();

	private String baseOrderUrl(String path) {
		return "http://" + hostName + randomServerPort + path;
	}
	
	private void assertOrder(Long id) {
		
		ResponseEntity<Order> response = restTemplate.getForEntity(baseOrderUrl("/order/"+ id), Order.class);
		Assert.assertEquals(response.getStatusCodeValue(), 200);
		Assert.assertEquals(response.getBody().getId(), id);
	}

	@Test
	public void shouldGetAllOrders() {

		ResponseEntity<List> response = restTemplate.getForEntity(baseOrderUrl("/orders"), List.class);
		Assert.assertEquals(response.getStatusCodeValue(), 200);
	}

	@Test
	public void shouldSaveOrder() {

		Order order = new Order(3500d, "Created");
		
		Order response = restTemplate.postForObject(baseOrderUrl("/order"), order, Order.class);
		assertOrder(response.getId());
		
	}
}
