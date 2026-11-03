package matheusrrt.encurtadorurl.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;

@Document(collection = "urls")
public class UrlEntity {

    @Id
    private String id;

    private String fullUrl;

    @Indexed(expireAfter = "0s")
    private Instant expirationTime;

    public UrlEntity() {
    }

    public UrlEntity(String id, String fullUrl, Instant expirationTime) {
        this.id = id;
        this.fullUrl = fullUrl;
        this.expirationTime = expirationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Instant expirationTime) {
        this.expirationTime = expirationTime;
    }
}