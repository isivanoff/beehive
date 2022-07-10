package bg.beesoft.beehive.model.dto;

import bg.beesoft.beehive.model.entity.enums.BeeHiveTypeEnum;
import bg.beesoft.beehive.model.entity.enums.ColorEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class BeehiveAddDTO {
    @NotNull
    @Positive
    private int referenceNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BeeHiveTypeEnum type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    @NotNull
    private boolean alive;

    private String imageUrl;

    @NotNull
    private boolean queenIsAlive;

    @NotNull
    private boolean queenIsMarked;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfMark;

    @NotNull
    private boolean queenIsActive;

    @NotNull
    @Positive
    private Long apiaryId;

    public BeehiveAddDTO() {
    }

    public int getReferenceNumber() {
        return referenceNumber;
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

    public ColorEnum getColor() {
        return color;
    }

    public BeehiveAddDTO setColor(ColorEnum color) {
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

    public boolean isQueenIsAlive() {
        return queenIsAlive;
    }

    public BeehiveAddDTO setQueenIsAlive(boolean queenIsAlive) {
        this.queenIsAlive = queenIsAlive;
        return this;
    }

    public boolean isQueenIsMarked() {
        return queenIsMarked;
    }

    public BeehiveAddDTO setQueenIsMarked(boolean queenIsMarked) {
        this.queenIsMarked = queenIsMarked;
        return this;
    }

    public LocalDate getDateOfMark() {
        return dateOfMark;
    }

    public BeehiveAddDTO setDateOfMark(LocalDate dateOfMark) {
        this.dateOfMark = dateOfMark;
        return this;
    }

    public boolean isQueenIsActive() {
        return queenIsActive;
    }

    public BeehiveAddDTO setQueenIsActive(boolean queenIsActive) {
        this.queenIsActive = queenIsActive;
        return this;
    }

    public Long getApiaryId() {
        return apiaryId;
    }

    public BeehiveAddDTO setApiaryId(Long apiaryId) {
        this.apiaryId = apiaryId;
        return this;
    }
}
