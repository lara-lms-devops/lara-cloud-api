package lara.lara_cloud_api.api.factory;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class ProxyFactory {

    public String createUrl(String baseUrl, String endpointPath, HttpServletRequest request) {
        var urlBuilder = new StringBuilder();
        urlBuilder.append(baseUrl);
        urlBuilder.append(getProxyPath(endpointPath, request));
        ofNullable(getQueryString(request)).ifPresent(urlBuilder::append);

        return urlBuilder.toString();
    }

    public HttpHeaders copyHeaders(HttpServletRequest request) {
        var headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        Collections.list(request.getHeaderNames()).forEach(name -> {
            if (!name.equalsIgnoreCase("host")) {
                headers.addAll(name, Collections.list(request.getHeaders(name)));
            }
        });

        return headers;
    }

    public <T> HttpEntity<T> createProxyRequest(T body, HttpHeaders headers) {
        return new HttpEntity<>(body, headers);
    }

    public HttpMethod getHttpMethod(HttpServletRequest request) {
        return HttpMethod.valueOf(request.getMethod());
    }

    private String getProxyPath(String endpointPath, HttpServletRequest request) {
        return request.getRequestURI().replaceFirst(endpointPath, "");
    }

    private @Nullable String getQueryString(HttpServletRequest request) {
        return request.getQueryString();
    }
}
