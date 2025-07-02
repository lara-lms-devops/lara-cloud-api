package lara.lara_cloud_api.api.service;

import io.kubernetes.client.openapi.ApiClient;
import jakarta.servlet.http.HttpServletRequest;
import lara.lara_cloud_api.api.dto.ProxyDto;
import lara.lara_cloud_api.api.dto.ProxyDtoV2;
import lara.lara_cloud_api.api.factory.ProxyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KubernetesProxyService {
    private final ProxyFactory proxyFactory;
    private final KubernetesAuthService kubernetesAuthService;

    @Value("${k3s.cluster.jwt}")
    private String k3sClusterJwt;

    @Value("${kubernetes.api.url}")
    private String kubernetesUrl;
// FIXME get the JWT using the kubernetes client or other way
    public ProxyDto<String> createKubernetesProxyDto(String endpointUrl,
                                                     HttpServletRequest request,
                                                     String body) {

        final HttpHeaders headers = proxyFactory.copyHeaders(request);

        // TODO improve this
        // FIXME extract the namespace
        headers.add("Authorization",
                String.format("Bearer %s", kubernetesAuthService.createToken("default")));

        return ProxyDto.<String>builder()
                .fullUrl(proxyFactory.createUrl(kubernetesUrl, endpointUrl, request))
                .method(proxyFactory.getHttpMethod(request))
                .request(proxyFactory.createProxyRequest(body, headers))
                .build();
    }

    public ProxyDtoV2 createKubernetesProxyDtoV2(String endpointUrl,
                                                 HttpServletRequest request,
                                                 String body) {

        return ProxyDtoV2.builder()
                .baseUrl(kubernetesUrl)
                .path(proxyFactory.getProxyPath(endpointUrl, request))
                .method(proxyFactory.getHttpMethod(request).name())
                .queryParams(proxyFactory.getQueryParams(request))
                .body(body)
                .headerParams(proxyFactory.copyHeaders(request).asSingleValueMap())
                .build();
    }
}
