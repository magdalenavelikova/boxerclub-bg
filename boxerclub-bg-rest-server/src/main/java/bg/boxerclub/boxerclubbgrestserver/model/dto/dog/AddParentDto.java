package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

import jakarta.validation.constraints.NotEmpty;

public class AddParentDto {
    @NotEmpty
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String childId;
    @NotEmpty
    private String sex;

    public AddParentDto() {
    }

    public String getId() {
        return id;
    }

    public AddParentDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AddParentDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getChildId() {
        return childId;
    }

    public AddParentDto setChildId(String childId) {
        this.childId = childId;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public AddParentDto setSex(String sex) {
        this.sex = sex;
        return this;
    }
}
