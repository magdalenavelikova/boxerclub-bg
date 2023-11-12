package bg.boxerclub.boxerclubbgrestserver.services;

import bg.boxerclub.boxerclubbgrestserver.exception.ContactNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.ContactEntity;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.ContactMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.ContactRepository;
import bg.boxerclub.boxerclubbgrestserver.service.ContactService;
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
public class ContactServiceTest {
    @Mock
    private ContactRepository mockContactRepository;
    @Mock
    private ContactMapper mockContactMapper;
    private ContactDto testContactDto;
    private ContactViewDto testContactViewDto;
    private ContactService toTest;
    private ContactEntity testContactEntity;
    @Captor
    private ArgumentCaptor<ContactEntity> contactEntityArgumentCaptor;

    public ContactServiceTest() {
    }

    @BeforeEach
    void setUp() {


        toTest = new ContactService(mockContactRepository, mockContactMapper);
        testContactEntity = new ContactEntity() {
            {
                setId(1L);
                setName("Contact");
                setPosition("President");
                setAddress("Address");
                setEmail("test@test.bg");
            }
        };


        testContactDto = new ContactDto() {
            {
                setId(1L);
                setName("Contact");
                setPosition("President");
                setAddress("Address");
                setEmail("test@test.bg");
            }

        };
        testContactViewDto = new ContactViewDto() {
            {
                setId(1L);
                setName("Contact");
                setPosition("President");
                setAddress("Address");
                setEmail("test@test.bg");
            }
        };
        when(mockContactRepository.findAll()).thenReturn(List.of(testContactEntity));
        when(mockContactRepository.findById(1L)).thenReturn(Optional.of(testContactEntity));
        when(mockContactRepository.findById(2L)).thenReturn(Optional.empty());
        when(mockContactMapper.contactEntityToContactViewDto(testContactEntity)).thenReturn(testContactViewDto);
        when(mockContactMapper.contactDtoToContactEntity(testContactDto)).thenReturn(testContactEntity);

    }

    @Test
    void addContact_SaveInvokedTest() {
        toTest.addContact(testContactDto);
        Mockito.verify(mockContactRepository).save(any());
    }

    @Test
    void addContact_withCaptorTest() {
        toTest.addContact(testContactDto);
        Mockito.verify(mockContactRepository).save(contactEntityArgumentCaptor.capture());
        ContactEntity savedContactEntity = contactEntityArgumentCaptor.getValue();
        assertEquals(testContactDto.getName(), savedContactEntity.getName());
        assertEquals(testContactDto.getEmail(), savedContactEntity.getEmail());
        assertEquals(testContactDto.getAddress(), savedContactEntity.getAddress());
        assertEquals(testContactDto.getPosition(), savedContactEntity.getPosition());

    }

    @Test
    void getAllTest() {
        List<ContactViewDto> expected = List.of(testContactViewDto);
        assertEquals(expected, toTest.getAll());
    }

    @Test
    void deleteContactWhenExist_SaveInvoked() {
        toTest.deleteContact(1L);
        Mockito.verify(mockContactRepository, times(1)).deleteById(1L);

    }

    @Test()
    void deleteContactWhenNotExist() {
        Exception exception = assertThrows(ContactNotFoundException.class, () -> toTest.deleteContact(2L));
        String expectedMessage = "Contact with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Mockito.verify(mockContactRepository, times(0)).deleteById(2L);
    }

    @Test
    void editContactTestWhenIsUpdated() {
        ContactDto edited = testContactDto;
        edited.setName("New");
        when(mockContactRepository.findById(edited.getId())).thenReturn(Optional.of(testContactEntity));
        ContactEntity testTempContactEntity = new ContactEntity() {{
            setId(1L);
            setName(edited.getName());
            setPosition("President");
            setAddress("Address");
            setEmail("test@test.bg");
        }};

        when(mockContactMapper.contactDtoToContactEntity(edited)).thenReturn(testTempContactEntity);
        toTest.editContact(edited.getId(), edited);
        Mockito.verify(mockContactRepository, times(1)).findById(testContactEntity.getId());
        Mockito.verify(mockContactRepository, times(1)).save(testTempContactEntity);
    }

    @Test
    void editContactTestWhenIsNotUpdated() {
        when(mockContactMapper.contactDtoToContactEntity(testContactDto)).thenReturn(testContactEntity);
        toTest.editContact(testContactDto.getId(), testContactDto);
        Mockito.verify(mockContactRepository, times(1)).findById(testContactEntity.getId());
        Mockito.verify(mockContactRepository, times(0)).save(testContactEntity);

    }

    @Test
    void editContactTestWhenContactIsNotFound() {

        Exception exception = assertThrows(ContactNotFoundException.class, () -> toTest.editContact(2L, testContactDto));
        String expectedMessage = "Contact with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Mockito.verify(mockContactRepository, times(1)).findById(2L);
        Mockito.verify(mockContactRepository, times(0)).save(testContactEntity);
    }

    @Test
    void initTestWhenRepoIsNotEmpty() {
        when(mockContactRepository.count()).thenReturn(1L);

        toTest.init();
        Mockito.verify(mockContactRepository, times(0)).save(any());

    }

    @Test
    void initTestWhenRepoEmpty() {
        when(mockContactRepository.count()).thenReturn(0L);

        toTest.init();
        Mockito.verify(mockContactRepository, times(3)).save(any());

    }
}
