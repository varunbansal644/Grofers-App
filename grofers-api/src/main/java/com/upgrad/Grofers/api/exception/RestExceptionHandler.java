package com.upgrad.Grofers.api.interceptor;

import com.upgrad.Grofers.api.ErrorResponse;
import com.upgrad.Grofers.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<ErrorResponse> restaurantNotFoundException(StoreNotFoundException exc, WebRequest webRequest) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> categoryNotFoundException(CategoryNotFoundException exc, WebRequest webRequest) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<ErrorResponse> authorizationFailedException(AuthorizationFailedException exe,
                                                                      WebRequest request) {

        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
                HttpStatus.FORBIDDEN);

    }
    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<ErrorResponse> couponCodeNotFoundException(CouponNotFoundException exe,
                                                                     WebRequest request) {

        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
                HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(SignUpRestrictedException.class)
    public ResponseEntity<ErrorResponse> signUpRestrictedException(SignUpRestrictedException exe, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> authenticationFailedException(AuthenticationFailedException exe,
                                                                       WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
                HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UpdateCustomerException.class)
    public ResponseEntity<ErrorResponse> updateCustomerFailedFailedException(UpdateCustomerException exe,
                                                                             WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> nullPointerException(NullPointerException exe, WebRequest request)  {
        exe.printStackTrace();
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code("Internal server error").message(exe.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(SaveAddressException.class)
    public ResponseEntity<ErrorResponse> invalidAddressRequestFieldException(SaveAddressException exc, WebRequest webRequest) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> addressNotFoundException(AddressNotFoundException exc, WebRequest webRequest) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()),
                HttpStatus.NOT_FOUND);
    }

    private class CouponNotFoundException extends Throwable {
        public void getCode() {

        }

        public String getErrorMessage() {
        }
    }
}
