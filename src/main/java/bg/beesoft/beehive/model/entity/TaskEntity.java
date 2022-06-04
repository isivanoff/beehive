package bg.beesoft.beehive.model.entity;

import bg.beesoft.beehive.model.entity.enums.TaskEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class TaskEntity extends BaseEntity{
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private TaskEnum description;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String notes;

    @ManyToOne
    private BeehiveEntity beehive;

    public LocalDate getDate() {
        return date;
    }

    public TaskEntity setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public TaskEnum getDescription() {
        return description;
    }

    public TaskEntity setDescription(TaskEnum description) {
        this.description = description;
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
