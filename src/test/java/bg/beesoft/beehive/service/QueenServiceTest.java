package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.entity.QueenEntity;
import bg.beesoft.beehive.repository.QueenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class QueenServiceTest {

    @Mock
    QueenRepository queenRepository;

    QueenService toTest;

    @BeforeEach
    void setUp() {
        toTest = new QueenService(queenRepository);
    }

    @Test
    void testAdd() {
        QueenEntity queenEntity = (QueenEntity) new QueenEntity().setMarked(false).setAlive(true).setActive(true).setId(1L);

        toTest.add(queenEntity);

        verify(queenRepository, times(1)).save(queenEntity);
    }


}
