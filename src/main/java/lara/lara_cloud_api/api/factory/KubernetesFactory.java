package lara.lara_cloud_api.api.factory;

import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import lara.lara_cloud_api.api.entities.request.OrganizationRequest;
import org.springframework.stereotype.Component;

@Component
public class KubernetesFactory {

    public V1Namespace createNameSpace(OrganizationRequest organization) {
        var namespace = new V1Namespace();
        namespace.setMetadata(createMetadata(organization));
        return namespace;
    }

    private V1ObjectMeta createMetadata(OrganizationRequest organization) {
        final String name = createNameSpaceName(organization);
        var metadata = new V1ObjectMeta();
        metadata.setName(name);
        return metadata;
    }
// TODO create ut
    private String createNameSpaceName(OrganizationRequest organization) {
        return organization.name()
                .toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-");
    }

}
