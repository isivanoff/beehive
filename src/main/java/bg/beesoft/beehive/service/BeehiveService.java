package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.BeehiveAddDTO;
import bg.beesoft.beehive.model.dto.BeehiveEditDTO;
import bg.beesoft.beehive.model.entity.ApiaryEntity;
import bg.beesoft.beehive.model.entity.BeehiveEntity;
import bg.beesoft.beehive.model.entity.QueenEntity;
import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.model.view.BeehiveFullView;
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
                setActive(beehiveAddDTO.isQueenActive()).
                setAlive(beehiveAddDTO.isQueenAlive()).
                setMarked(beehiveAddDTO.isQueenMarked()).
                setDateOfMark(beehiveAddDTO.getDateOfMark()).
                setAlive(beehiveAddDTO.isQueenAlive());
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

    public BeehiveFullView viewById(Long id) {
        return modelMapper.map(beehiveRepository.findById(id), BeehiveFullView.class);
    }

    public void deleteById(Long id) {
        beehiveRepository.deleteById(id);
    }

    public Long findApiaryIdByBeehiveId(Long id) {
        return beehiveRepository.findById(id).orElseThrow().getApiary().getId();
    }

    public BeehiveEditDTO getEditDTOById(Long id) {
        return modelMapper.map(beehiveRepository.findById(id), BeehiveEditDTO.class);
    }

    public void updateBeehive(BeehiveEditDTO beehiveEditDTO) {
        ApiaryEntity apiaryEntity = apiaryService.getById(beehiveEditDTO.getApiaryId());

        BeehiveEntity beehiveEntity = beehiveRepository.findById(beehiveEditDTO.getId()).orElseThrow();

        beehiveEntity.setAlive(beehiveEditDTO.isAlive()).
                setImageUrl(beehiveEditDTO.getImageUrl()).
                setColor(beehiveEditDTO.getColor()).
                setReferenceNumber(beehiveEditDTO.getReferenceNumber())
                .setType(beehiveEditDTO.getType())
                .setApiary(apiaryEntity)
                .setPower(beehiveEditDTO.getPower())
                .setTemperament(beehiveEditDTO.getTemperament())
                .getQueen().setActive(beehiveEditDTO.isQueenActive())
                .setAlive(beehiveEditDTO.isQueenAlive())
                .setMarked(beehiveEditDTO.isQueenMarked())
                .setDateOfMark(beehiveEditDTO.getQueenDateOfMark());

        beehiveRepository.save(beehiveEntity);
    }
}
