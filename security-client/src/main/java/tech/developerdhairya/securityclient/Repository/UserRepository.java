package tech.developerdhairya.securityclient.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.developerdhairya.securityclient.Entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
}
