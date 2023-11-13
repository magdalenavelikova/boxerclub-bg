package bg.boxerclub.boxerclubbgrestserver.services;

import bg.boxerclub.boxerclubbgrestserver.model.entity.BlackListEntity;
import bg.boxerclub.boxerclubbgrestserver.repository.BlackListRepository;
import bg.boxerclub.boxerclubbgrestserver.service.BlackListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BlackListServiceTest {
    @Mock
    private BlackListRepository mockBlackListRepository;

    private BlackListService toTest;


    public BlackListServiceTest() {
    }

    @BeforeEach
    void setUp() {
        toTest = new BlackListService(mockBlackListRepository);
        BlackListEntity testLinkBlackListEntity = new BlackListEntity() {
            {
                setId(1L);
                setIp("103.113.68");
            }
        };
        when(mockBlackListRepository.getBlackListEntitiesByIp("103.113.68")).thenReturn(Optional.of(testLinkBlackListEntity));
        when(mockBlackListRepository.getBlackListEntitiesByIp("127.0.0")).thenReturn(Optional.empty());
    }

    @Test
    void testIsBlacklisted() {
        assertTrue(toTest.isBlacklisted("103.113.68"));
    }

    @Test
    void testIsBlacklisted_WhenIsNot() {
        assertFalse(toTest.isBlacklisted("127.0.0"));
    }

}
