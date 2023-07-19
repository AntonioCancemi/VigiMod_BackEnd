package com.vigimod.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private double price;

	private int stock;

	private String brand;
	@Column(nullable = false)
	private String category;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "seller_id")
	private Seller seller;
}
// product
// {
// "id": 1,
// "title": "iPhone 9",
// "description": "An apple mobile which is nothing like apple",
// "price": 549,
// "discountPercentage": 12.96,
// "rating": 4.69,
// "stock": 94,
// "brand": "Apple",
// "category": "smartphones",
// "thumbnail": "...",
// }
// PRUDCT LIST
// {
// "id": 1,
// "products": [
// {
// "id": 59,
// "title": "Spring and summershoes",
// "price": 20,
// "quantity": 3,
// "total": 60,
// "discountPercentage": 8.71,
// "discountedPrice": 55
// },
// {...},
// // more products
// ],
// "total": 2328,
// "discountedTotal": 1941,
// "userId": 97,
// "totalProducts": 5,
// "totalQuantity": 10
// }