package bg.beesoft.beehive.model.view;

import bg.beesoft.beehive.model.entity.enums.BeeHiveTypeEnum;

public class BeehiveView {

    private Long id;
    private int referenceNumber;
    private BeeHiveTypeEnum type;
    private String color;
    private boolean isAlive;
    private String imageUrl;

    public BeehiveView() {
    }

    public Long getId() {
        return id;
    }

    public BeehiveView setId(Long id) {
        this.id = id;
        return this;
    }

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public BeehiveView setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public BeeHiveTypeEnum getType() {
        return type;
    }

    public BeehiveView setType(BeeHiveTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getColor() {
        return color;
    }

    public BeehiveView setColor(String color) {
        this.color = color;
        return this;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public BeehiveView setAlive(boolean alive) {
        isAlive = alive;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BeehiveView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
