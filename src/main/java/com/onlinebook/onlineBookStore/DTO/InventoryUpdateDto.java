package com.onlinebook.onlineBookStore.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryUpdateDto {

    private Integer bookId;
    private Integer quantityChange;
}
