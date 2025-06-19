package lara.lara_cloud_api.api.dto;

import lombok.Builder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@Builder
public record ProxyDto<T>(
        String fullUrl,
        HttpMethod method,
        HttpEntity<T> request
) {
}
