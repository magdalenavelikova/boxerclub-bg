package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

import java.util.List;

public class DogChartNodeDTO {
    private String name;
    private DogAttributesDTO attributes;
    private List<DogChartNodeDTO> children;

    public DogChartNodeDTO() {
    }

    public String getName() {
        return name;
    }

    public DogChartNodeDTO setName(String name) {
        this.name = name;
        return this;
    }

    public DogAttributesDTO getAttributes() {
        return attributes;
    }

    public DogChartNodeDTO setAttributes(DogAttributesDTO attributes) {
        this.attributes = attributes;
        return this;
    }

    public List<DogChartNodeDTO> getChildren() {
        return children;
    }

    public DogChartNodeDTO setChildren(List<DogChartNodeDTO> children) {
        this.children = children;
        return this;
    }
}
