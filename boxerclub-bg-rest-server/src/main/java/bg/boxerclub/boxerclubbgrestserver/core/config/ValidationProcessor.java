package bg.boxerclub.boxerclubbgrestserver.core.config;

import bg.boxerclub.boxerclubbgrestserver.model.entity.LinkEntity;
import org.springframework.batch.item.ItemProcessor;

public class ValidationProcessor implements ItemProcessor<LinkEntity, LinkEntity> {

    @Override
    public LinkEntity process(LinkEntity link) throws Exception {
        //todo validations
        return link;
    }
}
