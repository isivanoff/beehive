package bg.beesoft.beehive.repository;

import bg.beesoft.beehive.model.entity.QueenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueenRepository extends JpaRepository<QueenEntity,Long> {
}
