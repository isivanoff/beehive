package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.BeehiveAddDTO;
import bg.beesoft.beehive.model.entity.ApiaryEntity;
import bg.beesoft.beehive.model.entity.BeehiveEntity;
import bg.beesoft.beehive.model.entity.QueenEntity;
import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.model.view.BeehiveView;
import bg.beesoft.beehive.repository.BeehiveRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeehiveService {

    private ModelMapper modelMapper;
    private QueenService queenService;
    private ApiaryService apiaryService;
    private UserService userService;
    private BeehiveRepository beehiveRepository;

    public BeehiveService(ModelMapper modelMapper, QueenService queenService, ApiaryService apiaryService, UserService userService, BeehiveRepository beehiveRepository) {
        this.modelMapper = modelMapper;
        this.queenService = queenService;
        this.apiaryService = apiaryService;
        this.userService = userService;
        this.beehiveRepository = beehiveRepository;
    }

    public void addBeehive(BeehiveAddDTO beehiveAddDTO, String username) {
        BeehiveEntity beehiveEntity = modelMapper.map(beehiveAddDTO, BeehiveEntity.class);
        QueenEntity queenEntity = new QueenEntity().
                setActive(beehiveAddDTO.isQueenIsActive()).
                setAlive(beehiveAddDTO.isQueenIsAlive()).
                setMarked(beehiveAddDTO.isQueenIsMarked()).
                setDateOfMark(beehiveAddDTO.getDateOfMark()).
                setAlive(beehiveAddDTO.isQueenIsAlive());
        beehiveEntity.setId(null);

        queenService.add(queenEntity);
        beehiveEntity.setQueen(queenEntity);

        ApiaryEntity apiaryEntity = apiaryService.getById(beehiveAddDTO.getApiaryId());
        beehiveEntity.setApiary(apiaryEntity);

        UserEntity userEntity = userService.findByEmail(username);
        beehiveEntity.setBeekeeper(userEntity);

        beehiveRepository.save(beehiveEntity);
    }

    public List<BeehiveView> findViewAllByApiaryId(Long id) {
        return beehiveRepository.findByApiaryId(id)
                .stream()
                .map(b -> modelMapper.map(b, BeehiveView.class))
                .collect(Collectors.toList());
    }
}
