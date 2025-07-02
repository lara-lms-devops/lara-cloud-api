package lara.lara_cloud_api.api.error;

import jakarta.servlet.http.HttpServletRequest;
import lara.lara_cloud_api.api.error.exception.HttpStatusException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandlerAdvice {
    private final ErrorFactory errorFactory;

    @ExceptionHandler(HttpStatusException.class)
    public ResponseEntity<ErrorMessage> httpError(HttpServletRequest request, HttpStatusException exception) {
        var errorMessage = errorFactory.of(exception.getStatusCode(), exception.getMessage());

        return ResponseEntity
                .status(exception.getStatusCode())
                .body(errorMessage);
    }
}
