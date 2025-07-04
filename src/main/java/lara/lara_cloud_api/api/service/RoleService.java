package lara.lara_cloud_api.api.service;

import lara.lara_cloud_api.domain.Organization;
import lara.lara_cloud_api.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    public List<?> getRoles(User user, Organization organization) {
        // TODO get organization roles if user is no super
        // TODO if user have super role, only return the super role
        return null;
    }

    public Object getSuperRole(User user) {
        // TODO implement get super role
        return null;
    }
}
