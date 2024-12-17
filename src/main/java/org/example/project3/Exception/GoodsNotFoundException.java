package org.example.project3.Exception;

import lombok.Getter;

@Getter
public class GoodsNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    public GoodsNotFoundException(String message) {
        this.message = message;
    }
}
