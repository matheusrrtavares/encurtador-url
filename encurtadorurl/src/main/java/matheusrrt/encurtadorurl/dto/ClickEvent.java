package matheusrrt.encurtadorurl.dto;

import java.time.Instant;

public record ClickEvent(
        String urlId,
        Instant clickedAt,
        String ipAddres,
        String userAgent,
        String referrer
) {}
