package uz.pdp.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.restservice.model.AgentEntity;

public interface AgentRepository extends JpaRepository<AgentEntity, Long> {

    AgentEntity findBySecretKey(String secretKey);
}
