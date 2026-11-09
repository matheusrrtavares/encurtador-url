package matheusrrt.encurtadorurl.event;

import matheusrrt.encurtadorurl.dto.ClickEvent;
import matheusrrt.encurtadorurl.entities.ClickLogsEntity;
import matheusrrt.encurtadorurl.repository.ClickLogsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ClickListener {

    private static final Logger log = LoggerFactory.getLogger(ClickListener.class);
    private final ClickLogsRepository clickLogsRepository;

    public ClickListener(ClickLogsRepository clickLogsRepository) {
        this.clickLogsRepository = clickLogsRepository;
    }

    @Async(value = "clickExecutor")
    @EventListener
    public void handle(ClickEvent event) {
        log.info("Processando clique assíncrono para urlId: {}", event.urlId());

        try {
            String browser = detectBrowser(event.userAgent());
            String operationalSystem = detectOperationalSystem(event.userAgent());
            String device = event.userAgent() != null &&
                    event.userAgent().toLowerCase().contains("mobile") ? "Mobile" : "Desktop";

            ClickLogsEntity logs = new ClickLogsEntity(
                    event.urlId(),
                    event.clickedAt(),
                    event.referrer(),
                    device,
                    browser,
                    operationalSystem);

            ClickLogsEntity saved = clickLogsRepository.save(logs);
            log.info("Log de clique salvo com sucesso no MongoDB! ID: {}, Collection: click_logs", saved.getId());
        } catch (Exception e) {
            log.error("Erro ao salvar log de clique no MongoDB para urlId: {}", event.urlId(), e);
        }
    }

    private String detectBrowser(String br) {
        if (br == null) return "Unknown";
        if (br.contains("Edg")) return "Edge";
        if (br.contains("Chrome")) return "Chrome";
        if (br.contains("Firefox")) return "Firefox";
        if (br.contains("Safari")) return "Safari";
        return "Other";
    }

    private String detectOperationalSystem(String op) {
        if (op == null) return "Unknown";
        String lower = op.toLowerCase();
        if (lower.contains("windows")) return "Windows";
        if (lower.contains("mac") || lower.contains("darwin")) return "MacOS";
        if (lower.contains("android")) return "Android";
        if (lower.contains("linux")) return "Linux";
        if (lower.contains("iphone") || lower.contains("ipad")) return "iOS";
        return "Other";
    }

}
