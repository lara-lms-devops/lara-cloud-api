package lara.lara_cloud_api.api.service;

import lara.lara_cloud_api.domain.User;
import lara.lara_cloud_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Optional<User> findUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public Optional<User> findById(UUID uuid) {
        return repository.findById(uuid);
    }
}
