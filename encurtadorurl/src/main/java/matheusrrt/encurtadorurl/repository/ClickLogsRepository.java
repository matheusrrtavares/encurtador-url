package matheusrrt.encurtadorurl.repository;

import matheusrrt.encurtadorurl.entities.ClickLogsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClickLogsRepository extends MongoRepository<ClickLogsEntity, String> {
}
