package lara.lara_cloud_api.api.error;

import org.springframework.stereotype.Component;

@Component
public class ErrorFactory {

    // FIXME add internationalization
    public ErrorMessage of(int httpCode, String message) {
        var error = new ErrorMessage();
        error.setHttpCode(httpCode);
        error.setMessage(message);
        return error;
    }
}
