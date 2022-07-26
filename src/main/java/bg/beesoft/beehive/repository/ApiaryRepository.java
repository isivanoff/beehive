package bg.beesoft.beehive.repository;

import bg.beesoft.beehive.model.entity.ApiaryEntity;
import bg.beesoft.beehive.service.ApiaryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiaryRepository extends JpaRepository<ApiaryEntity,Long> {
    List<ApiaryEntity> findByBeekeeperEmail(String email);
}
