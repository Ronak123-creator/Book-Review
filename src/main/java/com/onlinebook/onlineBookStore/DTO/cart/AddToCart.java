package com.onlinebook.onlineBookStore.DTO.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddToCart {
    @NotNull
    private Integer bookId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
