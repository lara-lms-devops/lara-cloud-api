package lara.lara_cloud_api.repository;

import lara.lara_cloud_api.domain.User;

import java.util.Optional;

public interface UserRepository extends BaseJpaRepository<User> {

    Optional<User> findByEmail(String email);

}
