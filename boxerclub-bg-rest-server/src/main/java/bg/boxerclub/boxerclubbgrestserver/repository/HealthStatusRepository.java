package bg.boxerclub.boxerclubbgrestserver.repository;

import bg.boxerclub.boxerclubbgrestserver.model.entity.HealthStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthStatusRepository extends JpaRepository<HealthStatusEntity, Long> {
}
