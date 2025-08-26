package com.onlinebook.onlineBookStore.Services.Implementaion;

import com.onlinebook.onlineBookStore.DTO.cart.AddToCart;
import com.onlinebook.onlineBookStore.DTO.cart.CartItemResponseDto;
import com.onlinebook.onlineBookStore.DTO.cart.UpdateCartItemDto;
import com.onlinebook.onlineBookStore.Entity.Book;
import com.onlinebook.onlineBookStore.Entity.Cart;
import com.onlinebook.onlineBookStore.Entity.CartItem;
import com.onlinebook.onlineBookStore.Entity.UserInfo;
import com.onlinebook.onlineBookStore.ExceptionHandeling.CustomExceptionHandel;
import com.onlinebook.onlineBookStore.Mapper.CartMapper;
import com.onlinebook.onlineBookStore.Repository.BookRepository;
import com.onlinebook.onlineBookStore.Repository.CartItemRepository;
import com.onlinebook.onlineBookStore.Repository.CartRepository;
import com.onlinebook.onlineBookStore.Repository.UserInfoRepository;
import com.onlinebook.onlineBookStore.Services.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartServiceImplementation implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserInfoRepository userInfoRepository;

    private UserInfo getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        return userInfoRepository.findByEmail(email)
                .orElseThrow(()-> new CustomExceptionHandel("User Not Found",
                        HttpStatus.NOT_FOUND.value()));
    }

    private Cart getOrCreateCart(UserInfo user){
     if(user.getCart() == null){
         Cart newCart = new Cart();
         newCart.setUser(user);
         return cartRepository.save(newCart);
     }
     return user.getCart();
    }

    @Override
    public CartItemResponseDto getCart() {
        UserInfo user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        return CartMapper.toDto(cart);
    }

    @Override
    public CartItemResponseDto addToCart(AddToCart dto) {
        UserInfo user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(()->new CustomExceptionHandel("Book Not Found", HttpStatus.NOT_FOUND.value()));

        if(book.getQuantity()< dto.getQuantity()){
            throw  new CustomExceptionHandel("Insufficient Stock. Available: " +book.getQuantity(),
                    HttpStatus.BAD_REQUEST.value());

        }
        //check in cart if already exists
        Optional<CartItem> existing = cartItemRepository.findByCartAndBook(cart,book);

        if(existing.isPresent()){
            CartItem cartItem = existing.get();
            int newQuantity = cartItem.getQuantity() + dto.getQuantity();

            if(book.getQuantity()<newQuantity){
                throw  new CustomExceptionHandel("Insufficient Stock.",
                        HttpStatus.BAD_REQUEST.value());
            }
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        }else {
            CartItem cartItem = CartMapper.toEntity(dto,book,cart);
            cartItemRepository.save(cartItem);
        }
        Cart existingCart = cartRepository.findById(cart.getId())
                .orElse(cart);
        return CartMapper.toDto(existingCart);
    }

    @Override
    public CartItemResponseDto updateCartItem(UpdateCartItemDto dto) {
        UserInfo user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new CustomExceptionHandel("Book not found",
                        HttpStatus.NOT_FOUND.value()));

        CartItem cartItem = cartItemRepository.findByCartAndBook(cart, book)
                .orElseThrow(() -> new CustomExceptionHandel("Item not found in cart",
                        HttpStatus.NOT_FOUND.value()));

        if(book.getQuantity()< dto.getQuantity()){
            throw  new CustomExceptionHandel("Insufficient Stock.", HttpStatus.BAD_REQUEST.value());
        }
        cartItem.setQuantity(dto.getQuantity());
        cartItemRepository.save(cartItem);
        Cart existingCart = cartRepository.findById(cart.getId())
                .orElse(cart);
        return CartMapper.toDto(existingCart);
    }

    @Override
    public CartItemResponseDto removeFromCart(Integer bookId) {
        UserInfo user =getCurrentUser();
        Cart cart = getOrCreateCart(user);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomExceptionHandel("Book not found",
                        HttpStatus.NOT_FOUND.value()));

        cartItemRepository.findByCartAndBook(cart, book)
                .ifPresentOrElse(
                        cartItemRepository::delete,
                        ()-> {
                            throw new CustomExceptionHandel("Item Not FOund in cart",
                                    HttpStatus.NOT_FOUND.value());
                        }
                );
        Cart existingCart = cartRepository.findById(cart.getId())
                .orElse(cart);
        return CartMapper.toDto(existingCart);

    }

    @Override
    public void clearCart() {
        UserInfo user = getCurrentUser();
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()->new CustomExceptionHandel("Cart Not Found ",
                        HttpStatus.NOT_FOUND.value()));
        cart.getItems().clear();
        cartRepository.save(cart);

    }
}
