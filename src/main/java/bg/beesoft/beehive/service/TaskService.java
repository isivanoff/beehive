package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.TaskAddDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TaskService {


    private ModelMapper modelMapper;
    private BeehiveService beehiveService;

    public TaskService(ModelMapper modelMapper, BeehiveService beehiveService) {
        this.modelMapper = modelMapper;
        this.beehiveService = beehiveService;
    }

    public TaskAddDTO initializeTask(Long id) {
        return modelMapper.map(beehiveService.findById(id),TaskAddDTO.class);
    }
}
