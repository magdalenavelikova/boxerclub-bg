package bg.boxerclub.boxerclubbgrestserver.services;

import bg.boxerclub.boxerclubbgrestserver.exception.LinkNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.LinkEntity;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.LinkMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.LinkRepository;
import bg.boxerclub.boxerclubbgrestserver.service.impl.LinkServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LinkServiceTest {
    @Mock
    private LinkRepository mockLinkRepository;
    @Mock
    private LinkMapper mockLinkMapper;
    private LinkDto testLinkDto;
    private LinkViewDto testLinkViewDto;
    private LinkServiceImpl toTest;
    private LinkEntity testLinkEntity;
    @Captor
    private ArgumentCaptor<LinkEntity> linkEntityArgumentCaptor;

    public LinkServiceTest() {
    }

    @BeforeEach
    void setUp() {
        toTest = new LinkServiceImpl(mockLinkRepository, mockLinkMapper);
        testLinkEntity = new LinkEntity() {
            {
                setId(1L);
                setTitle("Link");
                setType("Boxer Club");
                setDescription("Description");
                setUrlLink("URL Link");
            }
        };


        testLinkDto = new LinkDto() {
            {
                setId(1L);
                setTitle("Link");
                setType("Boxer Club");
                setDescription("Description");
                setUrlLink("URL Link");
            }

        };
        testLinkViewDto = new LinkViewDto() {
            {
                setId(1L);
                setTitle("Link");
                setType("Boxer Club");
                setDescription("Description");
                setUrlLink("URL Link");
            }
        };
        when(mockLinkRepository.findAll()).thenReturn(List.of(testLinkEntity));
        when(mockLinkRepository.findById(1L)).thenReturn(Optional.of(testLinkEntity));
        when(mockLinkRepository.findById(2L)).thenReturn(Optional.empty());
        when(mockLinkMapper.linkEntityToLinkViewDto(testLinkEntity)).thenReturn(testLinkViewDto);
        when(mockLinkMapper.linkDtoToLinkEntity(testLinkDto)).thenReturn(testLinkEntity);

    }

    @Test
    void testAddLink_SaveInvoked() {
        toTest.addLink(testLinkDto);
        Mockito.verify(mockLinkRepository).save(any());
    }

    @Test
    void testAddLink_withCaptor() {
        toTest.addLink(testLinkDto);
        Mockito.verify(mockLinkRepository).save(linkEntityArgumentCaptor.capture());
        LinkEntity savedLinkEntity = linkEntityArgumentCaptor.getValue();
        assertEquals(testLinkDto.getUrlLink(), savedLinkEntity.getUrlLink());
        assertEquals(testLinkDto.getType(), savedLinkEntity.getType());
        assertEquals(testLinkDto.getTitle(), savedLinkEntity.getTitle());
        assertEquals(testLinkDto.getDescription(), savedLinkEntity.getDescription());

    }

    @Test
    void testGetAll() {
        List<LinkViewDto> expected = List.of(testLinkViewDto);
        assertEquals(expected, toTest.getAll());
    }

    @Test
    void testDeleteLinkWhenExist() {
        toTest.deleteLink(1L);
        Mockito.verify(mockLinkRepository, times(1)).deleteById(1L);

    }

    @Test()
    void testDeleteLinkWhenNotExist() {
        Exception exception = assertThrows(LinkNotFoundException.class, () -> toTest.deleteLink(2L));
        String expectedMessage = "Link with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Mockito.verify(mockLinkRepository, times(0)).deleteById(2L);
    }

    @Test
    void testEditLinkWhenIsUpdated() {
        LinkDto edited = testLinkDto;
        edited.setTitle("New");
        when(mockLinkRepository.findById(edited.getId())).thenReturn(Optional.of(testLinkEntity));
        LinkEntity testTempLinkEntity = new LinkEntity() {{
            setId(1L);
            setTitle(edited.getTitle());
            setType("Boxer Club");
            setDescription("Description");
            setUrlLink("URL Link");
        }};

        when(mockLinkMapper.linkDtoToLinkEntity(edited)).thenReturn(testTempLinkEntity);
        toTest.editLink(edited.getId(), edited);
        Mockito.verify(mockLinkRepository, times(1)).findById(testLinkEntity.getId());
        Mockito.verify(mockLinkRepository, times(1)).save(testTempLinkEntity);
    }

    @Test
    void testEditLinkWhenIsNotUpdated() {
        when(mockLinkMapper.linkDtoToLinkEntity(testLinkDto)).thenReturn(testLinkEntity);
        toTest.editLink(testLinkDto.getId(), testLinkDto);
        Mockito.verify(mockLinkRepository, times(1)).findById(testLinkEntity.getId());
        Mockito.verify(mockLinkRepository, times(0)).save(testLinkEntity);

    }

    @Test
    void testEditLinkWhenLinkIsNotFound() {

        Exception exception = assertThrows(LinkNotFoundException.class, () -> toTest.editLink(2L, testLinkDto));
        String expectedMessage = "Link with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Mockito.verify(mockLinkRepository, times(1)).findById(2L);
        Mockito.verify(mockLinkRepository, times(0)).save(testLinkEntity);
    }
}
