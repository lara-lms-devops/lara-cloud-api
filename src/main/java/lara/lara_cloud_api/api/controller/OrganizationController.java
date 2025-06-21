package lara.lara_cloud_api.api.controller;

import lara.lara_cloud_api.api.converter.OrganizationConverter;
import lara.lara_cloud_api.api.entities.request.OrganizationRequest;
import lara.lara_cloud_api.api.entities.response.OrganizationResponse;
import lara.lara_cloud_api.api.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService service;
    private final OrganizationConverter converter;

    @PostMapping
    public OrganizationResponse create(@Validated @RequestBody OrganizationRequest request) {
        // FIXME add authentication and authorization, this should be executed only by SUPER
        var organization = service.create(request);
        return converter.toResponse(organization);
    }
}
