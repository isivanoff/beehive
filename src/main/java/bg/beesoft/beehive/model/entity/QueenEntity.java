package bg.beesoft.beehive.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "queens")
public class QueenEntity extends BaseEntity{
    @Column(nullable = false)
    boolean alive;

    @Column(nullable = false)
    boolean marked;

    LocalDate dateOfMark;

    @Column(nullable = false)
    boolean active;

    public boolean isAlive() {
        return alive;
    }

    public QueenEntity setAlive(boolean alive) {
        this.alive = alive;
        return this;
    }

    public boolean isMarked() {
        return marked;
    }

    public QueenEntity setMarked(boolean marked) {
        this.marked = marked;
        return this;
    }

    public LocalDate getDateOfMark() {
        return dateOfMark;
    }

    public QueenEntity setDateOfMark(LocalDate dateOfMark) {
        this.dateOfMark = dateOfMark;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public QueenEntity setActive(boolean active) {
        this.active = active;
        return this;
    }
}
