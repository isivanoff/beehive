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
    private TaskEnum type;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne
    private BeehiveEntity beehive;

    public LocalDate getDate() {
        return date;
    }

    public TaskEntity setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public TaskEnum getType() {
        return type;
    }

    public TaskEntity setType(TaskEnum type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskEntity setDescription(String description) {
        this.description = description;
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
