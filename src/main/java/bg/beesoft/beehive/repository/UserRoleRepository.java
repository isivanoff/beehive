package bg.beesoft.beehive.repository;

import bg.beesoft.beehive.model.entity.UserRoleEntity;
import bg.beesoft.beehive.model.entity.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    UserRoleEntity findByUserRole(UserRoleEnum role);
}
