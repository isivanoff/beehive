package bg.beesoft.beehive.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "apiaries")
public class ApiaryEntity extends BaseEntity{

    @Column(nullable = false,unique = true)
    private String name;

    private int area;


    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    @OneToOne(cascade = CascadeType.ALL ,optional = false,fetch = FetchType.LAZY, orphanRemoval = true)
    private AddressEntity address;

    @ManyToOne(optional = false)
    private UserEntity beekeeper;

    @OneToMany(mappedBy = "apiary")
    private List<BeehiveEntity> beehives;

    public String getName() {
        return name;
    }

    public ApiaryEntity setName(String name) {
        this.name = name;
        return this;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public ApiaryEntity setAddress(AddressEntity address) {
        this.address = address;
        return this;
    }

    public int getArea() {
        return area;
    }

    public ApiaryEntity setArea(int area) {
        this.area = area;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ApiaryEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public UserEntity getBeekeeper() {
        return beekeeper;
    }

    public ApiaryEntity setBeekeeper(UserEntity beekeeper) {
        this.beekeeper = beekeeper;
        return this;
    }

    public List<BeehiveEntity> getBeehives() {
        return beehives;
    }

    public ApiaryEntity setBeehives(List<BeehiveEntity> beehives) {
        this.beehives = beehives;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ApiaryEntity setDescription(String description) {
        this.description = description;
        return this;
    }


}
