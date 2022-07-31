package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.BeehiveAddDTO;
import bg.beesoft.beehive.model.dto.BeehiveEditDTO;
import bg.beesoft.beehive.model.entity.ApiaryEntity;
import bg.beesoft.beehive.model.entity.BeehiveEntity;
import bg.beesoft.beehive.model.entity.QueenEntity;
import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.model.exception.NotFoundException;
import bg.beesoft.beehive.model.exception.UnauthorizedRequestException;
import bg.beesoft.beehive.model.view.BeehiveFullView;
import bg.beesoft.beehive.model.view.BeehiveView;
import bg.beesoft.beehive.repository.BeehiveRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

        ApiaryEntity apiaryEntity = apiaryService.findById(beehiveAddDTO.getApiaryId());
        beehiveEntity.setApiary(apiaryEntity);

        UserEntity userEntity = userService.findByEmail(username);
        beehiveEntity.setBeekeeper(userEntity);

        beehiveRepository.save(beehiveEntity);
    }

    public Page<BeehiveView> findViewAllByApiaryId(Long apiaryId, Pageable pageable, UserDetails userDetails) {
        Page<BeehiveView> beehiveViews = beehiveRepository.findByApiaryId(apiaryId, pageable)
                .map(b -> modelMapper.map(b, BeehiveView.class));

        if (!apiaryService.findById(apiaryId).getBeekeeper().getEmail().equals(userDetails.getUsername())) {
            throw new UnauthorizedRequestException("Нямате достъп до този пчелин.");
        }

        return beehiveViews;
    }

    public BeehiveFullView viewById(Long id, UserDetails userDetails) {
        BeehiveEntity beehiveEntity = findById(id);

        if (!beehiveEntity.getBeekeeper().getEmail().equals(userDetails.getUsername())) {
            throw new UnauthorizedRequestException("Нямате достъп до този пчелин.");
        }

        return modelMapper.map(beehiveEntity, BeehiveFullView.class);
    }

    public void deleteById(Long id, UserDetails userDetails) {


        BeehiveEntity beehiveEntity = findById(id);

        if (!beehiveEntity.getBeekeeper().getEmail().equals(userDetails.getUsername())) {
            throw new UnauthorizedRequestException("Нямате достъп до този пчелин.");
        }

        beehiveRepository.deleteById(id);
    }

    public Long findApiaryIdByBeehiveId(Long id, UserDetails userDetails) {
        BeehiveEntity beehiveEntity = findById(id);

        if (!beehiveEntity.getBeekeeper().getEmail().equals(userDetails.getUsername())) {
            throw new UnauthorizedRequestException("Нямате достъп до този пчелин.");
        }

        return beehiveEntity.getApiary().getId();

    }

    public BeehiveEditDTO getEditDTOById(Long id, UserDetails userDetails) {
        BeehiveEntity beehiveEntity = findById(id);

        if (!beehiveEntity.getBeekeeper().getEmail().equals(userDetails.getUsername())) {
            throw new UnauthorizedRequestException("Нямате достъп до този пчелин.");
        }

        return modelMapper.map(beehiveEntity, BeehiveEditDTO.class);
    }

    public void updateBeehive(BeehiveEditDTO beehiveEditDTO, UserDetails userDetails, Long id) {
        BeehiveEntity beehiveEntity = findById(id);

        if (!beehiveEntity.getBeekeeper().getEmail().equals(userDetails.getUsername())) {
            throw new UnauthorizedRequestException("Нямате достъп до този пчелин.");
        }

        ApiaryEntity apiaryEntity = apiaryService.findById(beehiveEditDTO.getApiaryId());


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

    public BeehiveEntity findById(Long id){
        return beehiveRepository.findById(id).orElseThrow(() -> new NotFoundException("Кошерът не е намерен."));
    }

    public BeehiveEntity findById(Long id, UserDetails userDetails) {
        BeehiveEntity beehiveEntity = findById(id);

        if (!beehiveEntity.getBeekeeper().getEmail().equals(userDetails.getUsername())) {
            throw new UnauthorizedRequestException("Нямате достъп до този кошер.");
        }
        return beehiveEntity;
    }

    public void save(BeehiveEntity beehiveEntity) {
        beehiveRepository.save(beehiveEntity);
    }
}
