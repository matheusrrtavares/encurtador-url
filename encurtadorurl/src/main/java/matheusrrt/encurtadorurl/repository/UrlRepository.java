package matheusrrt.encurtadorurl.repository;

import matheusrrt.encurtadorurl.entities.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {



}
