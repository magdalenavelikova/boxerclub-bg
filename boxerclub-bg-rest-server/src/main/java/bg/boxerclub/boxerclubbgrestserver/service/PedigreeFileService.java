package bg.boxerclub.boxerclubbgrestserver.service;

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

    //todo handle exception
    public Long upload(MultipartFile file, Long id) throws IOException {
        PedigreeFileEntity newPedigree = new PedigreeFileEntity();
        newPedigree.setFileData(file.getBytes());
        newPedigree.setContentType(file.getContentType());
        newPedigree.setFileName(file.getOriginalFilename());
//        String id = dto.split(":")[1].replace("}"
//                , "").replace("\""
//                , "");
        Optional<DogEntity> dog = dogRepository.findById(id);
        dog.ifPresent(newPedigree::setDogEntity);
        return fileRepository.save(newPedigree).getId();
    }

    public void deleteByDogId(Long id) {
        fileRepository.deletePedigreeFileEntitiesByDogEntityId(id);
    }

  /*  public PedigreeFieDownloadDto download(int fileId) {
        PedigreeFileEntity file = fileRepository.findById(fileId).orElseThrow(() -> new IllegalArgumentException("File" + fileId + " not found!"));

        return new FileDownloadModel()
                .setContentType(file.getContentType())
                .setName(file.getFileName()).
                setDocument(file.getFileData());
    }

*/
}
