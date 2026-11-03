package matheusrrt.encurtadorurl.controller;

import jakarta.servlet.http.HttpServletRequest;
import matheusrrt.encurtadorurl.dto.ShortenUrlRequest;
import matheusrrt.encurtadorurl.dto.ShortenUrlResponse;
import matheusrrt.encurtadorurl.entities.UrlEntity;
import matheusrrt.encurtadorurl.repository.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.PrivilegedAction;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@RestController
public class UrlController {

    private final UrlRepository urlRepository;

    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }


    @PostMapping(value = "/shorten-url")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest request,
                                           HttpServletRequest serveletRequest) {

        String id = "";
        do {
            id = RandomStringUtils.randomAlphanumeric(5, 10);
        } while (urlRepository.existsById(id));

        urlRepository.save(new UrlEntity(id, request.url(), Instant.now().plus(1, ChronoUnit.MINUTES)));

        var redirectUrl = serveletRequest.getRequestURL().toString().replace("shorten-url", id);

        return ResponseEntity.ok(new ShortenUrlResponse(redirectUrl));
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> redirect (@PathVariable("id") String id){

        var url = urlRepository.findById(id);

        if(url.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(url.get().getFullUrl()));

        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).build();

    }


}
