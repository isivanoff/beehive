package bg.beesoft.beehive.repository;

import bg.beesoft.beehive.model.entity.BeehiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeehiveRepository extends JpaRepository<BeehiveEntity, Long> {
    List<BeehiveEntity> findByApiaryId(Long id);
}
