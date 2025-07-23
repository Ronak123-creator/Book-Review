package com.onlinebook.onlineBookStore.DTO;

import jakarta.validation.constraints.NotBlank;


public class CreateBookDTO {

    @NotBlank(message = "Title is required..")
    private String title;
    @NotBlank(message = "Author is required..")
    private String author;
    @NotBlank(message = "Published date is required..")
    private String publicationDate;
    @NotBlank(message = "Category is required..")
    private String category;
    @NotBlank(message = "Image Url is required..")
    private String imageUrl;
}
