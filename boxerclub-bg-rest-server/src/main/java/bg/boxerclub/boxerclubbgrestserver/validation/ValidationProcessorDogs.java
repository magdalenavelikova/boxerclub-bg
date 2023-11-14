package bg.boxerclub.boxerclubbgrestserver.validation;

import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import org.springframework.batch.item.ItemProcessor;

public class ValidationProcessorDogs implements ItemProcessor<DogEntity, DogEntity> {
    @Override
    public DogEntity process(DogEntity item) throws Exception {
        //todo validations
        return item;
    }
}
