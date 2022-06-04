package bg.beesoft.beehive.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class AddressEntity extends BaseEntity {
    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    private String postcode;

    public String getCountry() {
        return country;
    }

    public AddressEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public AddressEntity setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getPostcode() {
        return postcode;
    }

    public AddressEntity setPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }
}
