package bg.boxerclub.boxerclubbgrestserver.repository;

import bg.boxerclub.boxerclubbgrestserver.model.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinksRepository extends JpaRepository<LinkEntity, Long> {
}
