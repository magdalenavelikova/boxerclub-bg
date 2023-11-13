package bg.boxerclub.boxerclubbgrestserver.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "blackList_ip")
public class BlackListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true)
    private String ip;

    public BlackListEntity() {
    }

    public BlackListEntity(String ip) {

        this.ip = ip;
    }

    public Long getId() {
        return Id;
    }

    public BlackListEntity setId(Long id) {
        Id = id;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public BlackListEntity setIp(String ip) {
        this.ip = ip;
        return this;
    }
}
