package lara.lara_cloud_api.api.controller;

import io.kubernetes.client.openapi.ApiClient;
import jakarta.servlet.http.HttpServletRequest;
import lara.lara_cloud_api.api.dto.ProxyDto;
import lara.lara_cloud_api.api.dto.ProxyDtoV2;
import lara.lara_cloud_api.api.service.KubernetesProxyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static java.util.Optional.ofNullable;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kubernetes")
public class KubernetesProxyController {
    private final RestTemplate restTemplate;
    private final KubernetesProxyService service;
    private final ApiClient apiClient;

    @RequestMapping("/proxy/**")
    public ResponseEntity<?> proxy(HttpServletRequest request,
                                   HttpEntity<String> httpEntity) {
        // TODO extract information about the deployment or service like port or image to store it in the database and add to the nginx proxy
        // FIXME add authentication getting the using the kubernetes username and password
        try {
            // FIXME validate the namespace, must be the same of the organization that should be stored in the JWT
//            final ProxyDto<String> proxy = service.createKubernetesProxyDto(
//                    "/api/v1/kubernetes/proxy",
//                    request,
//                    httpEntity.getBody());

            final ProxyDtoV2 proxyV2 = service.createKubernetesProxyDtoV2(
                    "/api/v1/kubernetes/proxy",
                    request,
                    httpEntity.getBody());

            var kubernetesRequest = apiClient.buildRequest(
                    proxyV2.baseUrl(),
                    proxyV2.path(),
                    proxyV2.method(),
                    proxyV2.queryParams(),
                    Collections.emptyList(),
                    proxyV2.body(),
                    proxyV2.headerParams(),
                    Collections.emptyMap(),
                    Collections.emptyMap(),
                    new String[0],
                    null
            );

            var response = apiClient.getHttpClient()
                    .newCall(kubernetesRequest)
                    .execute();

            var responseEntity = ResponseEntity
                    .status(response.code())
                    .headers(headers(response.headers()))
                    .body(response.body().string());

            response.close();

            return responseEntity;
//            ResponseEntity<String> response = restTemplate.exchange(
//                    proxy.fullUrl(),
//                    proxy.method(),
//                    proxy.request(),
//                    String.class
//            );
//
//            return ResponseEntity
//                    .status(response.getStatusCode())
//                    .headers(response.getHeaders())
//                    .body(response.getBody());

            // TODO improve this exceptions
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Proxy error: " + e.getMessage());
        }
    }

    private HttpHeaders headers(Headers headers) {
        var springHeaders = new HttpHeaders();

        headers.names().forEach(name -> springHeaders.add(name, headers.get(name)));

        return springHeaders;
    }
}
