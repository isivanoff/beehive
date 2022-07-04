package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.ApiaryEditDTO;
import bg.beesoft.beehive.model.entity.AddressEntity;
import bg.beesoft.beehive.model.entity.ApiaryEntity;
import bg.beesoft.beehive.repository.ApiaryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApiaryService {

    private ApiaryRepository apiaryRepository;
    private ModelMapper modelMapper;
    private AddressService addressService;

    public ApiaryService(ApiaryRepository apiaryRepository, ModelMapper modelMapper, AddressService addressService) {
        this.apiaryRepository = apiaryRepository;
        this.modelMapper = modelMapper;
        this.addressService = addressService;
    }

    public void save(ApiaryEntity apiaryEntity) {
        apiaryRepository.save(apiaryEntity);
    }

    public List<ApiaryEntity> findAllApiaries(String email) {
        return apiaryRepository.findByBeekeeperEmail(email);
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

        AddressEntity address;

        if (optionalAddress.isPresent()) {
            address = optionalAddress.get();
        } else {
            address = new AddressEntity();
        }
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
}
