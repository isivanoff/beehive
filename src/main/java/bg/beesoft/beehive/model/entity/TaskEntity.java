package bg.beesoft.beehive.model.entity;

import bg.beesoft.beehive.model.entity.enums.TaskEnum;
import bg.beesoft.beehive.model.entity.enums.TemperamentEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class TaskEntity extends BaseEntity {
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskEnum task;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    private BeehiveEntity beehive;

    private int power;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TemperamentEnum temperament;

    @Column(nullable = false)
    private Long queenId;

    @Column(nullable = false)
    private boolean queenAlive;

    @Column(nullable = false)
    private boolean queenMarked;

    @Column(nullable = false)
    private LocalDate queenDateOfMark;

    @Column(nullable = false)
    private boolean queenActive;

    private boolean eggs;
    private boolean larva;
    private boolean puppa;
    private boolean disease;

    @Column(nullable = true)
    private int beeFrames;

    @Column(nullable = true)
    private int broodFrames;

    @Column(nullable = true)
    private int honeyFrames;

    @Column(nullable = true)
    private int pollenFrames;

    @Column(nullable = true)
    private int foundationFrames;


    public LocalDate getDate() {
        return date;
    }

    public int getPower() {
        return power;
    }

    public TaskEntity setPower(int power) {
        this.power = power;
        return this;
    }

    public TemperamentEnum getTemperament() {
        return temperament;
    }

    public TaskEntity setTemperament(TemperamentEnum temperament) {
        this.temperament = temperament;
        return this;
    }

    public boolean isEggs() {
        return eggs;
    }

    public TaskEntity setEggs(boolean eggs) {
        this.eggs = eggs;
        return this;
    }

    public boolean isLarva() {
        return larva;
    }

    public TaskEntity setLarva(boolean larva) {
        this.larva = larva;
        return this;
    }

    public boolean isPuppa() {
        return puppa;
    }

    public TaskEntity setPuppa(boolean puppa) {
        this.puppa = puppa;
        return this;
    }

    public boolean isDisease() {
        return disease;
    }

    public TaskEntity setDisease(boolean disease) {
        this.disease = disease;
        return this;
    }

    public int getBeeFrames() {
        return beeFrames;
    }

    public TaskEntity setBeeFrames(int beeFrames) {
        this.beeFrames = beeFrames;
        return this;
    }

    public int getBroodFrames() {
        return broodFrames;
    }

    public TaskEntity setBroodFrames(int broodFrames) {
        this.broodFrames = broodFrames;
        return this;
    }

    public int getHoneyFrames() {
        return honeyFrames;
    }

    public TaskEntity setHoneyFrames(int honeyFrames) {
        this.honeyFrames = honeyFrames;
        return this;
    }

    public int getPollenFrames() {
        return pollenFrames;
    }

    public TaskEntity setPollenFrames(int pollenFrames) {
        this.pollenFrames = pollenFrames;
        return this;
    }

    public int getFoundationFrames() {
        return foundationFrames;
    }

    public TaskEntity setFoundationFrames(int foundationFrames) {
        this.foundationFrames = foundationFrames;
        return this;
    }

    public TaskEntity setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public TaskEnum getTask() {
        return task;
    }

    public TaskEntity setTask(TaskEnum task) {
        this.task = task;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public TaskEntity setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public BeehiveEntity getBeehive() {
        return beehive;
    }

    public TaskEntity setBeehive(BeehiveEntity beehive) {
        this.beehive = beehive;
        return this;
    }
}
