package uz.pdp.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.restservice.model.GatewayEntity;

public interface GatewayRepository extends JpaRepository<GatewayEntity, Long> {
}
