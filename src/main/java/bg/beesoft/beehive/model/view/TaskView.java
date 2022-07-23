package bg.beesoft.beehive.model.view;

import bg.beesoft.beehive.model.entity.enums.TaskEnum;

import java.time.LocalDate;

public class TaskView {
    private Long id;
    private LocalDate date;
    private TaskEnum description;
    private String notes;

    public TaskView() {
    }

    public Long getId() {
        return id;
    }

    public TaskView setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public TaskView setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public TaskEnum getDescription() {
        return description;
    }

    public TaskView setDescription(TaskEnum description) {
        this.description = description;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public TaskView setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}
