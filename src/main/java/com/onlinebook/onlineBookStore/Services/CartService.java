package com.onlinebook.onlineBookStore.Services;

import com.onlinebook.onlineBookStore.DTO.cart.AddToCart;
import com.onlinebook.onlineBookStore.DTO.cart.CartItemResponseDto;
import com.onlinebook.onlineBookStore.DTO.cart.UpdateCartItemDto;

public interface CartService {

    CartItemResponseDto getCart();
    CartItemResponseDto addToCart(AddToCart dto);
    CartItemResponseDto updateCartItem(UpdateCartItemDto dto);
    CartItemResponseDto removeFromCart(Integer bookId);
    void clearCart();

}
