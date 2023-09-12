package bg.boxerclub.boxerclubbgrestserver.repository;

import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends JpaRepository<DogEntity, Long> {
}
