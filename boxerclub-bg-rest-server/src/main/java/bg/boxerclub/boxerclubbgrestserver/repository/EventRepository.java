package bg.boxerclub.boxerclubbgrestserver.repository;

import bg.boxerclub.boxerclubbgrestserver.model.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
