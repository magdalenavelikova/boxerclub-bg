package bg.boxerclub.boxerclubbgrestserver.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "dogs")
public class DogEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String registrationNum;
    private String pictureUrl;
    private String microChip;
    @Column(nullable = false)
    private String sex;
    @Column(nullable = false)
    private String color;
    // @Column(nullable = false)
    private LocalDate birthday;


    private String healthStatus;
    // @Column(nullable = false)
    private String kennel;
    @ManyToOne
    private UserEntity owner;
    @ManyToOne
    private DogEntity mother;
    @ManyToOne
    private DogEntity father;
    @Column(nullable = false)
    private Boolean isApproved;
    private LocalDate dateOfDeath;

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

    public String getName() {
        return name;
    }

    public DogEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public DogEntity setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
        return this;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public DogEntity setApproved(Boolean approved) {
        isApproved = approved;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public DogEntity setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    public DogEntity setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogEntity dogEntity = (DogEntity) o;
        return Objects.equals(name, dogEntity.name) && Objects.equals(registrationNum, dogEntity.registrationNum) && Objects.equals(pictureUrl, dogEntity.pictureUrl) && Objects.equals(microChip, dogEntity.microChip) && Objects.equals(sex, dogEntity.sex) && Objects.equals(color, dogEntity.color) && Objects.equals(birthday, dogEntity.birthday) && Objects.equals(healthStatus, dogEntity.healthStatus) && Objects.equals(kennel, dogEntity.kennel) && Objects.equals(owner, dogEntity.owner) && Objects.equals(mother, dogEntity.mother) && Objects.equals(father, dogEntity.father);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, registrationNum, pictureUrl, microChip, sex, color, birthday, healthStatus, kennel, owner, mother, father);
    }
}
