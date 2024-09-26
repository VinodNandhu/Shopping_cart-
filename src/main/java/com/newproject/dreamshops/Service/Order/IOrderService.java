package com.newproject.dreamshops.Service.Order;

import com.newproject.dreamshops.DTO.OrderDto;
import com.newproject.dreamshops.Entity.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);  // Use lowercase method name as per Java conventions
    OrderDto  getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}