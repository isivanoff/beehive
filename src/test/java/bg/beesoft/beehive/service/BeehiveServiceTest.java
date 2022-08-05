package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.BeehiveAddDTO;
import bg.beesoft.beehive.model.dto.BeehiveEditDTO;
import bg.beesoft.beehive.model.dto.SearchBeehiveDTO;
import bg.beesoft.beehive.model.entity.*;
import bg.beesoft.beehive.model.entity.enums.BeeHiveTypeEnum;
import bg.beesoft.beehive.model.entity.enums.TemperamentEnum;
import bg.beesoft.beehive.model.entity.enums.UserRoleEnum;
import bg.beesoft.beehive.model.exception.NotFoundException;
import bg.beesoft.beehive.model.exception.UnauthorizedRequestException;
import bg.beesoft.beehive.model.user.BeehiveUserDetails;
import bg.beesoft.beehive.model.view.BeehiveFullView;
import bg.beesoft.beehive.model.view.BeehiveView;
import bg.beesoft.beehive.repository.BeehiveRepository;
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
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BeehiveServiceTest {

    BeehiveService toTest;
    @Mock
    private QueenService queenService;
    @Mock
    private ApiaryService apiaryService;
    @Mock
    private BeehiveRepository beehiveRepository;
    @Mock
    private UserService userService;
    private BeehiveUserDetails userDetails;
    private BeehiveEntity beehive;
    private ApiaryEntity apiary;
    private BeehiveUserDetails userDetails1;

    @BeforeEach
    void setUp() {
        toTest = new BeehiveService(new ModelMapper(), queenService, apiaryService, userService, beehiveRepository);

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

        apiary = (ApiaryEntity) new ApiaryEntity()
                .setName("apiary")
                .setArea(2)
                .setAddress((AddressEntity) new AddressEntity()
                        .setCity("Sofia")
                        .setCountry("Bulgaria")
                        .setPostcode("1235")
                        .setStreet("Dondukov")
                        .setId(1L))

                .setBeehives(List.of(beehive))
                .setBeekeeper((UserEntity) new UserEntity().setEmail("test@example.com").
                        setPassword("123456").
                        setFirstName("Sasho").
                        setLastName("Kolev").
                        setImageUrl("http://profile.com/test").
                        setUserRoles(
                                List.of(
                                        new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN)
                                ))
                        .setActive(true)
                        .setId(1L)
                ).setId(1L);

        beehive.setApiary(apiary);

        userDetails1 = new BeehiveUserDetails(
                "123456",
                "sancho",
                "Stanimir",
                "Kotsev",
                true,
                new ArrayList<>(),
                false
        );
    }

    @Test
    void testAddBeehive() {
        BeehiveAddDTO beehiveAddDTO = new BeehiveAddDTO()
                .setReferenceNumber(1)
                .setAlive(true)
                .setColor("#e6e6e6")
                .setPower(100)
                .setApiaryId(1L)
                .setQueenActive(true)
                .setQueenAlive(true)
                .setTemperament(TemperamentEnum.CALM)
                .setType(BeeHiveTypeEnum.TOPBARHIVE);

        toTest.addBeehive(beehiveAddDTO, userDetails.getUsername());

        verify(beehiveRepository).save(any());
    }

    @Test
    void testFindViewAllByApiaryIdSuccessfully() {
        Page<BeehiveEntity> pageOriginal = new PageImpl<>(List.of(beehive));

        when(beehiveRepository.findByApiaryId(any(), any())).thenReturn(pageOriginal);
        when(apiaryService.findById(any())).thenReturn(apiary);
        Page<BeehiveView> page = toTest.findViewAllByApiaryId(1L, Pageable.ofSize(10), userDetails);

        Assertions.assertEquals(pageOriginal.getTotalElements(), page.getTotalElements());

    }

    @Test
    void testFindViewAllByApiaryIdUnSuccessfully() {
        Page<BeehiveEntity> pageOriginal = new PageImpl<>(List.of(beehive));

        when(beehiveRepository.findByApiaryId(any(), any())).thenReturn(pageOriginal);
        when(apiaryService.findById(any())).thenReturn(apiary);

        Assertions.assertThrows(UnauthorizedRequestException.class, () -> toTest.findViewAllByApiaryId(1L, Pageable.ofSize(10), userDetails1));

    }

    @Test
    void testViewById() {
        when(beehiveRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(beehive));
        BeehiveFullView beehiveFullView = toTest.viewById(apiary.getId(), userDetails);
        Assertions.assertEquals(beehive.getReferenceNumber(), beehiveFullView.getReferenceNumber());
    }

    @Test
    void testDeleteById() {
        when(beehiveRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(beehive));
        toTest.deleteById(beehive.getId(), userDetails);
        verify(beehiveRepository).deleteById(beehive.getId());
    }

    @Test
    void testFindApiaryByBeehiveId() {
        when(beehiveRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(beehive));
        Long id = toTest.findApiaryIdByBeehiveId(beehive.getId(), userDetails);
        Assertions.assertEquals(apiary.getId(), id);
    }

    @Test
    void testGetEditDTOById() {
        when(beehiveRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(beehive));
        BeehiveEditDTO beehiveEditDTO = toTest.getEditDTOById(beehive.getId(), userDetails);
        Assertions.assertEquals(beehive.getReferenceNumber(), beehiveEditDTO.getReferenceNumber());
    }

    @Test
    void testUpdateBeehive() {
        when(beehiveRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(beehive));
        BeehiveEditDTO beehiveEditDTO = new BeehiveEditDTO()
                .setReferenceNumber(1)
                .setAlive(true)
                .setColor("#e6e6e6")
                .setPower(100)
                .setApiaryId(1L)
                .setQueenActive(true)
                .setQueenAlive(true)
                .setTemperament(TemperamentEnum.CALM)
                .setType(BeeHiveTypeEnum.TOPBARHIVE);
        toTest.updateBeehive(beehiveEditDTO, userDetails, beehive.getId());
        verify(beehiveRepository).save(beehive.setReferenceNumber(1)
                .setAlive(true)
                .setColor("#e6e6e6")
                .setPower(100)
                .setTemperament(TemperamentEnum.CALM)
                .setType(BeeHiveTypeEnum.TOPBARHIVE));
    }

    @Test
    void testFindByIdUnsuccessful() {
        when(beehiveRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> toTest.findById(100L));

    }

    @Test
    void testFindById() {
        when(beehiveRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(beehive));
        BeehiveEntity beehiveEntity = toTest.findById(beehive.getId(), userDetails);
        Assertions.assertEquals(beehive.getReferenceNumber(), beehiveEntity.getReferenceNumber());
    }

    @Test
    void testSave() {
        toTest.save(beehive);
        verify(beehiveRepository).save(beehive);
    }

    @Test
    void testSearchBeehives() {
        when(apiaryService.findById(any())).thenReturn(apiary);
        when(beehiveRepository.findAll((Specification<BeehiveEntity>) any(), (Pageable) any())).thenReturn(new PageImpl<>(List.of(beehive)));
        SearchBeehiveDTO searchBeehiveDTO = new SearchBeehiveDTO()
                .setReferenceNumber(null)
                .setType(null);

        Page<BeehiveView> page = toTest.searchBeehives(searchBeehiveDTO, apiary.getId(), Pageable.ofSize(10), userDetails);

        Assertions.assertEquals(1, page.getTotalElements());

    }


}
