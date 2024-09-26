package com.newproject.dreamshops.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Data
@Getter
@Setter

public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory ;
    private String description;
    private String category;// Represent category as a String (e.g., "electronics")



}
