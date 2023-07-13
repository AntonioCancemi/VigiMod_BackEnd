package com.vigimod.api.entity;

import java.util.Random;

import com.github.javafaker.Faker;
import com.vigimod.api.utils.SellerType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sellers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String fullName;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false, unique = true)
	private String phoneNumber;
	@Column(nullable = false)
	private boolean accontActive;
	private String image;
	// per il livello di moderazione
	@Enumerated(EnumType.STRING)
	private SellerType sellerType;

	public Seller sellerTestBean(boolean active) {
		return Seller.builder()
				.username("test")
				.fullName("test")
				.email("test")
				.phoneNumber("test")
				.accontActive(active ? true : false)
				.image("test")
				.sellerType(SellerType.AGENCY)
				.build();
	}

	public Seller sellerFakeBean() {
		Faker faker = new Faker();
		SellerType[] t = SellerType.values();
		Random rand = new Random();
		return Seller.builder()
				.username(faker.name().username())
				.fullName(faker.name().fullName())
				.email(faker.internet().emailAddress())
				.phoneNumber(faker.phoneNumber().cellPhone())
				.accontActive(rand.nextBoolean())
				.image(faker.internet().image())
				.sellerType(t[rand.nextInt(t.length)])
				.build();
	}
}
// One-to-Many relationship with Ad
// @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
// private List<Ad> ads;
