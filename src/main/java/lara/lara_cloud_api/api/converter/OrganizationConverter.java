package lara.lara_cloud_api.api.converter;

import lara.lara_cloud_api.api.entities.response.OrganizationResponse;
import lara.lara_cloud_api.domain.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationConverter {

    public OrganizationResponse toResponse(Organization organization) {
        return OrganizationResponse.builder()
                .uuid(organization.getUuid().toString())
                .name(organization.getName())
                .namespaceUuid(organization.getKubernetesNamespaceUuid()) // TODO add the namespace name
                .build();
    }
}
