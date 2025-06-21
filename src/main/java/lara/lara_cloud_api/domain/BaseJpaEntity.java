package lara.lara_cloud_api.domain;

import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BaseJpaEntity {
    @Id
    @UuidGenerator
    void setUuid(UUID uuid); // TODO add a numeric id and the uuid for front outside access?

    void setDeleted(boolean deleted);
    // TODO check what should be the date type and check if it is possible to put with timezone
    @CreationTimestamp
    void setCreationDate(LocalDateTime localDateTime);
    @UpdateTimestamp
    void setUpdateDate(LocalDateTime localDateTime);
}
