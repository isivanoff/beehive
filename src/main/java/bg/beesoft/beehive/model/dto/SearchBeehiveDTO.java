package bg.beesoft.beehive.model.dto;

import bg.beesoft.beehive.model.entity.enums.BeeHiveTypeEnum;

public class SearchBeehiveDTO {
    private Integer referenceNumber;

    private BeeHiveTypeEnum type;

    public SearchBeehiveDTO() {
    }

    public Integer getReferenceNumber() {
        return referenceNumber;
    }

    public SearchBeehiveDTO setReferenceNumber(Integer referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public BeeHiveTypeEnum getType() {
        return type;
    }

    public SearchBeehiveDTO setType(BeeHiveTypeEnum type) {
        this.type = type;
        return this;
    }

    public boolean isEmpty() {
        return type==null && referenceNumber==null;
    }
}
