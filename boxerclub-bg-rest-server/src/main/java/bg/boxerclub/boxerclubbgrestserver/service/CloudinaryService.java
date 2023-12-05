package bg.boxerclub.boxerclubbgrestserver.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    String uploadImage(MultipartFile multipartFile) throws IOException;


    void deleteImage(String url) throws IOException;
}
