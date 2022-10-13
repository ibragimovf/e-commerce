package uz.pdp.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.restservice.model.AgentEntity;

import java.util.List;

public interface AgentRepository extends JpaRepository<AgentEntity, Long> {

    AgentEntity findBySecretKey(String secretKey);
    List<AgentEntity> findAllByIdNot(long id);
}
