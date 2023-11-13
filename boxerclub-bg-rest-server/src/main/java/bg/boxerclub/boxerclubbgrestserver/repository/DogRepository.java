package bg.boxerclub.boxerclubbgrestserver.repository;

import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DogRepository extends JpaRepository<DogEntity, Long> {

    Optional<DogEntity> findDogEntityByRegistrationNum(String regNumber);

    List<DogEntity> findAllByIsApprovedTrue();


    List<DogEntity> findAllByMotherIdOrFatherId(Long motherId, Long fatherId);

    List<DogEntity> findAllByMotherId(Long motherId);

    List<DogEntity> findAllByFatherId(Long fatherId);
}
