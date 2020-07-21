package com.ecom.product.controller.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.ecom.product.entity.Product;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

	@LocalServerPort
	int randomServerPort;

	private String hostName = "localhost:";

	RestTemplate restTemplate = new RestTemplate();

	private String baseUrl(String path) {
		return "http://" + hostName + randomServerPort + path;
	}
	
	private void assertProduct(Long id) {
		
		ResponseEntity<Product> response = restTemplate.getForEntity(baseUrl("/product/"+ id), Product.class);
		Assert.assertEquals(response.getStatusCodeValue(), 200);
		Assert.assertEquals(response.getBody().getId(), id);
	}

	@Test
	public void shouldGetAllProducts() {

		ResponseEntity<List> response = restTemplate.getForEntity(baseUrl("/products"), List.class);
		Assert.assertEquals(response.getStatusCodeValue(), 200);
	}

	@Test
	public void shouldSaveProduct() {

		Product product = new Product("Mobile", "Desc", "Electronics", 20000d, 10d);
		
		Product response = restTemplate.postForObject(baseUrl("/product"), product, Product.class);
		assertProduct(response.getId());
		
	}
}
