package bg.boxerclub.boxerclubbgrestserver.service;

public interface BlackListService {
    boolean isBlacklisted(String ip);

    void init();
}
