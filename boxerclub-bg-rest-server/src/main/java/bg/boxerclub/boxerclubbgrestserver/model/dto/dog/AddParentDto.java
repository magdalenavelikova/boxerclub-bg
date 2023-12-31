package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AddParentDto {
    @NotNull
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String childId;
    @NotEmpty
    private String sex;

    public AddParentDto() {
    }

    public Long getId() {
        return id;
    }

    public AddParentDto setId(Long id) {
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
