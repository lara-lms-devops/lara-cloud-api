package lara.lara_cloud_api.api.entities.request;

// FIXME add validations
public record UserRegistrationRequest(
        String name,
        String email, // TODO add last name
        String password
) {
}
