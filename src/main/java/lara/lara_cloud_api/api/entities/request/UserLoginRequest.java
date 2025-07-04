package lara.lara_cloud_api.api.entities.request;

public record UserLoginRequest(
        String email,
        String password
) {
    // FIXME add validations
}
