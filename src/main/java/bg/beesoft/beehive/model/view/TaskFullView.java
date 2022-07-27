package bg.beesoft.beehive.model.view;

import bg.beesoft.beehive.model.entity.enums.TaskEnum;
import bg.beesoft.beehive.model.entity.enums.TemperamentEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class TaskFullView {
    private Long id;
    private LocalDate date;
    private TaskEnum task;
    private String notes;
    private Long beehiveId;
    private int power;
    private TemperamentEnum temperament;
    private Long queenId;
    private boolean queenAlive;
    private boolean queenMarked;
    private LocalDate queenDateOfMark;
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

    public TaskFullView() {
    }

    public Long getId() {
        return id;
    }

    public TaskFullView setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public TaskFullView setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public TaskEnum getTask() {
        return task;
    }

    public TaskFullView setTask(TaskEnum task) {
        this.task = task;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public TaskFullView setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Long getBeehiveId() {
        return beehiveId;
    }

    public TaskFullView setBeehiveId(Long beehiveId) {
        this.beehiveId = beehiveId;
        return this;
    }

    public int getPower() {
        return power;
    }

    public TaskFullView setPower(int power) {
        this.power = power;
        return this;
    }

    public TemperamentEnum getTemperament() {
        return temperament;
    }

    public TaskFullView setTemperament(TemperamentEnum temperament) {
        this.temperament = temperament;
        return this;
    }

    public Long getQueenId() {
        return queenId;
    }

    public TaskFullView setQueenId(Long queenId) {
        this.queenId = queenId;
        return this;
    }

    public boolean isQueenAlive() {
        return queenAlive;
    }

    public TaskFullView setQueenAlive(boolean queenAlive) {
        this.queenAlive = queenAlive;
        return this;
    }

    public boolean isQueenMarked() {
        return queenMarked;
    }

    public TaskFullView setQueenMarked(boolean queenMarked) {
        this.queenMarked = queenMarked;
        return this;
    }

    public LocalDate getQueenDateOfMark() {
        return queenDateOfMark;
    }

    public TaskFullView setQueenDateOfMark(LocalDate queenDateOfMark) {
        this.queenDateOfMark = queenDateOfMark;
        return this;
    }

    public boolean isQueenActive() {
        return queenActive;
    }

    public TaskFullView setQueenActive(boolean queenActive) {
        this.queenActive = queenActive;
        return this;
    }

    public boolean isEggs() {
        return eggs;
    }

    public TaskFullView setEggs(boolean eggs) {
        this.eggs = eggs;
        return this;
    }

    public boolean isLarva() {
        return larva;
    }

    public TaskFullView setLarva(boolean larva) {
        this.larva = larva;
        return this;
    }

    public boolean isPuppa() {
        return puppa;
    }

    public TaskFullView setPuppa(boolean puppa) {
        this.puppa = puppa;
        return this;
    }

    public boolean isDisease() {
        return disease;
    }

    public TaskFullView setDisease(boolean disease) {
        this.disease = disease;
        return this;
    }

    public int getBeeFrames() {
        return beeFrames;
    }

    public TaskFullView setBeeFrames(int beeFrames) {
        this.beeFrames = beeFrames;
        return this;
    }

    public int getBroodFrames() {
        return broodFrames;
    }

    public TaskFullView setBroodFrames(int broodFrames) {
        this.broodFrames = broodFrames;
        return this;
    }

    public int getHoneyFrames() {
        return honeyFrames;
    }

    public TaskFullView setHoneyFrames(int honeyFrames) {
        this.honeyFrames = honeyFrames;
        return this;
    }

    public int getPollenFrames() {
        return pollenFrames;
    }

    public TaskFullView setPollenFrames(int pollenFrames) {
        this.pollenFrames = pollenFrames;
        return this;
    }

    public int getFoundationFrames() {
        return foundationFrames;
    }

    public TaskFullView setFoundationFrames(int foundationFrames) {
        this.foundationFrames = foundationFrames;
        return this;
    }
}
