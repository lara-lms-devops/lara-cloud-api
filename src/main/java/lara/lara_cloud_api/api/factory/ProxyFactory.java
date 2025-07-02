package lara.lara_cloud_api.api.factory;

import io.kubernetes.client.openapi.Pair;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

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

    public String getProxyPath(String endpointPath, HttpServletRequest request) {
        return request.getRequestURI().replaceFirst(endpointPath, "");
    }

    private @Nullable String getQueryString(HttpServletRequest request) {
        return request.getQueryString();
    }

    public List<Pair> getQueryParams(HttpServletRequest request) {
        var queryParamMap = request.getParameterMap();

        return queryParamMap.keySet().stream()
                .map(key -> createPairs(key, queryParamMap.get(key)))
                .flatMap(Collection::stream)
                .toList();
    }

    private List<Pair> createPairs(String name, String[] values) {
        return Arrays.stream(values)
                .map(value -> new Pair(name, value))
                .toList();
    }

}
