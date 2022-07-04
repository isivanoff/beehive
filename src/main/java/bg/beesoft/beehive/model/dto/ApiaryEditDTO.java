package bg.beesoft.beehive.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ApiaryEditDTO {
    @NotNull
    @Positive
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 20)
    private String name;

    @Positive
    private int area;

    private String description;

    @NotEmpty
    @Size(min = 3)
    private String addressStreet;

    @NotEmpty
    @Size(min = 3)
    private String addressCity;

    @NotEmpty
    @Size(min = 3)
    private String addressCountry;

    private String addressPostcode;
    private String imageURL;

    public ApiaryEditDTO() {
    }

    public String getName() {
        return name;
    }

    public ApiaryEditDTO setName(String name) {
        this.name = name;
        return this;
    }

    public int getArea() {
        return area;
    }

    public ApiaryEditDTO setArea(int area) {
        this.area = area;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ApiaryEditDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public ApiaryEditDTO setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
        return this;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public ApiaryEditDTO setAddressCity(String addressCity) {
        this.addressCity = addressCity;
        return this;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public ApiaryEditDTO setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
        return this;
    }

    public String getAddressPostcode() {
        return addressPostcode;
    }

    public ApiaryEditDTO setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public ApiaryEditDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ApiaryEditDTO setId(Long id) {
        this.id = id;
        return this;
    }
}
