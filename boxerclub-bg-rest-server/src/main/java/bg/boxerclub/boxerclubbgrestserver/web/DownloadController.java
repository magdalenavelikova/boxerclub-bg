package bg.boxerclub.boxerclubbgrestserver.web;


import bg.boxerclub.boxerclubbgrestserver.service.PedigreeFileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedigree")
public class DownloadController {

    private final PedigreeFileService fileService;

    public DownloadController(PedigreeFileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable("id") long fileId) {

        var fileDownloadModel = fileService.download(fileId);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(fileDownloadModel.getContentType()));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileDownloadModel.getName());
        header.setContentLength(fileDownloadModel.getDocument().length);

        return new ResponseEntity<>(fileDownloadModel.getDocument(), header, HttpStatus.OK);
    }


}


