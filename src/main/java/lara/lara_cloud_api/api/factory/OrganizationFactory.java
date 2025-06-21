package lara.lara_cloud_api.api.factory;

import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import lara.lara_cloud_api.api.entities.request.OrganizationRequest;
import lara.lara_cloud_api.domain.Organization;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class OrganizationFactory {

    public String createNameSpaceName(Organization organization) {
        return ""; // TODO
    }

    public Organization toOrganization(OrganizationRequest request, V1Namespace namespace) {
        var organization = new Organization();
        organization.setName(request.name());
        organization.setKubernetesNamespaceUuid(getNamespaceUuid(namespace));
        return organization;
    }

    private String getNamespaceUuid(V1Namespace namespace) {
        return ofNullable(namespace.getMetadata())
                        .map(V1ObjectMeta::getUid)
                        .orElseThrow(RuntimeException::new); // TODO improve
    }
}
