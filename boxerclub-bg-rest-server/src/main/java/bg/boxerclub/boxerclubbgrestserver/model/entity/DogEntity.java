package bg.boxerclub.boxerclubbgrestserver.model.entity;

import bg.boxerclub.boxerclubbgrestserver.model.enums.Color;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Sex;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "dogs")
public class DogEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String registrationNum;
    private String pictureUrl;
    private String microChip;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Color color;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String healthStatus;

    private String kennel;
    @ManyToOne
    private UserEntity owner;
    @ManyToOne
    private DogEntity mother;
    @ManyToOne
    private DogEntity father;
    @Column(nullable = false)
    private Boolean isApproved;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfDecease;

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

    public Sex getSex() {
        return sex;
    }

    public DogEntity setSex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public DogEntity setColor(Color color) {
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

    public LocalDate getDateOfDecease() {
        return dateOfDecease;
    }

    public DogEntity setDateOfDecease(LocalDate dateOfDecease) {
        this.dateOfDecease = dateOfDecease;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DogEntity dogEntity)) return false;
        return Objects.equals(name, dogEntity.name)
                && Objects.equals(registrationNum, dogEntity.registrationNum)
                && Objects.equals(pictureUrl, dogEntity.pictureUrl)
                && Objects.equals(microChip, dogEntity.microChip)
                && Objects.equals(sex, dogEntity.sex)
                && Objects.equals(color, dogEntity.color)
                && Objects.equals(birthday, dogEntity.birthday)
                && Objects.equals(healthStatus, dogEntity.healthStatus)
                && Objects.equals(kennel, dogEntity.kennel)
                && Objects.equals(owner, dogEntity.owner)
                && Objects.equals(mother, dogEntity.mother)
                && Objects.equals(father, dogEntity.father)
                && Objects.equals(isApproved, dogEntity.isApproved)
                && Objects.equals(dateOfDecease, dogEntity.dateOfDecease);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, registrationNum, pictureUrl, microChip, sex, color, birthday, healthStatus, kennel, owner, mother, father, isApproved, dateOfDecease);
    }
}
