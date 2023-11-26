package bg.boxerclub.boxerclubbgrestserver.service.dog;

import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.DogChartNodeDTO;

public interface DogChartService {
    DogChartNodeDTO createDogChart(Long id, int depth);
}
