package lara.lara_cloud_api.repository;

import lara.lara_cloud_api.domain.BaseJpaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface BaseJpaRepository<T extends BaseJpaEntity> extends CrudRepository<T, UUID> {
    // FIXME: implement the behavior
    void softDelete(T entity);
    void softDeleteByUuid(UUID uuid);
}
