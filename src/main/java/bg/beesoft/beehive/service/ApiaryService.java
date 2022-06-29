package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.entity.ApiaryEntity;
import bg.beesoft.beehive.repository.ApiaryRepository;
import org.springframework.stereotype.Service;

@Service
public class ApiaryService {

    private ApiaryRepository apiaryRepository;

    public ApiaryService(ApiaryRepository apiaryRepository) {
        this.apiaryRepository = apiaryRepository;
    }

    public void save(ApiaryEntity apiaryEntity) {
        apiaryRepository.save(apiaryEntity);
    }
}
