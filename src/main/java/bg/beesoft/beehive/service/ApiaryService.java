package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.entity.ApiaryEntity;
import bg.beesoft.beehive.repository.ApiaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiaryService {

    private ApiaryRepository apiaryRepository;

    public ApiaryService(ApiaryRepository apiaryRepository) {
        this.apiaryRepository = apiaryRepository;
    }

    public void save(ApiaryEntity apiaryEntity) {
        apiaryRepository.save(apiaryEntity);
    }

    public List<ApiaryEntity> findAllApiaries(String email) {
        return apiaryRepository.findByBeekeeperEmail(email);
    }
}
