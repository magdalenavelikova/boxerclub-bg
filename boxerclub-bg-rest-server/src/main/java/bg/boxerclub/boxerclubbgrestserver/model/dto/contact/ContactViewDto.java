package bg.boxerclub.boxerclubbgrestserver.model.dto.contact;

public class ContactViewDto {
    private Long Id;

    private String name;

    private String nameBG;

    private String position;

    private String positionBG;

    private String country;

    private String countryBG;

    private String city;

    private String cityBG;

    private String zip;

    private String address;

    private String addressBG;

    private String email;

    private String phone;

    public ContactViewDto() {
    }

    public Long getId() {
        return Id;
    }

    public ContactViewDto setId(Long id) {
        Id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ContactViewDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameBG() {
        return nameBG;
    }

    public ContactViewDto setNameBG(String nameBG) {
        this.nameBG = nameBG;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public ContactViewDto setPosition(String position) {
        this.position = position;
        return this;
    }

    public String getPositionBG() {
        return positionBG;
    }

    public ContactViewDto setPositionBG(String positionBG) {
        this.positionBG = positionBG;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ContactViewDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCountryBG() {
        return countryBG;
    }

    public ContactViewDto setCountryBG(String countryBG) {
        this.countryBG = countryBG;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ContactViewDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCityBG() {
        return cityBG;
    }

    public ContactViewDto setCityBG(String cityBG) {
        this.cityBG = cityBG;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public ContactViewDto setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public ContactViewDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getAddressBG() {
        return addressBG;
    }

    public ContactViewDto setAddressBG(String addressBG) {
        this.addressBG = addressBG;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactViewDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ContactViewDto setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
