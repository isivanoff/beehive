package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.ApiaryAddDTO;
import bg.beesoft.beehive.model.dto.ApiaryEditDTO;
import bg.beesoft.beehive.model.entity.*;
import bg.beesoft.beehive.model.entity.enums.UserRoleEnum;
import bg.beesoft.beehive.model.exception.NotFoundException;
import bg.beesoft.beehive.model.exception.UnauthorizedRequestException;
import bg.beesoft.beehive.model.user.BeehiveUserDetails;
import bg.beesoft.beehive.model.view.ApiaryView;
import bg.beesoft.beehive.repository.AddressRepository;
import bg.beesoft.beehive.repository.ApiaryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApiaryServiceTest {
    @Mock
    private ApiaryRepository apiaryRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private UserService userService;

    ApiaryEntity apiary;
    ApiaryService toTest;

    UserDetails userDetails;
    UserDetails userDetails1;

    @BeforeEach
    void setUp() {
        toTest = new ApiaryService(apiaryRepository, new ModelMapper(), addressService, userService);
        addressService = new AddressService(addressRepository);

        userDetails = new BeehiveUserDetails(
                "123456",
                "test@example.com",
                "Stanimir",
                "Kotsev",
                true,
                new ArrayList<>(),
                false
        );

        userDetails1 = new BeehiveUserDetails(
                "123456",
                "sancho",
                "Stanimir",
                "Kotsev",
                true,
                new ArrayList<>(),
                false
        );

        apiary = new ApiaryEntity()
                .setName("apiary")
                .setArea(2)
                .setAddress((AddressEntity) new AddressEntity()
                        .setCity("Sofia")
                        .setCountry("Bulgaria")
                        .setPostcode("1235")
                        .setStreet("Dondukov")
                        .setId(1L))
                .setBeehives(List.of((BeehiveEntity) new BeehiveEntity()
                        .setReferenceNumber(1)
                        .setAlive(true)
                        .setApiary(apiary)
                        .setId(1L)))
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
                );
    }

    @Test
    void testSave() {
        ApiaryAddDTO apiaryAddDTO = new ApiaryAddDTO()
                .setArea(2)
                .setCountry("Bulgaria")
                .setCity("Sofia")
                .setDescription("Small one")
                .setName("Mocker")
                .setPostcode("12345")
                .setStreet("Dondukov");


        toTest.save(apiaryAddDTO, userDetails);

        verify(apiaryRepository, times(1)).save(any());
    }

    @Test
    void testFindAllApiaries() {
        List<ApiaryView> apiaryViews = new ArrayList<>();

        apiaryViews.add(new ApiaryView()
                .setName("apiary")
                .setArea(2)
                .setAddressCity("Sofia")
                .setAddressCountry("Bulgaria")
                .setAddressPostcode("1235")
                .setAddressStreet("Dondukov")
                .setBeehives(new ArrayList<>()));

        List<ApiaryEntity> apiaryEntities = new ArrayList<>();
        apiaryEntities.add(apiary);

        when(apiaryRepository.findByBeekeeperEmail(any())).thenReturn(apiaryEntities);


        List<ApiaryView> apiaries = toTest.findAllApiaries("test");

        Assertions.assertEquals(apiaryViews.size(), apiaries.size());
        Assertions.assertEquals(apiaryViews.get(0).getName(), apiaries.get(0).getName());

    }

    @Test
    void testFindByIdSuccessful() {
        when(apiaryRepository.findById(1L)).thenReturn(Optional.of(apiary));
        ApiaryEntity apiaryEntity = toTest.findById(1L);
        Assertions.assertEquals(apiaryEntity.getName(), apiary.getName());
    }

    @Test
    void testFindByIdUnSuccessful() {
        when(apiaryRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> toTest.findById(1L));
    }

    @Test
    void testFindByIdAuthorized() {

        when(apiaryRepository.findById(1L)).thenReturn(Optional.of(apiary));
        ApiaryEditDTO apiaryEditDTO = toTest.findById(1L, userDetails);
        Assertions.assertEquals(apiary.getName(), apiaryEditDTO.getName());
    }

    @Test
    void testFindByIdUnAuthorized() {
        when(apiaryRepository.findById(1L)).thenReturn(Optional.of(apiary));
        Assertions.assertThrows(UnauthorizedRequestException.class, () -> toTest.findById(1L, userDetails1));
    }

    @Test
    void testUpdateApiary() {
        ApiaryEditDTO apiaryEditDTO = new ApiaryEditDTO()
                .setArea(2)
                .setAddressCountry("Bulgaria")
                .setAddressCity("Sofia")
                .setDescription("Small one")
                .setName("Mocker")
                .setAddressPostcode("12345")
                .setAddressStreet("Dondukov");

        when(apiaryRepository.findById(any())).thenReturn(Optional.of(apiary));

        Optional<AddressEntity> optionalAddress = Optional.empty();

        toTest.update(apiaryEditDTO, optionalAddress, userDetails);

        verify(apiaryRepository, times(1)).save(any());
    }

    @Test
    void testUpdateApiaryNotSuccessful() {
        ApiaryEditDTO apiaryEditDTO = new ApiaryEditDTO()
                .setArea(2)
                .setAddressCountry("Bulgaria")
                .setAddressCity("Sofia")
                .setDescription("Small one")
                .setName("Mocker")
                .setAddressPostcode("12345")
                .setAddressStreet("Dondukov").setId(2L);

        when(apiaryRepository.findById(any())).thenReturn(Optional.of(apiary));

        Optional<AddressEntity> optionalAddress = Optional.empty();

        Assertions.assertThrows(UnauthorizedRequestException.class, () -> toTest.update(apiaryEditDTO, optionalAddress, userDetails1));
    }

    @Test
    void testFindApiaryAddressId() {
        when(apiaryRepository.findById(any())).thenReturn(Optional.of(apiary));

        Long addressId = toTest.findApiaryAddressId(1L);

        Assertions.assertEquals(addressId, apiary.getAddress().getId());

    }

    @Test
    void testDeleteByIdSuccessfully() {
        when(apiaryRepository.findById(any())).thenReturn(Optional.of(apiary));

        toTest.deleteById(apiary.getId(), userDetails.getUsername());
        verify(apiaryRepository, times(1)).deleteById(apiary.getId());
    }

    @Test
    void testDeleteByIdUnSuccessfully() {
        when(apiaryRepository.findById(any())).thenReturn(Optional.of(apiary));

        Assertions.assertThrows(UnauthorizedRequestException.class, () -> toTest.deleteById(apiary.getId(), userDetails1.getUsername()));
    }

    @Test
    void testViewAllByBeekeeperEmail() {
        when(apiaryRepository.findByBeekeeperEmail(userDetails.getUsername())).thenReturn(List.of(apiary));
        List<ApiaryView> apiaryViews = toTest.viewAllByBeekeperEmail(userDetails.getUsername());
        Assertions.assertEquals(apiaryViews.size(), 1);
    }

    @Test
    void testApiaryAlreadyHasBeehiveNumber() {
        when(apiaryRepository.findById(any())).thenReturn(Optional.of(apiary));

        Assertions.assertTrue(() -> toTest.apiaryAlreadyHasBeehiveNumber(apiary.getId(), 1));
    }

    @Test
    void testSaveApiary() {
        toTest.saveApiary(apiary);
        verify(apiaryRepository, times(1)).save(apiary);
    }

    @Test
    void testFindViewById() {
        when(apiaryRepository.findById(any())).thenReturn(Optional.of(apiary));
        ApiaryView apiaryView = toTest.findViewById(apiary.getId(), userDetails);
        Assertions.assertEquals(apiary.getName(), apiaryView.getName());
    }

    @Test
    void testFindIdByReferenceNumberInApiary() {
        when(apiaryRepository.findById(any())).thenReturn(Optional.of(apiary));

        Long id = toTest.findIdByReferenceNumberInApiary(apiary.getId(), apiary.getBeehives().stream().findFirst().get().getReferenceNumber());
        Assertions.assertEquals(apiary.getBeehives().stream().findFirst().get().getId(), id);
    }

    @Test
    void testDeleteAllApiaries() {
        toTest.deleteAllApiaries(userDetails.getUsername());
        verify(apiaryRepository, times(1)).deleteAllByBeekeeperEmail(userDetails.getUsername());
    }

    @Test
    void testCheckApiary() {
        when(apiaryRepository.findById(any())).thenReturn(Optional.of(apiary));
        Assertions.assertThrows(UnauthorizedRequestException.class, () -> toTest.checkApiary(apiary.getId(), userDetails1));
    }

}
