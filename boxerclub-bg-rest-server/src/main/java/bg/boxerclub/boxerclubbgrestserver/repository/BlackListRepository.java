package bg.boxerclub.boxerclubbgrestserver.repository;

import bg.boxerclub.boxerclubbgrestserver.model.entity.BlackListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListRepository extends JpaRepository<BlackListEntity, Long> {
    Optional<BlackListEntity> getBlackListEntitiesByIp(String ip);
}
