package lara.lara_cloud_api.api.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import lara.lara_cloud_api.api.entities.request.OrganizationRequest;
import lara.lara_cloud_api.api.factory.KubernetesFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KubernetesService {
    private final CoreV1Api kubernetes;
    private final KubernetesFactory factory;

    public List<V1Namespace> getNamespaces() {
        try {
            return kubernetes.listNamespace()
                    .execute()
                    .getItems();
        } catch (ApiException e) {
            throw new RuntimeException(e); // TODO improve
        }
    }

    public V1Namespace createNamespace(OrganizationRequest organization) {
        var namespace = factory.createNameSpace(organization);

        try {
            return kubernetes.createNamespace(namespace)
                    .execute();
        } catch (ApiException e) {
            throw new RuntimeException(e); // TODO map this to a HTTP exception later on
        }
    }
}
