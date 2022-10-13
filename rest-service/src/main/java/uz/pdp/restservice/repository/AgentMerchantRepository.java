package uz.pdp.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.restservice.model.AgentMerchantEntity;

import java.util.List;

public interface AgentMerchantRepository extends JpaRepository<AgentMerchantEntity, Long> {
    List<AgentMerchantEntity> findAllByAgentEntityId(long agentEntity_id);
    List<AgentMerchantEntity> findAllByIdNot(long id);
}
