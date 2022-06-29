package bg.beesoft.beehive.repository;

import bg.beesoft.beehive.model.entity.AddressEntity;
import bg.beesoft.beehive.model.entity.ApiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    Optional<AddressEntity> findByCityAndCountryAndStreet(String city, String country, String street);
}
