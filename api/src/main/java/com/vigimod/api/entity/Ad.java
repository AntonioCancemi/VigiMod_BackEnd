package com.vigimod.api.entity;

import java.time.LocalDateTime;

import com.vigimod.api.utils.AdStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "ads")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Ad {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// jpa relations

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	// Other ad properties
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AdStatus adStatus;

	@Column(nullable = false)
	private String location;// country-region-city

	@Column(nullable = false)
	private LocalDateTime publicationDate;

	@Column(nullable = true)
	private String motivation;

	@Column(nullable = true)
	private LocalDateTime lastUpdateAt;

	// ...
}
