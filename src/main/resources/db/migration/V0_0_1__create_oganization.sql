CREATE TABLE IF NOT EXISTS organizations (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    kubernetes_namespace_uuid TEXT NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT false,
    creation_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL
);