package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.TaskAddDTO;
import bg.beesoft.beehive.model.entity.BeehiveEntity;
import bg.beesoft.beehive.model.entity.TaskEntity;
import bg.beesoft.beehive.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

    public TaskAddDTO initializeTask(Long id) {
        return modelMapper.map(beehiveService.findById(id),TaskAddDTO.class);
    }

    public void addTask(TaskAddDTO taskAddDTO, Long id, UserDetails userDetails) {
        BeehiveEntity beehiveEntity = beehiveService.findById(id);
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
}
