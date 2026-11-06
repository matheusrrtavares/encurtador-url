package matheusrrt.encurtadorurl.dto;

import java.time.Instant;

public record ClickEventRequest(
        String urlId,
        Instant clickedAt,
        String ipAddres,
        String userAgent,
        String referrer
) {}
