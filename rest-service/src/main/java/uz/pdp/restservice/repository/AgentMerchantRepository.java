package uz.pdp.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.restservice.model.AgentMerchantEntity;

public interface AgentMerchantRepository extends JpaRepository<AgentMerchantEntity, Long> {
}
