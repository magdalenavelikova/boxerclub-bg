package bg.boxerclub.boxerclubbgrestserver.service.dog;

import bg.boxerclub.boxerclubbgrestserver.model.dto.pedigree.PedigreeFieDownloadDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PedigreeFileService {
    Long upload(MultipartFile file, Long id) throws IOException;

    void deleteByDogId(Long id);

    boolean findPedigreeByDogId(Long dogId);

    PedigreeFieDownloadDto download(Long dogId);
}
