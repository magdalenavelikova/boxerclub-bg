package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

import org.springframework.web.multipart.MultipartFile;

public class PedigreeFileUploadDto {
    private MultipartFile file;
    private String dogId;

    public PedigreeFileUploadDto() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public PedigreeFileUploadDto setFile(MultipartFile file) {
        this.file = file;
        return this;
    }

    public String getDogId() {
        return dogId;
    }

    public PedigreeFileUploadDto setDogId(String dogId) {
        this.dogId = dogId;
        return this;
    }
}
