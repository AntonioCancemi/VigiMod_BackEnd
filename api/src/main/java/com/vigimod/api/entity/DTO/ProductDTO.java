package com.vigimod.api.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private String title;
    private String description;
    private double price;
    private int stock;
    private String brand;
    private String category;

    private Long sellerId;
}
