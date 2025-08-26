package com.onlinebook.onlineBookStore.Repository;

import com.onlinebook.onlineBookStore.Entity.Book;
import com.onlinebook.onlineBookStore.Entity.Cart;
import com.onlinebook.onlineBookStore.Entity.CartItem;
import com.onlinebook.onlineBookStore.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

//    List<CartItem> findByUser(UserInfo user);
//    Optional<CartItem> findByUserAndBook(UserInfo user, Book book);
//    void deleteAllByUser(UserInfo user);
    Optional<Cart> findByUser(UserInfo user);
    Optional<Cart> findByUserId(Integer userId);

}
