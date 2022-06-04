package bg.beesoft.beehive.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "queens")
public class QueenEntity extends BaseEntity{
    @Column(nullable = false)
    boolean isAlive;

    @Column(nullable = false)
    boolean isMarked;

    LocalDate dateOfMark;

    @Column(nullable = false)
    boolean isActive;

    public boolean isAlive() {
        return isAlive;
    }

    public QueenEntity setAlive(boolean alive) {
        isAlive = alive;
        return this;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public QueenEntity setMarked(boolean marked) {
        isMarked = marked;
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
        return isActive;
    }

    public QueenEntity setActive(boolean active) {
        isActive = active;
        return this;
    }
}
