package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.TaskAddDTO;
import bg.beesoft.beehive.model.entity.BeehiveEntity;
import bg.beesoft.beehive.model.entity.QueenEntity;
import bg.beesoft.beehive.model.entity.TaskEntity;
import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.model.entity.enums.BeeHiveTypeEnum;
import bg.beesoft.beehive.model.entity.enums.TaskEnum;
import bg.beesoft.beehive.model.entity.enums.TemperamentEnum;
import bg.beesoft.beehive.model.user.BeehiveUserDetails;
import bg.beesoft.beehive.model.view.TaskFullView;
import bg.beesoft.beehive.model.view.TaskView;
import bg.beesoft.beehive.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    TaskService toTest;

    @Mock
    private BeehiveService beehiveService;
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    private UserDetails userDetails;
    private TaskEntity taskEntity;
    private BeehiveEntity beehive;
    private TaskAddDTO taskAddDTO;


    @BeforeEach
    void setUp() {

        toTest = new TaskService(modelMapper, beehiveService, taskRepository);
        userDetails = new BeehiveUserDetails(
                "123456",
                "test@example.com",
                "Stanimir",
                "Kotsev",
                true,
                new ArrayList<>(),
                false
        );

        beehive = new BeehiveEntity()
                .setReferenceNumber(1)
                .setAlive(true)
                .setColor("#e6e6e6")
                .setPower(100)
                .setTemperament(TemperamentEnum.CALM)
                .setType(BeeHiveTypeEnum.TOPBARHIVE)
                .setBeekeeper((UserEntity) new UserEntity()
                        .setEmail("test@example.com")
                        .setId(1L))
                .setQueen((QueenEntity) new QueenEntity()
                        .setActive(true)
                        .setAlive(true)
                        .setId(1L));

        taskAddDTO = new TaskAddDTO()
                .setTask(TaskEnum.INSPECTION)
                .setDate(LocalDate.now())
                .setPower(100)
                .setTemperament(TemperamentEnum.CALM)
                .setQueenAlive(true);


        taskEntity = new TaskEntity().setTask(TaskEnum.INSPECTION)
                .setDate(LocalDate.now())
                .setPower(100)
                .setTemperament(TemperamentEnum.CALM)
                .setQueenAlive(true);

    }

    @Test
    void testInitializeTask() {
        when(beehiveService.findById(any(), any())).thenReturn(beehive);
        when(modelMapper.map(any(), any())).thenReturn(taskAddDTO);

        TaskAddDTO result = toTest.initializeTask(1L, userDetails);

        Assertions.assertEquals(result.getPower(), beehive.getPower());
    }

    @Test
    void testAddTask() {
        when(beehiveService.findById(any(), any())).thenReturn(beehive);
        when(modelMapper.map(any(), any())).thenReturn(taskEntity);

        doNothing().when(beehiveService).save(beehive);

        toTest.addTask(taskAddDTO, 1L, userDetails);

        verify(taskRepository).save(taskEntity);
    }

    @Test
    void testFindAllByBeehiveId() {
        when(taskRepository.findAllByBeehiveId(any(), any())).thenReturn(new PageImpl<>(List.of(taskEntity)));
        when(modelMapper.map(any(), any())).thenReturn(new ModelMapper().map(taskEntity, TaskView.class));

        Page<TaskView> allViews = toTest.findAllByBeehiveId(1L, Pageable.ofSize(5));

        Assertions.assertEquals(taskEntity.getTask(), allViews.getContent().get(0).getTask());
    }

    @Test
    void testFindViewById() {
        when(taskRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(taskEntity));
        when(modelMapper.map(any(), any())).thenReturn(new ModelMapper().map(taskEntity, TaskFullView.class));

        TaskFullView taskFullView = toTest.findViewById(taskEntity.getId());

        Assertions.assertEquals(taskEntity.getTask(), taskFullView.getTask());

    }

    @Test
    void testDeleteByIdSuccessfully() {
        when(taskRepository.existsById(any())).thenReturn(true);
        doNothing().when(taskRepository).deleteById(any());
        Assertions.assertTrue(() -> toTest.deleteById(1L));
    }

    @Test
    void testDeleteByIdUnsuccessfully() {
        when(taskRepository.existsById(any())).thenReturn(false);
        Assertions.assertFalse(() -> toTest.deleteById(1L));
    }
}
