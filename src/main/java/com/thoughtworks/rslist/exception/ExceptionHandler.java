package com.thoughtworks.rslist.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({IndexOutOfBoundsException.class,
            InvalidIndexException.class,
            MethodArgumentNotValidException.class})
    public ResponseEntity<CommentError> handleIndexOutOfBoundsException(Exception ex) {
        CommentError commentError = new CommentError();
        if (ex instanceof IndexOutOfBoundsException) {
            commentError.setError("invalid request param");
        }
        if (ex instanceof InvalidIndexException) {
            commentError.setError("invalid index");
        }
        if (ex instanceof MethodArgumentNotValidException) {
            commentError.setError("invalid param");
        }

        return ResponseEntity.status(400).body(commentError);
    }
}
