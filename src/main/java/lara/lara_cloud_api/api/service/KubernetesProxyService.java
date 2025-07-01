package lara.lara_cloud_api.api.service;

import jakarta.servlet.http.HttpServletRequest;
import lara.lara_cloud_api.api.dto.ProxyDto;
import lara.lara_cloud_api.api.factory.ProxyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KubernetesProxyService {
    private final ProxyFactory proxyFactory;

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
        headers.add("Authorization", String.format("Bearer %s", k3sClusterJwt));

        return ProxyDto.<String>builder()
                .fullUrl(proxyFactory.createUrl(kubernetesUrl, endpointUrl, request))
                .method(proxyFactory.getHttpMethod(request))
                .request(proxyFactory.createProxyRequest(body, headers))
                .build();
    }
}
