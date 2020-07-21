/**
 * 
 */
package com.ecom.product.util;

import javax.validation.Valid;

import com.ecom.product.entity.Product;

/**
 * @author Pulin
 *
 */
public class ProductUtil {

	public static Product updateProduct(Product productFromDb, Product updatedProduct) {

		productFromDb.setProductName(updatedProduct.getProductName());
		productFromDb.setProductDescription(updatedProduct.getProductDescription());
		productFromDb.setProductCategory(updatedProduct.getProductCategory());
		productFromDb.setProductBasePrice(updatedProduct.getProductBasePrice());
		productFromDb.setProductGstPercent(updatedProduct.getProductGstPercent());
		productFromDb.setProductMrp(ProductUtil.computeMRP(updatedProduct));
		return productFromDb;
	}

	public static Double computeMRP(@Valid Product product) {
		
		Double gstPercent = product.getProductGstPercent();
		Double basePrice = product.getProductBasePrice();
		
		return basePrice + ((gstPercent * basePrice)/100);
	}
}
