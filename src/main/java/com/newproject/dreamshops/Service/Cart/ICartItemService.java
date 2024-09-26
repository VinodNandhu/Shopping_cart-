package com.newproject.dreamshops.Service.Cart;


import com.newproject.dreamshops.Entity.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long itemId);

    void updateItemQuantity(Long cartId, Long itemId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
