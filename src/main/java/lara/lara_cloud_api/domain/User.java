package lara.lara_cloud_api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")// FIXME create it in the sql migration
public class User implements BaseJpaEntity {
    @Id
    @UuidGenerator
    private UUID uuid;

    private String name;
    private String email; // TODO create this as unique
    private String hashedPassword;

    private boolean deleted;
    @CreationTimestamp
    private LocalDateTime creationDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;
}
