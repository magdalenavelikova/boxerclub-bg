package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

import java.util.ArrayList;
import java.util.List;

public class DogDetailsDto {
    private DogViewDto dog;

    private List<DogViewDto> parents = new ArrayList<>();

    public DogDetailsDto() {
    }

    public DogViewDto getDog() {
        return dog;
    }

    public DogDetailsDto setDog(DogViewDto dog) {
        this.dog = dog;
        return this;
    }

    public List<DogViewDto> getParents() {
        return parents;
    }

    public DogDetailsDto setParents(List<DogViewDto> parents) {
        this.parents = parents;
        return this;
    }
}
