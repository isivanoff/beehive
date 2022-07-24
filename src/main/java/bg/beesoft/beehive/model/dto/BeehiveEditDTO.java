package bg.beesoft.beehive.model.dto;

import bg.beesoft.beehive.model.entity.enums.BeeHiveTypeEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class BeehiveEditDTO {


    private Long id;

    @NotNull
    @Positive
    private int referenceNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BeeHiveTypeEnum type;

    @NotNull
    private String color;

    @NotNull
    private boolean alive;

    private String imageUrl;

    @NotNull
    private boolean queenAlive;

    @NotNull
    private boolean queenMarked;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate queenDateOfMark;

    @NotNull
    private boolean queenActive;

    @NotNull
    @Positive
    private Long apiaryId;

    public BeehiveEditDTO() {
    }

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public BeehiveEditDTO setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public BeeHiveTypeEnum getType() {
        return type;
    }

    public BeehiveEditDTO setType(BeeHiveTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getColor() {
        return color;
    }

    public BeehiveEditDTO setColor(String color) {
        this.color = color;
        return this;
    }

    public boolean isAlive() {
        return alive;
    }

    public BeehiveEditDTO setAlive(boolean alive) {
        this.alive = alive;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BeehiveEditDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public boolean isQueenAlive() {
        return queenAlive;
    }

    public BeehiveEditDTO setQueenAlive(boolean queenAlive) {
        this.queenAlive = queenAlive;
        return this;
    }

    public boolean isQueenMarked() {
        return queenMarked;
    }

    public BeehiveEditDTO setQueenMarked(boolean queenMarked) {
        this.queenMarked = queenMarked;
        return this;
    }

    public LocalDate getQueenDateOfMark() {
        return queenDateOfMark;
    }

    public BeehiveEditDTO setQueenDateOfMark(LocalDate queenDateOfMark) {
        this.queenDateOfMark = queenDateOfMark;
        return this;
    }

    public boolean isQueenActive() {
        return queenActive;
    }

    public BeehiveEditDTO setQueenActive(boolean queenActive) {
        this.queenActive = queenActive;
        return this;
    }

    public Long getApiaryId() {
        return apiaryId;
    }

    public BeehiveEditDTO setApiaryId(Long apiaryId) {
        this.apiaryId = apiaryId;
        return this;
    }

    public Long getId() {
        return id;
    }

    public BeehiveEditDTO setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "BeehiveAddDTO{" +
                "referenceNumber=" + referenceNumber +
                ", type=" + type +
                ", color=" + color +
                ", alive=" + alive +
                ", imageUrl='" + imageUrl + '\'' +
                ", isQueenAlive=" + queenAlive +
                ", queenIsMarked=" + queenMarked +
                ", dateOfMark=" + queenDateOfMark +
                ", queenIsActive=" + queenActive +
                ", apiaryId=" + apiaryId +
                '}';
    }

}

