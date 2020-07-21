/**
 * 
 */
package com.ecom.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.product.dao.ProductDAO;
import com.ecom.product.entity.Product;

/**
 * @author Pulin
 *
 */
@Service
public class ProductService {

	@Autowired
	private ProductDAO productdao;
	
	public List<Product> getProducts() {
		return productdao.findAll();
	}
	
	public Product postProduct(Product product) {
		return productdao.save(product);
	}

	public Optional<Product> getProductById(Long id) {
		return productdao.findById(id);
	}

	public void deleteProduct(Product productFromDb) {
		productdao.delete(productFromDb);
	}
}
