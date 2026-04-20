package com.samadihadis.loanmanagementsystem.exception;


import com.samadihadis.loanmanagementsystem.exception.customer.CustomerNotFoundException;
import com.samadihadis.loanmanagementsystem.exception.customer.DuplicateEmailException;
import com.samadihadis.loanmanagementsystem.exception.customer.InvalidEmailException;
import com.samadihadis.loanmanagementsystem.exception.loan.InvalidLoanDataException;
import com.samadihadis.loanmanagementsystem.exception.loan.LoanNotFoundException;
import com.samadihadis.loanmanagementsystem.exception.loan.LoanStatusException;
import com.samadihadis.loanmanagementsystem.exception.payment.InvalidPaymentAmountException;
import com.samadihadis.loanmanagementsystem.exception.payment.PaymentNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    //Customer Exception
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(
            CustomerNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                404,
                "Not Found",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmail(
            DuplicateEmailException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                409,
                "Conflict",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEmail(
            InvalidEmailException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                400,
                "Bad Request",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    //Catch-all
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                "خطای داخلی سرور: " + ex.getMessage(),
                500,
                "Internal Server Error",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    //Loan Exception
    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFound(
            LoanNotFoundException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidLoanDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidLoanData(
            InvalidLoanDataException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(LoanStatusException.class)
    public ResponseEntity<ErrorResponse> handleLoanStatus(
            LoanStatusException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Payment Exception
    @ExceptionHandler(InvalidPaymentAmountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPaymentAmount(
            InvalidPaymentAmountException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentNotFound(
            PaymentNotFoundException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
