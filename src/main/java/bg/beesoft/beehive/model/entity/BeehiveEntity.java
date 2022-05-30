package bg.beesoft.beehive.model.entity;


import bg.beesoft.beehive.model.entity.enums.BeeHiveTypeEnum;
import bg.beesoft.beehive.model.entity.enums.ColorEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "beehives")
public class BeehiveEntity extends BaseEntity {

    private int number;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BeeHiveTypeEnum type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    private boolean hasQueen;

    private LocalDate queenDate;

    private boolean isAlive;

    private String imageUrl;

    @ManyToOne
    private ApiaryEntity apiary;

    @ManyToOne
    private UserEntity beekeeper;

    @OneToMany(mappedBy = "beehive")
    private List<TaskEntity> tasks;

    public int getNumber() {
        return number;
    }

    public BeehiveEntity setNumber(int number) {
        this.number = number;
        return this;
    }

    public BeeHiveTypeEnum getType() {
        return type;
    }

    public BeehiveEntity setType(BeeHiveTypeEnum type) {
        this.type = type;
        return this;
    }

    public ColorEnum getColor() {
        return color;
    }

    public BeehiveEntity setColor(ColorEnum color) {
        this.color = color;
        return this;
    }

    public boolean isHasQueen() {
        return hasQueen;
    }

    public BeehiveEntity setHasQueen(boolean hasQueen) {
        this.hasQueen = hasQueen;
        return this;
    }

    public LocalDate getQueenDate() {
        return queenDate;
    }

    public BeehiveEntity setQueenDate(LocalDate queenDate) {
        this.queenDate = queenDate;
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
}
