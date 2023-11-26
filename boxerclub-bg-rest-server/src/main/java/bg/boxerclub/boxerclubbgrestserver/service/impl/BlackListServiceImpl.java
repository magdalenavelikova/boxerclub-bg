package bg.boxerclub.boxerclubbgrestserver.service.impl;

import bg.boxerclub.boxerclubbgrestserver.model.entity.BlackListEntity;
import bg.boxerclub.boxerclubbgrestserver.repository.BlackListRepository;
import bg.boxerclub.boxerclubbgrestserver.service.BlackListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListServiceImpl implements BlackListService {
    private final BlackListRepository blackListRepository;

    public BlackListServiceImpl(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    @Override
    public boolean isBlacklisted(String ip) {

        return blackListRepository.getBlackListEntitiesByIp(ip).isPresent();
    }

    @Override
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
