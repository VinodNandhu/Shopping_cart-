package com.newproject.dreamshops.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDate orderDate;
    private String status;
    private BigDecimal totalAmount;

    private List<OrderItemDto> items;


}
