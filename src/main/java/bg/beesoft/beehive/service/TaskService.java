package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.TaskAddDTO;
import bg.beesoft.beehive.model.entity.BeehiveEntity;
import bg.beesoft.beehive.model.entity.TaskEntity;
import bg.beesoft.beehive.model.view.TaskFullView;
import bg.beesoft.beehive.model.view.TaskView;
import bg.beesoft.beehive.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {


    private ModelMapper modelMapper;
    private BeehiveService beehiveService;
    private TaskRepository taskRepository;

    public TaskService(ModelMapper modelMapper, BeehiveService beehiveService, TaskRepository taskRepository) {
        this.modelMapper = modelMapper;
        this.beehiveService = beehiveService;
        this.taskRepository = taskRepository;
    }

    public TaskAddDTO initializeTask(Long id,UserDetails userDetails) {
        BeehiveEntity beehive = beehiveService.findById(id,userDetails);
        TaskAddDTO taskAddDTO = modelMapper.map(beehive,TaskAddDTO.class);
        taskAddDTO.setBeehiveId(beehive.getId());
        return taskAddDTO;
    }

    public void addTask(TaskAddDTO taskAddDTO, Long id, UserDetails userDetails) {
        BeehiveEntity beehiveEntity = beehiveService.findById(id,userDetails);
        beehiveEntity.setPower(taskAddDTO.getPower())
                .setTemperament(taskAddDTO.getTemperament())
                .getQueen()
                .setDateOfMark(taskAddDTO.getQueenDateOfMark())
                .setAlive(taskAddDTO.isQueenAlive())
                .setMarked(taskAddDTO.isQueenMarked())
                .setActive(taskAddDTO.isQueenActive());
        beehiveService.save(beehiveEntity);

        TaskEntity taskEntity = modelMapper.map(taskAddDTO,TaskEntity.class);
        taskEntity.setBeehive(beehiveEntity);
        taskRepository.save(taskEntity);
    }

    public Page<TaskView> findAllByBeehiveId(Long beehiveId, Pageable pageable) {
        return taskRepository.findAllByBeehiveId(beehiveId,pageable)
                .map(t->modelMapper.map(t,TaskView.class));
    }

    public TaskFullView findViewById(Long taskId) {
        return modelMapper.map(taskRepository.findById(taskId),TaskFullView.class);
    }

    public boolean deleteById(Long taskId) {
        if(taskRepository.existsById(taskId)){
             taskRepository.deleteById(taskId);
            return true;
        }
        return false;
    }
}
