package bg.beesoft.beehive.model.dto;

import bg.beesoft.beehive.model.entity.enums.TaskEnum;
import bg.beesoft.beehive.model.entity.enums.TemperamentEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class TaskAddDTO {

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate date;

    @NotNull
    private TaskEnum task;

    private String notes;

    @NotNull
    private Long beehiveId;

    @NotNull
    @Min(1)
    @Max(100)
    private int power;

    @NotNull
    private TemperamentEnum temperament;

    @NotNull
    private Long queenId;
    @NotNull
    private boolean queenAlive;

    @NotNull
    private boolean queenMarked;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate queenDateOfMark;

    @NotNull
    private boolean queenActive;

    private boolean eggs;
    private boolean larva;
    private boolean puppa;
    private boolean disease;
    private int beeFrames;
    private int broodFrames;
    private int honeyFrames;
    private int pollenFrames;
    private int foundationFrames;

    public TaskAddDTO() {
    }

    public Long getQueenId() {
        return queenId;
    }

    public TaskAddDTO setQueenId(Long queenId) {
        this.queenId = queenId;
        return this;
    }

    public boolean isQueenAlive() {
        return queenAlive;
    }

    public TaskAddDTO setQueenAlive(boolean queenAlive) {
        this.queenAlive = queenAlive;
        return this;
    }

    public boolean isQueenMarked() {
        return queenMarked;
    }

    public TaskAddDTO setQueenMarked(boolean queenMarked) {
        this.queenMarked = queenMarked;
        return this;
    }

    public LocalDate getQueenDateOfMark() {
        return queenDateOfMark;
    }

    public TaskAddDTO setQueenDateOfMark(LocalDate queenDateOfMark) {
        this.queenDateOfMark = queenDateOfMark;
        return this;
    }

    public boolean isQueenActive() {
        return queenActive;
    }

    public TaskAddDTO setQueenActive(boolean queenActive) {
        this.queenActive = queenActive;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public TaskAddDTO setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public TaskEnum getTask() {
        return task;
    }

    public TaskAddDTO setTask(TaskEnum task) {
        this.task = task;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public TaskAddDTO setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Long getBeehiveId() {
        return beehiveId;
    }

    public TaskAddDTO setBeehiveId(Long beehiveId) {
        this.beehiveId = beehiveId;
        return this;
    }

    public int getPower() {
        return power;
    }

    public TaskAddDTO setPower(int power) {
        this.power = power;
        return this;
    }

    public TemperamentEnum getTemperament() {
        return temperament;
    }

    public TaskAddDTO setTemperament(TemperamentEnum temperament) {
        this.temperament = temperament;
        return this;
    }

    public boolean isEggs() {
        return eggs;
    }

    public TaskAddDTO setEggs(boolean eggs) {
        this.eggs = eggs;
        return this;
    }

    public boolean isLarva() {
        return larva;
    }

    public TaskAddDTO setLarva(boolean larva) {
        this.larva = larva;
        return this;
    }

    public boolean isPuppa() {
        return puppa;
    }

    public TaskAddDTO setPuppa(boolean puppa) {
        this.puppa = puppa;
        return this;
    }

    public boolean isDisease() {
        return disease;
    }

    public TaskAddDTO setDisease(boolean disease) {
        this.disease = disease;
        return this;
    }

    public int getBeeFrames() {
        return beeFrames;
    }

    public TaskAddDTO setBeeFrames(int beeFrames) {
        this.beeFrames = beeFrames;
        return this;
    }

    public int getBroodFrames() {
        return broodFrames;
    }

    public TaskAddDTO setBroodFrames(int broodFrames) {
        this.broodFrames = broodFrames;
        return this;
    }

    public int getHoneyFrames() {
        return honeyFrames;
    }

    public TaskAddDTO setHoneyFrames(int honeyFrames) {
        this.honeyFrames = honeyFrames;
        return this;
    }

    public int getPollenFrames() {
        return pollenFrames;
    }

    public TaskAddDTO setPollenFrames(int pollenFrames) {
        this.pollenFrames = pollenFrames;
        return this;
    }

    public int getFoundationFrames() {
        return foundationFrames;
    }

    public TaskAddDTO setFoundationFrames(int foundationFrames) {
        this.foundationFrames = foundationFrames;
        return this;
    }
}
