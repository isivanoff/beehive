package bg.beesoft.beehive.repository;

import bg.beesoft.beehive.model.entity.BeehiveEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BeehiveRepository extends JpaRepository<BeehiveEntity, Long>, JpaSpecificationExecutor<BeehiveEntity> {
    Page<BeehiveEntity> findByApiaryId(Long id, Pageable pageable);
}
