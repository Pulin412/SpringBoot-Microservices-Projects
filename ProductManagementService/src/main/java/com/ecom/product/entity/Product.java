package com.ecom.product.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String productName;
	
	@Column(name = "description")
	private String productDescription;
	
	@Column(name = "category")
	private String productCategory;
	
	@Column(name = "baseprice")
	private Double productBasePrice;
	
	@Column(name = "gstpercent")
	private Double productGstPercent;
	
	@Column(name = "mrp")
	private Double productMrp;
	
	@Column
	@CreationTimestamp
    private Date createdAt;
	
	@Column
	@UpdateTimestamp
	private Date updatedDate;
	
	/**
	 * 
	 */
	public Product() {
		super();
	}

	
	/**
	 * @param productName
	 * @param productDescription
	 * @param productBasePrice
	 * @param productGstPercent
	 * @param productMrp
	 */
	public Product(String productName, String productDescription, String productCategory, Double productBasePrice, Double productGstPercent) {
		super();
		this.productName = productName;
		this.productDescription = productDescription;
		this.productCategory = productCategory;
		this.productBasePrice = productBasePrice;
		this.productGstPercent = productGstPercent;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}

	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	/**
	 * @return the productBasePrice
	 */
	public Double getProductBasePrice() {
		return productBasePrice;
	}

	/**
	 * @param productBasePrice the productBasePrice to set
	 */
	public void setProductBasePrice(Double productBasePrice) {
		this.productBasePrice = productBasePrice;
	}

	/**
	 * @return the productGstPercent
	 */
	public Double getProductGstPercent() {
		return productGstPercent;
	}

	/**
	 * @param productGstPercent the productGstPercent to set
	 */
	public void setProductGstPercent(Double productGstPercent) {
		this.productGstPercent = productGstPercent;
	}

	/**
	 * @return the productMrp
	 */
	public Double getProductMrp() {
		return productMrp;
	}

	/**
	 * @param productMrp the productMrp to set
	 */
	public void setProductMrp(Double productMrp) {
		this.productMrp = productMrp;
	}


	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}


	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}


	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}


	/**
	 * @return the productCategory
	 */
	public String getProductCategory() {
		return productCategory;
	}


	/**
	 * @param productCategory the productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	
	
}
