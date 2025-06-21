package lara.lara_cloud_api.api.entities.response;

import lombok.Builder;

@Builder
public record OrganizationResponse(
        String uuid,
        String name,
        String namespaceUuid
) {
}
