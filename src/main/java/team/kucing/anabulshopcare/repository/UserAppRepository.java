package team.kucing.anabulshopcare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.kucing.anabulshopcare.entity.UserApp;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, UUID> {
    boolean existsByEmail(String email);

    UserApp findByEmail(String email);

    Optional<UserApp> findById(UUID id);

}