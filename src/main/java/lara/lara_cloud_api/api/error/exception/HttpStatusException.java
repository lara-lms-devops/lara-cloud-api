package lara.lara_cloud_api.api.error.exception;

import lombok.Getter;

@Getter
public class HttpStatusException extends RuntimeException {
    private final int statusCode;

    public HttpStatusException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
