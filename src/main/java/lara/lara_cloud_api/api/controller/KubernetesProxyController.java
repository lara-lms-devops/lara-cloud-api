package lara.lara_cloud_api.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import lara.lara_cloud_api.api.dto.ProxyDto;
import lara.lara_cloud_api.api.service.KubernetesProxyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kubernetes")
public class KubernetesProxyController {
    private final RestTemplate restTemplate;
    private final KubernetesProxyService service;

    @RequestMapping("/proxy/**")
    public ResponseEntity<?> proxy(HttpServletRequest request,
                                   HttpEntity<String> httpEntity) {
        // TODO extract information about the deployment or service like port or image to store it in the database and add to the nginx proxy
        // FIXME add authentication getting the using the kubernetes username and password
        try {
            final ProxyDto<String> proxy = service.createKubernetesProxyDto(
                    "/api/v1/kubernetes/proxy",
                    request,
                    httpEntity.getBody());

            ResponseEntity<String> response = restTemplate.exchange(
                    proxy.fullUrl(),
                    proxy.method(),
                    proxy.request(),
                    String.class
            );

            return ResponseEntity
                    .status(response.getStatusCode())
                    .headers(response.getHeaders())
                    .body(response.getBody());

            // TODO improve this exceptions
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Proxy error: " + e.getMessage());
        }
    }
}
