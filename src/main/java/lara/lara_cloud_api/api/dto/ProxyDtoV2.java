package lara.lara_cloud_api.api.dto;

import io.kubernetes.client.openapi.ApiCallback;
import io.kubernetes.client.openapi.Pair;
import lombok.Builder;

@Builder
public record ProxyDtoV2(
        String baseUrl,
        String path,
        String method,
        java.util.List<Pair> queryParams,
        java.util.List<Pair> collectionQueryParams,
        Object body,
        java.util.Map<String, String> headerParams,
        java.util.Map<String, String> cookieParams,
        java.util.Map<String, Object> formParams,
        String[] authNames,
        ApiCallback callback
) {
}
