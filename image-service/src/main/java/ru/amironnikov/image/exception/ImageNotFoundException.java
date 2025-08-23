package ru.amironnikov.image.exception;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException(Throwable cause) {
        super(cause);
    }
}
