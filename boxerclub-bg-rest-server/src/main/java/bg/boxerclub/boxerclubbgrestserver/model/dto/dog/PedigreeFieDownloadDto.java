package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

public class PedigreeFieDownloadDto {

    private String contentType, name;
    private byte[] document;

    public PedigreeFieDownloadDto() {
    }

    public String getContentType() {
        return contentType;
    }

    public PedigreeFieDownloadDto setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getName() {
        return name;
    }

    public PedigreeFieDownloadDto setName(String name) {
        this.name = name;
        return this;
    }

    public byte[] getDocument() {
        return document;
    }

    public PedigreeFieDownloadDto setDocument(byte[] document) {
        this.document = document;
        return this;
    }
}
