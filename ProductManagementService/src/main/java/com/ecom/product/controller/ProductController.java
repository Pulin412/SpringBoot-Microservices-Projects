/**
 * 
 */
package com.ecom.product.controller;

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

import com.ecom.product.entity.Product;
import com.ecom.product.service.ProductService;
import com.ecom.product.util.ProductUtil;

/**
 * @author Pulin
 *
 */
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public List<Product> getProducts() {
		return productService.getProducts();
	}

	@PostMapping("/product")
	public Product postProduct(@Valid @RequestBody Product product) {
		product.setProductMrp(ProductUtil.computeMRP(product));
		return productService.postProduct(product);
	}

	@GetMapping("/product/{id}")
	public Product getProductById(@PathVariable(value = "id") Long id) {
		return productService.getProductById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Product Id:" + id));
	}

	@PutMapping("/product/{id}")
	public Product updateProduct(@PathVariable(value = "id") Long id, @Valid @RequestBody Product updatedProduct) {

		Product productFromDb = productService.getProductById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Product Id:" + id));
		Product persistedProduct = productService.postProduct(ProductUtil.updateProduct(productFromDb, updatedProduct));
		return persistedProduct;
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long id) {

		Product productFromDb = productService.getProductById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Product Id:" + id));

		productService.deleteProduct(productFromDb);

		return ResponseEntity.ok().build();
	}
}
