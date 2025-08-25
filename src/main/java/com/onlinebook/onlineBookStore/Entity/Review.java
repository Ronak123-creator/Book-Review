package com.onlinebook.onlineBookStore.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="book_id", nullable = false)
    private Book book;

//    @Column(nullable = false)
//    private String reviewerName;

    @Column(nullable = false)
    private Integer rating;

    private  String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;
}
