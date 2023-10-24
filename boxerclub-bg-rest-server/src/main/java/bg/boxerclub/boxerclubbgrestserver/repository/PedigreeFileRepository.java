package bg.boxerclub.boxerclubbgrestserver.repository;

import bg.boxerclub.boxerclubbgrestserver.model.entity.PedigreeFileEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PedigreeFileRepository extends JpaRepository<PedigreeFileEntity, Long> {

    void deletePedigreeFileEntitiesByDogEntityId(Long dogEntity_id);
}
