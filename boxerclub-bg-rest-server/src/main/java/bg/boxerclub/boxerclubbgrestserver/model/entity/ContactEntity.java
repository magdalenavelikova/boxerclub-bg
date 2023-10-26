package bg.boxerclub.boxerclubbgrestserver.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contacts")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String nameBG;
    @Column(nullable = false)
    private String position;
    @Column(nullable = false)
    private String positionBG;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String countryBG;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String cityBG;
    @Column(nullable = false)
    private String zip;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String addressBG;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;


    public ContactEntity() {
    }

    public ContactEntity(String name, String nameBG, String position, String positionBG, String country, String countryBG, String city, String cityBG, String zip, String address, String addressBG, String email, String phone) {
        this.name = name;
        this.nameBG = nameBG;
        this.position = position;
        this.positionBG = positionBG;
        this.country = country;
        this.countryBG = countryBG;
        this.city = city;
        this.cityBG = cityBG;
        this.zip = zip;
        this.address = address;
        this.addressBG = addressBG;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return Id;
    }

    public ContactEntity setId(Long id) {
        Id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ContactEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameBG() {
        return nameBG;
    }

    public ContactEntity setNameBG(String nameBG) {
        this.nameBG = nameBG;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public ContactEntity setPosition(String position) {
        this.position = position;
        return this;
    }

    public String getPositionBG() {
        return positionBG;
    }

    public ContactEntity setPositionBG(String positionBG) {
        this.positionBG = positionBG;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ContactEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCountryBG() {
        return countryBG;
    }

    public ContactEntity setCountryBG(String countryBG) {
        this.countryBG = countryBG;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ContactEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCityBG() {
        return cityBG;
    }

    public ContactEntity setCityBG(String cityBG) {
        this.cityBG = cityBG;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public ContactEntity setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public ContactEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getAddressBG() {
        return addressBG;
    }

    public ContactEntity setAddressBG(String addressBG) {
        this.addressBG = addressBG;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ContactEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}