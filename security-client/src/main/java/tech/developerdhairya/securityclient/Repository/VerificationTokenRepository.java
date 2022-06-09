package tech.developerdhairya.securityclient.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.developerdhairya.securityclient.Entity.VerificationTokenEntity;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity,Long> {

    VerificationTokenEntity findByToken(String token);

    VerificationTokenEntity findByUserId(Long id);
}
