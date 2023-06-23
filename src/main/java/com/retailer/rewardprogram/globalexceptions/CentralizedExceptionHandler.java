//package com.retailer.rewardprogram.globalexceptions;
//
//import com.retailer.rewardprogram.dto.error.ErrorResponse;
//import org.springframework.context.support.DefaultMessageSourceResolvable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * This class defines the centralized exception handling.
// * We have defined an exception handler to capture any invalid input passed in the request
// * and provide useful feedback to the user. It captures and responds with invalid field.
// */
//@RestControllerAdvice
//public class CentralizedExceptionHandler {
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
//        List<String> errors = ex.getBindingResult()
//                .getAllErrors()
//                .stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .collect(Collectors.toList());
//
////        ErrorResponse errorResponse = ErrorResponse.builder()
////                .message("Validation failed")
////                .errors(errors)
////                .build();
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//    }
//}
