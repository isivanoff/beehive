package bg.beesoft.beehive.model.view;

import bg.beesoft.beehive.model.entity.QueenEntity;
import bg.beesoft.beehive.model.entity.enums.BeeHiveTypeEnum;
import bg.beesoft.beehive.model.entity.enums.ColorEnum;

import java.util.List;

public class BeehiveFullView {
    private Long id;
    private int referenceNumber;
    private BeeHiveTypeEnum type;
    private ColorEnum color;
    private boolean isAlive;
    private String imageUrl;
    private QueenEntity queen;
    private String apiaryName;
    private Long apiaryId;

    public Long getApiaryId() {
        return apiaryId;
    }

    public BeehiveFullView setApiaryId(Long apiaryId) {
        this.apiaryId = apiaryId;
        return this;
    }

    private List<TaskView> tasks;

    public BeehiveFullView() {
    }

    public Long getId() {
        return id;
    }

    public BeehiveFullView setId(Long id) {
        this.id = id;
        return this;
    }

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public BeehiveFullView setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public BeeHiveTypeEnum getType() {
        return type;
    }

    public BeehiveFullView setType(BeeHiveTypeEnum type) {
        this.type = type;
        return this;
    }

    public ColorEnum getColor() {
        return color;
    }

    public BeehiveFullView setColor(ColorEnum color) {
        this.color = color;
        return this;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public BeehiveFullView setAlive(boolean alive) {
        isAlive = alive;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BeehiveFullView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public QueenEntity getQueen() {
        return queen;
    }

    public BeehiveFullView setQueen(QueenEntity queen) {
        this.queen = queen;
        return this;
    }

    public String getApiaryName() {
        return apiaryName;
    }

    public BeehiveFullView setApiaryName(String apiaryName) {
        this.apiaryName = apiaryName;
        return this;
    }

    public List<TaskView> getTasks() {
        return tasks;
    }

    public BeehiveFullView setTasks(List<TaskView> tasks) {
        this.tasks = tasks;
        return this;
    }

    @Override
    public String toString() {
        return "BeehiveFullView{" +
                "id=" + id +
                ", referenceNumber=" + referenceNumber +
                ", type=" + type +
                ", color=" + color +
                ", isAlive=" + isAlive +
                ", imageUrl='" + imageUrl + '\'' +
                ", queen=" + queen +
                ", apiaryName='" + apiaryName + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
