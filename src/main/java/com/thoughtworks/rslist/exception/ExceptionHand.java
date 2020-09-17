package com.thoughtworks.rslist.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHand {
    public static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler({IndexOutOfBoundsException.class,
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
        logger.error(ex.getMessage());
        return ResponseEntity.status(400).body(commentError);
    }
}
