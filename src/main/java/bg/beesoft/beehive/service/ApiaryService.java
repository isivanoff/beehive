package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.ApiaryAddDTO;
import bg.beesoft.beehive.model.dto.ApiaryEditDTO;
import bg.beesoft.beehive.model.entity.AddressEntity;
import bg.beesoft.beehive.model.entity.ApiaryEntity;
import bg.beesoft.beehive.model.entity.BeehiveEntity;
import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.model.view.ApiaryView;
import bg.beesoft.beehive.model.view.BeehiveView;
import bg.beesoft.beehive.repository.ApiaryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApiaryService {

    private ApiaryRepository apiaryRepository;
    private ModelMapper modelMapper;
    private AddressService addressService;
    private UserService userService;

    public ApiaryService(ApiaryRepository apiaryRepository, ModelMapper modelMapper, AddressService addressService, UserService userService) {
        this.apiaryRepository = apiaryRepository;
        this.modelMapper = modelMapper;
        this.addressService = addressService;
        this.userService = userService;
    }

    public void save(ApiaryAddDTO apiaryAddDTO, UserDetails userDetails) {
        ApiaryEntity apiaryEntity = new ApiaryEntity().
                setName(apiaryAddDTO.getName())
                .setDescription(apiaryAddDTO.getDescription())
                .setArea(apiaryAddDTO.getArea())
                .setImageUrl(apiaryAddDTO.getImageURL());

        AddressEntity address = new AddressEntity().
                setCity(apiaryAddDTO.getCity()).
                setCountry(apiaryAddDTO.getCountry()).
                setPostcode(apiaryAddDTO.getPostcode()).
                setStreet(apiaryAddDTO.getStreet());

        addressService.add(address);
        apiaryEntity.setAddress(address);


        UserEntity userEntity = userService.findByEmail(userDetails.getUsername());
        apiaryEntity.setBeekeeper(userEntity);

        apiaryRepository.save(apiaryEntity);
    }

    public List<ApiaryView> findAllApiaries(String email) {

        List<ApiaryEntity> apiaries = apiaryRepository.findByBeekeeperEmail(email);
        List<ApiaryView> apiaryViews = apiaries
                .stream()
                .map(e -> modelMapper.map(e, ApiaryView.class))
                .collect(Collectors.toList());

//        apiaries.stream().forEach(ap-> apiaryViews.get(apiaries.indexOf(ap))
//                .setBeehives(ap.getBeehives().stream()
//                        .map(b->modelMapper.map(b, BeehiveView.class)).collect(Collectors.toList())));

    return apiaryViews;
    }

    public ApiaryEditDTO findById(Long id) {
        return modelMapper.map(apiaryRepository.findById(id).orElse(null), ApiaryEditDTO.class);
    }

    public void update(ApiaryEditDTO apiaryEditDTO, Optional<AddressEntity> optionalAddress) {
        ApiaryEntity apiaryEntity = apiaryRepository.findById(apiaryEditDTO.getId()).orElse(new ApiaryEntity()).
                setName(apiaryEditDTO.getName())
                .setDescription(apiaryEditDTO.getDescription())
                .setArea(apiaryEditDTO.getArea())
                .setImageUrl(apiaryEditDTO.getImageURL());

        AddressEntity address = optionalAddress.orElseGet(AddressEntity::new);
        addressService.deleteById(apiaryEntity.getAddress().getId());

        address.
                setCity(apiaryEditDTO.getAddressCity()).
                setCountry(apiaryEditDTO.getAddressCountry()).
                setPostcode(apiaryEditDTO.getAddressPostcode()).
                setStreet(apiaryEditDTO.getAddressStreet());

        addressService.add(address);
        apiaryEntity.setAddress(address);

        apiaryRepository.save(apiaryEntity);
    }

    public Long findApiaryAddressId(Long id) {
        return apiaryRepository.findById(id).get().getAddress().getId();
    }

    public void deleteById(Long id) {
        apiaryRepository.deleteById(id);
    }

    public List<ApiaryView> viewAllByBeekeperEmail(String username) {
        return apiaryRepository.findByBeekeeperEmail(username)
                .stream()
                .map(a -> new ApiaryView().setId(a.getId()).setName(a.getName() + " - " + a.getAddress().getCity()))
                .collect(Collectors.toList());
    }

    public boolean apiaryAlreadyHasBeehiveNumber(Long apiaryId, int referenceNumber) {
        return apiaryRepository.findById(apiaryId).
                orElseThrow().
                getBeehives().
                stream().
                map(BeehiveEntity::getReferenceNumber).
                collect(Collectors.toList()).
                contains(referenceNumber);
    }

    public ApiaryEntity getById(Long apiaryId) {
        return apiaryRepository.findById(apiaryId).orElseThrow();
    }

    public void saveApiary(ApiaryEntity apiaryEntity) {
        apiaryRepository.save(apiaryEntity);
    }

    public ApiaryView findViewById(Long id) {
        return modelMapper.map(findById(id), ApiaryView.class);
    }

    public Long findIdByReferenceNumberInApiary(Long apiaryId, int referenceNumber) {
        return apiaryRepository.
                findById(apiaryId).orElseThrow().
                getBeehives().stream().
                filter(b -> b.getReferenceNumber() == referenceNumber)
                .map(b -> b.getId()).findFirst().orElse(null);
    }
}
