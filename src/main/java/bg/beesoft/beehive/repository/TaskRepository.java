package bg.beesoft.beehive.repository;

import bg.beesoft.beehive.model.entity.TaskEntity;
import bg.beesoft.beehive.model.view.TaskView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findAllByBeehiveId(Long beehiveId);
}
