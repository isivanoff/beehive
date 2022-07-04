package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.entity.AddressEntity;
import bg.beesoft.beehive.model.entity.ApiaryEntity;
import bg.beesoft.beehive.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {
    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void add(AddressEntity address) {
        addressRepository.save(address);
    }

    public Optional<AddressEntity> findByCityAndCountryAndStreet(String city, String country, String street) {
        return addressRepository.findByCityAndCountryAndStreet(city, country, street);
    }


    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }
}
