package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.entity.QueenEntity;
import bg.beesoft.beehive.repository.QueenRepository;
import org.springframework.stereotype.Service;

@Service
public class QueenService {
    private QueenRepository queenRepository;

    public QueenService(QueenRepository queenRepository) {
        this.queenRepository = queenRepository;
    }

    public void add(QueenEntity queenEntity) {
        queenRepository.save(queenEntity);
    }
}
