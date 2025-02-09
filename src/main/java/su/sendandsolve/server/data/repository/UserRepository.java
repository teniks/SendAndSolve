package su.sendandsolve.server.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import su.sendandsolve.server.data.domain.User;

import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.login = :login")
    @Transactional(readOnly = true)
    User findByLogin(@Param("login") String login);
}
