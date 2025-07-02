package lara.lara_cloud_api.api.service;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.AuthenticationV1TokenRequest;
import io.kubernetes.client.openapi.models.V1TokenRequestSpec;
import io.kubernetes.client.openapi.models.V1TokenRequestStatus;
import lara.lara_cloud_api.api.error.exception.HttpStatusException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class KubernetesAuthService {
    private final CoreV1Api coreV1Api;

    @Value("${kubernetes.super.user.service.account.name}")
    private String serviceAccountName;

    public Optional<String> createToken(String namespace) {
        var authenticationV1TokenRequest = new AuthenticationV1TokenRequest()
                .spec(new V1TokenRequestSpec()
                        .expirationSeconds(3600L));

        var response = coreV1Api.createNamespacedServiceAccountToken(
                serviceAccountName,
                namespace,
                authenticationV1TokenRequest
        );

        try {
            var request = response.execute();
            return ofNullable(request.getStatus())
                    .map(V1TokenRequestStatus::getToken);
        } catch (ApiException e) {
            throw new HttpStatusException(e.getCode(), e.getMessage());
        }
    }
}
