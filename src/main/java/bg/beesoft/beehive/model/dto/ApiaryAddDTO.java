package bg.beesoft.beehive.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ApiaryAddDTO {
    @NotEmpty
    @Size(min = 3, max = 20)
    private String name;

    @Positive
    private int area;

    private String description;

    @NotEmpty
    @Size(min = 3)
    private String street;

    @NotEmpty
    @Size(min = 3)
    private String city;

    @NotEmpty
    @Size(min = 3)
    private String country;

    private String postcode;
    private String imageURL;

    public ApiaryAddDTO() {
    }

    public String getName() {
        return name;
    }

    public ApiaryAddDTO setName(String name) {
        this.name = name;
        return this;
    }

    public int getArea() {
        return area;
    }

    public ApiaryAddDTO setArea(int area) {
        this.area = area;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ApiaryAddDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public ApiaryAddDTO setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ApiaryAddDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ApiaryAddDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPostcode() {
        return postcode;
    }

    public ApiaryAddDTO setPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public ApiaryAddDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }
}
