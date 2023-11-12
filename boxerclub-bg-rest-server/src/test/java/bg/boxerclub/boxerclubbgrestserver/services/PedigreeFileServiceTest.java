package bg.boxerclub.boxerclubbgrestserver.services;

import bg.boxerclub.boxerclubbgrestserver.model.dto.pedigree.PedigreeFileUploadDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.PedigreeFileEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Color;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Sex;
import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.PedigreeFileRepository;
import bg.boxerclub.boxerclubbgrestserver.service.dog.PedigreeFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PedigreeFileServiceTest {
    @Mock
    private PedigreeFileRepository mockPedigreeFileRepository;
    @Mock
    private DogRepository mockDogRepository;
    @Mock
    private PedigreeFileEntity mockSaved;
    private PedigreeFileService toTest;
    private PedigreeFileUploadDto testPedigreeFileUploadDto;
    private PedigreeFileEntity testPedigreeFileEntity;
    private MultipartFile testFile;
    private DogEntity testDogEntity;

    @BeforeEach
    void setUp() {


        toTest = new PedigreeFileService(mockPedigreeFileRepository, mockDogRepository);
        testDogEntity = new DogEntity() {
            {
                setId(1L);
                setName("New");
                setRegistrationNum("111");
                setPictureUrl("");
                setMicroChip("111");
                setSex(Sex.Male);
                setColor(Color.Brindle);
                setBirthday(LocalDate.of(2023, 10, 10));
                setHealthStatus("ok");
                setKennel("NA");
            }
        };
        testPedigreeFileEntity = new PedigreeFileEntity() {
            {
                setId(1L);
                setContentType("pdf");
                setDogEntity(testDogEntity);

            }
        };
        testFile = null;

        testPedigreeFileUploadDto = new PedigreeFileUploadDto() {
            {
                setFile(testFile);
                setDogId("1");
            }

        };

        when(mockDogRepository.findById(1L)).thenReturn(Optional.of(testDogEntity));
        when(mockPedigreeFileRepository.findById(1L)).thenReturn(Optional.of(testPedigreeFileEntity));
        when(mockPedigreeFileRepository.findPedigreeFileEntityByDogEntityId(1L)).thenReturn(Optional.of(testPedigreeFileEntity));

    }

    @Test
    void testFindPedigreeByDogId() {
        boolean actual = toTest.findPedigreeByDogId(1L);
        assertTrue(actual);

    }

    @Test
    void testDeletePedigreeByDogId() {
        toTest.deleteByDogId(1L);
        verify(mockPedigreeFileRepository, times(1)).deletePedigreeFileEntitiesByDogEntityId(1L);
    }

//    @Test
//    void uploadTest() throws IOException {
//        testFile = new MockMultipartFile("pdf", new byte[5656654]);
//        PedigreeFileEntity newPedigree = testPedigreeFileEntity;
//        when(mockPedigreeFileRepository.save(newPedigree)).thenReturn(newPedigree);
//        when(mockPedigreeFileRepository.save(newPedigree).getId()).thenReturn(1L);
//        toTest.upload(testFile, 1L);
//        verify(mockPedigreeFileRepository, times(1)).save(any());
//
//    }

}
