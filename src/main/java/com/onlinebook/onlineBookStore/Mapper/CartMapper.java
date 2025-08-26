package com.onlinebook.onlineBookStore.Mapper;

import com.onlinebook.onlineBookStore.DTO.cart.AddToCart;
import com.onlinebook.onlineBookStore.DTO.cart.CartItemDto;
import com.onlinebook.onlineBookStore.DTO.cart.CartItemResponseDto;
import com.onlinebook.onlineBookStore.DTO.cart.UpdateCartItemDto;
import com.onlinebook.onlineBookStore.Entity.Book;
import com.onlinebook.onlineBookStore.Entity.Cart;
import com.onlinebook.onlineBookStore.Entity.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {

    public static CartItem toEntity(AddToCart dto, Book book, Cart cart){
        CartItem cartItem = new CartItem();

        cartItem.setBook(book);
        cartItem.setCart(cart);
        cartItem.setQuantity(dto.getQuantity());
        return cartItem;
    }

    public static void updateEntity(CartItem cartItem , UpdateCartItemDto dto){
        cartItem.setQuantity(dto.getQuantity());
    }

    public static CartItemDto toCartItemDto(CartItem cartItem){

        return new CartItemDto(
                cartItem.getId(),
                cartItem.getBook().getId(),
                cartItem.getBook().getTitle(),
                cartItem.getBook().getAuthor(),
                cartItem.getBook().getPrice(),
                cartItem.getBook().getCoverImage(),
                cartItem.getQuantity(),
                cartItem.getBook().getPrice() * cartItem.getItemTotal()
        );

    }

    public static CartItemResponseDto toDto(Cart cart){
        List<CartItemDto> item = cart.getItems().stream()
                .map(CartMapper::toCartItemDto)
                .collect(Collectors.toList());

        return new CartItemResponseDto(
                cart.getId(),
                cart.getUser().getId(),
                cart.getUser().getEmail(),
                cart.getCreatedAt(),
                cart.getUpdatedAt(),
                item,
                cart.getItems().stream()
                        .mapToInt(CartItem::getQuantity)
                        .sum(),
                cart.getItems().stream()
                        .mapToDouble(items -> items.getBook().getPrice() * item.getQuantity())
                        .sum()
        );

    }


}
