package bg.boxerclub.boxerclubbgrestserver.model.dto.contact;

import jakarta.validation.constraints.NotEmpty;

public class ContactDto {
    private Long Id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String nameBG;
    @NotEmpty
    private String position;
    @NotEmpty
    private String positionBG;
    @NotEmpty
    private String country;
    @NotEmpty
    private String countryBG;
    @NotEmpty
    private String city;
    @NotEmpty
    private String cityBG;
    @NotEmpty
    private String zip;
    @NotEmpty
    private String address;
    @NotEmpty
    private String addressBG;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phone;

    public ContactDto() {
    }

    public Long getId() {
        return Id;
    }

    public ContactDto setId(Long id) {
        Id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ContactDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameBG() {
        return nameBG;
    }

    public ContactDto setNameBG(String nameBG) {
        this.nameBG = nameBG;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public ContactDto setPosition(String position) {
        this.position = position;
        return this;
    }

    public String getPositionBG() {
        return positionBG;
    }

    public ContactDto setPositionBG(String positionBG) {
        this.positionBG = positionBG;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ContactDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCountryBG() {
        return countryBG;
    }

    public ContactDto setCountryBG(String countryBG) {
        this.countryBG = countryBG;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ContactDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCityBG() {
        return cityBG;
    }

    public ContactDto setCityBG(String cityBG) {
        this.cityBG = cityBG;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public ContactDto setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public ContactDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getAddressBG() {
        return addressBG;
    }

    public ContactDto setAddressBG(String addressBG) {
        this.addressBG = addressBG;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ContactDto setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
