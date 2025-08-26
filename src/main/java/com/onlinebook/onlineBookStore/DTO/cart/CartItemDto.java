package com.onlinebook.onlineBookStore.DTO.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Integer id;
    private Integer bookId;
    private String bookTitle;
    private String bookAuthor;
    private Double bookPrice;
    private String coverImage;
    private Integer quantity;
    private Double subtotal;
}
