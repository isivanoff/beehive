package bg.beesoft.beehive.model.view;

import bg.beesoft.beehive.model.entity.BeehiveEntity;

import java.util.List;

public class ApiaryView {
    private Long id;
    private String name;
    private int area;
    private String description;
    private String imageUrl;
    private String addressCountry;
    private String addressCity;
    private String addressStreet;
    private String addressPostcode;
    private List<BeehiveView> beehives;


    public ApiaryView() {
    }

    public List<BeehiveView> getBeehives() {
        return beehives;
    }

    public ApiaryView setBeehives(List<BeehiveView> beehives) {
        this.beehives = beehives;
        return this;
    }

    public int getArea() {
        return area;
    }

    public ApiaryView setArea(int area) {
        this.area = area;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ApiaryView setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ApiaryView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public ApiaryView setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
        return this;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public ApiaryView setAddressCity(String addressCity) {
        this.addressCity = addressCity;
        return this;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public ApiaryView setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
        return this;
    }

    public String getAddressPostcode() {
        return addressPostcode;
    }

    public ApiaryView setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ApiaryView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ApiaryView setName(String name) {
        this.name = name;
        return this;
    }
}
