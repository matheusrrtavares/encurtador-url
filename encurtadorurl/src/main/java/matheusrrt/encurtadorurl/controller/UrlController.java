package matheusrrt.encurtadorurl.controller;

import jakarta.servlet.http.HttpServletRequest;
import matheusrrt.encurtadorurl.dto.ClickEvent;
import matheusrrt.encurtadorurl.dto.ShortenUrlRequest;
import matheusrrt.encurtadorurl.dto.ShortenUrlResponse;
import matheusrrt.encurtadorurl.entities.UrlEntity;
import matheusrrt.encurtadorurl.repository.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
public class UrlController {

    private static final Logger log = LoggerFactory.getLogger(UrlController.class);

    private final UrlRepository urlRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UrlController(UrlRepository urlRepository, ApplicationEventPublisher eventPublisher) {
        this.urlRepository = urlRepository;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping(value = "/shorten-url")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest request,
                                                         HttpServletRequest serveletRequest) {

        String id = "";
        do {
            id = RandomStringUtils.randomAlphanumeric(5, 10);
        } while (urlRepository.existsById(id));

        urlRepository.save(new UrlEntity(id, request.url(), Instant.now().plus(5, ChronoUnit.MINUTES)));

        var redirectUrl = serveletRequest.getRequestURL().toString().replace("shorten-url", id);

        return ResponseEntity.ok(new ShortenUrlResponse(redirectUrl));
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> redirect(@PathVariable("id") String id, HttpServletRequest request) {

        var url = urlRepository.findById(id);

        if (url.isEmpty()) {
            log.warn("Tentativa de acesso a URL inexistente ou expirada com id: {}", id);
            return ResponseEntity.notFound().build();
        }

        log.info("Redirecionando id: {} e publicando evento de clique", id);
        eventPublisher.publishEvent(new ClickEvent(
                id,
                Instant.now(),
                request.getRemoteAddr(),
                request.getHeader("User-Agent"),
                request.getHeader("Referer")
        ));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(url.get().getFullUrl()));

        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).build();
    }

}
