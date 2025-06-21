package lara.lara_cloud_api.api.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;

@Configuration
public class KubernetesClientConfig {
    @Value("${kubernetes.kube.config}")
    private String kubernetesConfigFilePath;

    @Bean
    public ApiClient apiClient() throws IOException {
        // TODO solve this problems with the certificate
        // TODO create this using Config.defaultClient(); for profile that is not local, since it will take the credentials direct from the cluster
        return Config.fromConfig(new FileReader(kubernetesConfigFilePath));
    }

    @Bean
    public CoreV1Api v1Api(ApiClient client) {
        return new CoreV1Api(client);
    }
}
