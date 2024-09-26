package com.newproject.dreamshops.Service.Cart;

import com.newproject.dreamshops.Entity.Cart;
import com.newproject.dreamshops.Entity.User;

import java.math.BigDecimal;


public interface ICartService {

    Cart getCart(Long cartId);

    void ClearCart(Long cartId);

    BigDecimal getTotalPrice(Long cartId);


    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
