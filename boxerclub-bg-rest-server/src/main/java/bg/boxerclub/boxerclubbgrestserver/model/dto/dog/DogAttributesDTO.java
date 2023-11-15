package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

public class DogAttributesDTO {
    private String sex;
    private String color;

    public DogAttributesDTO() {
    }

    public String getSex() {
        return sex;
    }

    public DogAttributesDTO setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getColor() {
        return color;
    }

    public DogAttributesDTO setColor(String color) {
        this.color = color;
        return this;
    }
}
