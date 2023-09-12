package bg.boxerclub.boxerclubbgrestserver.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "health_status")
public class HealthStatusEntity extends BaseEntity {
    private String dnaInfo;
    private String chicNum;
    private String hips;
    private String ofaLink;
    private String offspringRegistrationNum;

    public String getDnaInfo() {
        return dnaInfo;
    }

    public HealthStatusEntity setDnaInfo(String dnaInfo) {
        this.dnaInfo = dnaInfo;
        return this;
    }

    public String getChicNum() {
        return chicNum;
    }

    public HealthStatusEntity setChicNum(String chicNum) {
        this.chicNum = chicNum;
        return this;
    }

    public String getHips() {
        return hips;
    }

    public HealthStatusEntity setHips(String hips) {
        this.hips = hips;
        return this;
    }

    public String getOfaLink() {
        return ofaLink;
    }

    public HealthStatusEntity setOfaLink(String ofaLink) {
        this.ofaLink = ofaLink;
        return this;
    }

    public String getOffspringRegistrationNum() {
        return offspringRegistrationNum;
    }

    public HealthStatusEntity setOffspringRegistrationNum(String offspringRegistrationNum) {
        this.offspringRegistrationNum = offspringRegistrationNum;
        return this;
    }
}
