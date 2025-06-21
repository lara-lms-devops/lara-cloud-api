package lara.lara_cloud_api.domain;

import lombok.Data;
// FIXME: fix the problems with JPA and create the table in the database
@Data
public class Organization extends BaseEntity {
    private String name;
    private String kubernetesNamespaceUuid;
}
