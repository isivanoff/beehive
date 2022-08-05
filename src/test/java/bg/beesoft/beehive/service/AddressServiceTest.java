package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.entity.AddressEntity;
import bg.beesoft.beehive.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    AddressRepository mockAddressRepo;

    private AddressService toTest;

    AddressEntity addressEntity = new AddressEntity()
            .setCity("Sofia")
            .setCountry("Bulgaria")
            .setPostcode("1000")
            .setStreet("Dondukov");


    @BeforeEach
    void setUp() {
        toTest = new AddressService(mockAddressRepo);

    }


    @Test
    void testAddAddress() {

        when(mockAddressRepo.save(any())).thenReturn(addressEntity);

        toTest.add(addressEntity);

        verify(mockAddressRepo, times(1)).save(addressEntity);

    }

    @Test
    void testFindByCityAndCountryAndStreet() {
        when(mockAddressRepo.findByCityAndCountryAndStreet(addressEntity.getCity(), addressEntity.getCountry(), addressEntity.getStreet()))
                .thenReturn(Optional.ofNullable(addressEntity));

        Optional<AddressEntity> optAddress = toTest.findByCityAndCountryAndStreet(addressEntity.getCity(), addressEntity.getCountry(), addressEntity.getStreet());

        Assertions.assertEquals(addressEntity.getCity(), optAddress.get().getCity());
        Assertions.assertEquals(addressEntity.getCountry(), optAddress.get().getCountry());
        Assertions.assertEquals(addressEntity.getStreet(), optAddress.get().getStreet());

    }

    @Test
    void testDeleteAddress() {

        toTest.deleteById(1L);

        verify(mockAddressRepo, times(1)).deleteById(1L);

    }


}
