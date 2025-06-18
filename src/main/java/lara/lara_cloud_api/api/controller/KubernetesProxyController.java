package lara.lara_cloud_api.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kubernetes")
public class KubernetesProxyController {
    private final RestTemplate restTemplate;
    // TODO add this as a @Value
    private final String k8sApiBase;

    @RequestMapping("/proxy/**")
    public ResponseEntity<?> proxy(HttpServletRequest request,
                                   HttpEntity<String> httpEntity) {
// TODO extract information about the deployment or service like port or image to store it in the database and add to the nginx proxy
        try {
            // TODO move this to a factory class
            String proxyPath = request.getRequestURI().replaceFirst("/api/v1/kubernetes/proxy", "");
            String queryString = request.getQueryString();
            String fullUrl = k8sApiBase + proxyPath + (queryString != null ? "?" + queryString : "");

            // TODO move this to a factory clas and I think I only need to copy the headers and maybe check the kubernetes credentials
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            // Copy incoming headers (optional: whitelist or sanitize here)
            Collections.list(request.getHeaderNames()).forEach(name -> {
                if (!name.equalsIgnoreCase("host")) {
                    headers.addAll(name, Collections.list(request.getHeaders(name)));
                }
            });
            // TODO till here

            // TODO move this to a factory
            HttpMethod method = HttpMethod.valueOf(request.getMethod());
            if (method == null) return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Unsupported method");

            // TODO mve this to a factory
            HttpEntity<String> proxyRequest = new HttpEntity<>(httpEntity.getBody(), headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    fullUrl,
                    method,
                    proxyRequest,
                    String.class
            );

            return ResponseEntity
                    .status(response.getStatusCode())
                    .headers(response.getHeaders())
                    .body(response.getBody());

            // TODO improve this exceptions
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Proxy error: " + e.getMessage());
        }
    }
}
