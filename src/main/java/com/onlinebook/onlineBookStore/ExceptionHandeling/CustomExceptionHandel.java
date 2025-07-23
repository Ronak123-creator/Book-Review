package com.onlinebook.onlineBookStore.ExceptionHandeling;

public class CustomExceptionHandel extends RuntimeException {

  public Integer status;

  public CustomExceptionHandel(String message, Integer status) {
        super(message);
        this.status = status;

    }

    public Integer getStatus() {
        return status;
    }
}
