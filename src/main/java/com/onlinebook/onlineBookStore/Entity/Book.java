package com.onlinebook.onlineBookStore.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name ="Title", nullable = false, length = 200)
    private String title;

    @Column(name ="Author", nullable = false, length = 150)
    private String author;

    @Column(name ="Price", nullable = false)
    private Double price;

    @Column(name = "Published_Date", length = 12)
    private LocalDate publishedDate;

    @Column(name="Category", length = 30)
    private String category;

    @Column(name = "Cover_image")
    private String coverImage;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedDate;

    @Column(nullable = false)
    private Integer quantity = 0;

}
