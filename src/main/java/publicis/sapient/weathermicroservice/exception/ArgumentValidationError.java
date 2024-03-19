package publicis.sapient.weathermicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintDeclarationException;

@RestControllerAdvice
public class ArgumentValidationError {
    @ExceptionHandler(ConstraintDeclarationException.class)
    public ResponseEntity<String> handleValidationExceptions(ConstraintDeclarationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request Param");
    }
}
