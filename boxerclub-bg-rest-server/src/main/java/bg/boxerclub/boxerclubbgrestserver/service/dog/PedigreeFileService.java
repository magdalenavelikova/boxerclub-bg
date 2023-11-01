package bg.boxerclub.boxerclubbgrestserver.service.dog;

import bg.boxerclub.boxerclubbgrestserver.model.dto.pedigree.PedigreeFieDownloadDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.PedigreeFileEntity;
import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.PedigreeFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class PedigreeFileService {


    private final PedigreeFileRepository fileRepository;
    private final DogRepository dogRepository;

    public PedigreeFileService(PedigreeFileRepository fileRepository, DogRepository dogRepository) {
        this.fileRepository = fileRepository;
        this.dogRepository = dogRepository;
    }

    //todo handle exception for size
    public Long upload(MultipartFile file, Long id) throws IOException {
        PedigreeFileEntity newPedigree = new PedigreeFileEntity();
        newPedigree.setFileData(file.getBytes());
        newPedigree.setContentType(file.getContentType());
        newPedigree.setFileName(file.getOriginalFilename());
        Optional<DogEntity> dog = dogRepository.findById(id);
        dog.ifPresent(newPedigree::setDogEntity);
        return fileRepository.save(newPedigree).getId();
    }

    public void deleteByDogId(Long id) {
        fileRepository.deletePedigreeFileEntitiesByDogEntityId(id);
    }

    public boolean findPedigreeByDogId(Long dogId) {
        return fileRepository.findPedigreeFileEntityByDogEntityId(dogId).isPresent();

    }

    public PedigreeFieDownloadDto download(Long dogId) {
        PedigreeFileEntity file = fileRepository.
                findPedigreeFileEntityByDogEntityId(dogId).orElseThrow(() -> new IllegalArgumentException("No pedigree for dog with id: !" + dogId));

        return new PedigreeFieDownloadDto()
                .setContentType(file.getContentType())
                .setName(file.getFileName()).
                setDocument(file.getFileData());
    }


}
