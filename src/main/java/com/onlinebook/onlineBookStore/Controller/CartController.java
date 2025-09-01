package com.onlinebook.onlineBookStore.Controller;

import com.onlinebook.onlineBookStore.DTO.ApiResponse;
import com.onlinebook.onlineBookStore.DTO.cart.AddToCart;
import com.onlinebook.onlineBookStore.DTO.cart.CartItemResponseDto;
import com.onlinebook.onlineBookStore.DTO.cart.UpdateCartItemDto;
import com.onlinebook.onlineBookStore.Services.Implementaion.CartServiceImplementation;
import com.onlinebook.onlineBookStore.Utils.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartServiceImplementation cartServiceImplementation;
    private final ResponseUtils responseUtils;

    @GetMapping
    public ResponseEntity<ApiResponse<CartItemResponseDto>> getCart(){
        System.out.println("Get All Cart");
        CartItemResponseDto cartItemResponseDto = cartServiceImplementation.getCart();
        return responseUtils.ok(cartItemResponseDto);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> addToCart(
            @Valid @RequestBody AddToCart dto
            ){
        System.out.println("Received DTO: " + dto);
        CartItemResponseDto cartItemResponseDto = cartServiceImplementation.addToCart(dto);
        return responseUtils.ok(cartItemResponseDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> updateCart(
            @Valid @RequestBody UpdateCartItemDto dto){
        CartItemResponseDto cartItemResponseDto = cartServiceImplementation.updateCartItem(dto);
        return responseUtils.ok(cartItemResponseDto);
    }

    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> removeFromCart(@PathVariable Integer bookId) {
       cartServiceImplementation.removeFromCart(bookId);
        return responseUtils.ok("Removed from cart",null);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart(){
        cartServiceImplementation.clearCart();
        return responseUtils.noContent();
    }
}
