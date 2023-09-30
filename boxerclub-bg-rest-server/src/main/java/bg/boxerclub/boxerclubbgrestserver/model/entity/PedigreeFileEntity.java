package bg.boxerclub.boxerclubbgrestserver.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pedigree")
public class PedigreeFileEntity {
    @Id //ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = Integer.MAX_VALUE)
    private byte[] fileData;

    private String contentType;
    @OneToOne(optional = false)
    private DogEntity dogEntity;

    public PedigreeFileEntity() {
    }

    public Long getId() {
        return id;
    }

    public PedigreeFileEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public PedigreeFileEntity setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public PedigreeFileEntity setFileData(byte[] fileData) {
        this.fileData = fileData;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public PedigreeFileEntity setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public DogEntity getDogEntity() {
        return dogEntity;
    }

    public PedigreeFileEntity setDogEntity(DogEntity dogEntity) {
        this.dogEntity = dogEntity;
        return this;
    }
}
