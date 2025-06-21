package lara.lara_cloud_api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
public abstract class BaseEntity { // TODO transform this in an interface?
    @Id
    @UuidGenerator
    protected UUID uuid;
    protected boolean deleted;
    // TODO add creation and update date
}
