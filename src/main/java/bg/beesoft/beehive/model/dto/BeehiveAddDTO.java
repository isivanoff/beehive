package bg.beesoft.beehive.model.dto;

import bg.beesoft.beehive.model.entity.enums.BeeHiveTypeEnum;
import bg.beesoft.beehive.model.entity.enums.TemperamentEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.time.LocalDate;

public class BeehiveAddDTO {
    @NotNull
    @Positive
    private int referenceNumber;

    @NotNull
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
    private LocalDate dateOfMark;

    @NotNull
    private boolean queenActive;

    @NotNull
    @Min(1)
    @Max(100)
    private int power;


    @NotNull
    @Positive
    private Long apiaryId;

    @NotNull
    private TemperamentEnum temperament;

    public BeehiveAddDTO() {
    }

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public int getPower() {
        return power;
    }

    public BeehiveAddDTO setPower(int power) {
        this.power = power;
        return this;
    }

    public BeehiveAddDTO setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public BeeHiveTypeEnum getType() {
        return type;
    }

    public BeehiveAddDTO setType(BeeHiveTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getColor() {
        return color;
    }

    public BeehiveAddDTO setColor(String color) {
        this.color = color;
        return this;
    }

    public boolean isAlive() {
        return alive;
    }

    public BeehiveAddDTO setAlive(boolean alive) {
        this.alive = alive;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BeehiveAddDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public boolean isQueenAlive() {
        return queenAlive;
    }

    public BeehiveAddDTO setQueenAlive(boolean queenAlive) {
        this.queenAlive = queenAlive;
        return this;
    }

    public boolean isQueenMarked() {
        return queenMarked;
    }

    public BeehiveAddDTO setQueenMarked(boolean queenMarked) {
        this.queenMarked = queenMarked;
        return this;
    }

    public LocalDate getDateOfMark() {
        return dateOfMark;
    }

    public BeehiveAddDTO setDateOfMark(LocalDate dateOfMark) {
        this.dateOfMark = dateOfMark;
        return this;
    }

    public boolean isQueenActive() {
        return queenActive;
    }

    public BeehiveAddDTO setQueenActive(boolean queenActive) {
        this.queenActive = queenActive;
        return this;
    }

    public Long getApiaryId() {
        return apiaryId;
    }

    public BeehiveAddDTO setApiaryId(Long apiaryId) {
        this.apiaryId = apiaryId;
        return this;
    }

    public TemperamentEnum getTemperament() {
        return temperament;
    }

    public BeehiveAddDTO setTemperament(TemperamentEnum temperament) {
        this.temperament = temperament;
        return this;
    }
}
