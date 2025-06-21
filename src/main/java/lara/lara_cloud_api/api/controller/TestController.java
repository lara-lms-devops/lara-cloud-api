package lara.lara_cloud_api.api.controller;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1NamespaceList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final CoreV1Api kubernetesApi;

    @GetMapping("/test")
    public V1NamespaceList get() throws ApiException {
        return kubernetesApi.listNamespace()
                .execute();
    }
}
