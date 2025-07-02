package lara.lara_cloud_api.api.error;

import lombok.Data;

@Data
public class ErrorMessage {
    private int httpCode;
    private String message;
}
