package bg.boxerclub.boxerclubbgrestserver.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "dogs")
public class DogEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String registrationNum;
    private String microChip;
    @Column(nullable = false)
    private String sex;
    @Column(nullable = false)
    private String color;
    @Column(nullable = false)
    private LocalDate birthday;
    @OneToOne(fetch = FetchType.EAGER)
    private HealthStatusEntity healthStatusEntity;
    private String kennel;
    @ManyToOne
    private UserEntity owner;
    @ManyToOne
    private DogEntity mother;
    @ManyToOne
    private DogEntity father;
    @Column(nullable = false)
    private Boolean isApproved;

    public String getRegistrationNum() {
        return registrationNum;
    }

    public DogEntity setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
        return this;
    }

    public String getMicroChip() {
        return microChip;
    }

    public DogEntity setMicroChip(String microChip) {
        this.microChip = microChip;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public DogEntity setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getColor() {
        return color;
    }

    public DogEntity setColor(String color) {
        this.color = color;
        return this;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public DogEntity setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public HealthStatusEntity getHealthStatus() {
        return healthStatusEntity;
    }

    public DogEntity setHealthStatus(HealthStatusEntity healthStatusEntity) {
        this.healthStatusEntity = healthStatusEntity;
        return this;
    }

    public String getKennel() {
        return kennel;
    }

    public DogEntity setKennel(String kennel) {
        this.kennel = kennel;
        return this;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public DogEntity setOwner(UserEntity owner) {
        this.owner = owner;
        return this;
    }

    public DogEntity getMother() {
        return mother;
    }

    public DogEntity setMother(DogEntity mother) {
        this.mother = mother;
        return this;
    }

    public DogEntity getFather() {
        return father;
    }

    public DogEntity setFather(DogEntity father) {
        this.father = father;
        return this;
    }
}
