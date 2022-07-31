package bg.beesoft.beehive.model.entity;


import bg.beesoft.beehive.model.entity.enums.BeeHiveTypeEnum;
import bg.beesoft.beehive.model.entity.enums.TemperamentEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "beehives")
public class BeehiveEntity extends BaseEntity {

    private int referenceNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BeeHiveTypeEnum type;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private boolean isAlive;

    private String imageUrl;

    @OneToOne(cascade = CascadeType.ALL ,optional = false)
    private QueenEntity queen;

    private int power;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TemperamentEnum temperament;

    @ManyToOne
    private ApiaryEntity apiary;

    @ManyToOne
    private UserEntity beekeeper;

    @OneToMany(mappedBy = "beehive",cascade = CascadeType.ALL)
    private List<TaskEntity> tasks;

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public int getPower() {
        return power;
    }

    public BeehiveEntity setPower(int power) {
        this.power = power;
        return this;
    }

    public BeehiveEntity setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public QueenEntity getQueen() {
        return queen;
    }

    public BeehiveEntity setQueen(QueenEntity queen) {
        this.queen = queen;
        return this;
    }


    public BeeHiveTypeEnum getType() {
        return type;
    }

    public BeehiveEntity setType(BeeHiveTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getColor() {
        return color;
    }

    public BeehiveEntity setColor(String color) {
        this.color = color;
        return this;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public BeehiveEntity setAlive(boolean alive) {
        isAlive = alive;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BeehiveEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ApiaryEntity getApiary() {
        return apiary;
    }

    public BeehiveEntity setApiary(ApiaryEntity apiary) {
        this.apiary = apiary;
        return this;
    }

    public UserEntity getBeekeeper() {
        return beekeeper;
    }

    public BeehiveEntity setBeekeeper(UserEntity beekeeper) {
        this.beekeeper = beekeeper;
        return this;
    }


    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public BeehiveEntity setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
        return this;
    }

    public TemperamentEnum getTemperament() {
        return temperament;
    }

    public BeehiveEntity setTemperament(TemperamentEnum temperament) {
        this.temperament = temperament;
        return this;
    }
}
