package lara.lara_cloud_api.api.service;

import lara.lara_cloud_api.api.entities.request.OrganizationRequest;
import lara.lara_cloud_api.domain.Organization;
import lara.lara_cloud_api.repository.OrganizationRepository;
import lara.lara_cloud_api.api.factory.OrganizationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository repository;
    private final OrganizationFactory factory;
    private final KubernetesService kubernetesService;

    public Organization create(OrganizationRequest request) {
        var namespace = kubernetesService.createNamespace(request);
        var organization = factory.toOrganization(request, namespace);

        return save(organization);
    }

    public Organization save(Organization organization) {
        return repository.save(organization);
    }

    private void createNameSpace(Organization organization) {
        if (isEmpty(organization.getKubernetesNamespaceUuid()) && isNamespaceInUse(organization)) {
            throw new RuntimeException(); // TODO improve
        }


    }

    private boolean isNamespaceInUse(Organization organization) {
        final String namespaceName = factory.createNameSpaceName(organization);
        var namespaces = kubernetesService.getNamespaces();

        return namespaces.stream()
                .filter(item -> nonNull(item.getMetadata()))
                .filter(item -> isNotBlank(item.getMetadata().getName()))
                .anyMatch(item -> item.getMetadata().getName().equals(namespaceName));
    }
}
