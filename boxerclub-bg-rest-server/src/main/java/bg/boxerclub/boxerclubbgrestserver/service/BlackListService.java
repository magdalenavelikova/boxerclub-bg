package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.entity.BlackListEntity;
import bg.boxerclub.boxerclubbgrestserver.repository.BlackListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListService {
    private final BlackListRepository blackListRepository;

    public BlackListService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public boolean isBlacklisted(String ip) {

        return blackListRepository.getBlackListEntitiesByIp(ip).isPresent();
    }

    public void init() {
        List<String> blackListIps = List.of(
                "102.38.250",
                "103.113.68",
                "103.136.43",
                "103.145.20",
                "103.146.97",
                "103.160.59",
                "103.225.77",
                "103.76.52",
                "104.109.128",
                "104.166.148",
                "104.166.175");
        if (blackListRepository.count() == 0) {
            blackListIps.forEach(ip ->
                    blackListRepository.save(new BlackListEntity(ip))
            );
        }
    }
}
