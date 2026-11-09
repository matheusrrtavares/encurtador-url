package matheusrrt.encurtadorurl.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(value = "click_logs")
public class ClickLogsEntity {

    @Id
    private String id;

    @Indexed
    private String urlId;

    private Instant clickedAt;

    private String referrer;

    private String deviceType;

    private String browser;

    private String operationalSystem;

    public ClickLogsEntity() {
    }

    public ClickLogsEntity(
            String urlId, Instant clickedAt,
            String referrer, String deviceType, String browser,
            String operationalSystem) {
        this.urlId = urlId;
        this.clickedAt = clickedAt;
        this.referrer = referrer;
        this.deviceType = deviceType;
        this.browser = browser;
        this.operationalSystem = operationalSystem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public Instant getClickedAt() {
        return clickedAt;
    }

    public void setClickedAt(Instant clickedAt) {
        this.clickedAt = clickedAt;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOperationalSystem() {
        return operationalSystem;
    }

    public void setOperationalSystem(String operationalSystem) {
        this.operationalSystem = operationalSystem;
    }

}
