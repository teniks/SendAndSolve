package su.sendandsolve.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import su.sendandsolve.server.data.domain.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // Spring автоматически сгенерирует запрос SELECT * FROM Users WHERE login = ?.
    User findByLogin(String login);
}
