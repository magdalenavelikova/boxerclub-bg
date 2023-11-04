package bg.boxerclub.boxerclubbgrestserver.validation;

import bg.boxerclub.boxerclubbgrestserver.model.entity.LinkEntity;
import org.springframework.batch.item.ItemProcessor;

public class ValidationProcessor implements ItemProcessor<LinkEntity, LinkEntity> {
    @Override
    public LinkEntity process(LinkEntity item) throws Exception {
        //todo validations
        return item;
    }
}
