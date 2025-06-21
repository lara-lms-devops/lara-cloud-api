package lara.lara_cloud_api.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

// FIXME: fix the problems with JPA and create the table in the database
@Data
public class Organization implements BaseJpaEntity {
    private UUID uuid;
    private String name;
    private String kubernetesNamespaceUuid;
    private boolean deleted;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
