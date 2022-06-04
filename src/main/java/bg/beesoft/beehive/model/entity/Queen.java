package bg.beesoft.beehive.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "queens")
public class Queen extends BaseEntity{
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

    public Queen setAlive(boolean alive) {
        isAlive = alive;
        return this;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public Queen setMarked(boolean marked) {
        isMarked = marked;
        return this;
    }

    public LocalDate getDateOfMark() {
        return dateOfMark;
    }

    public Queen setDateOfMark(LocalDate dateOfMark) {
        this.dateOfMark = dateOfMark;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public Queen setActive(boolean active) {
        isActive = active;
        return this;
    }
}
