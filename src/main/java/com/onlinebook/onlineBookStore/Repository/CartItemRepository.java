package com.onlinebook.onlineBookStore.Repository;

import com.onlinebook.onlineBookStore.Entity.Book;
import com.onlinebook.onlineBookStore.Entity.Cart;
import com.onlinebook.onlineBookStore.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    Optional<CartItem> findByCartAndBook(Cart cart, Book book);
    List<CartItem> findByCart(Cart cart);
    void deleteByCartAndBook(Cart cart, Book book);
    void deleteAllByCart(Cart cart);
}
