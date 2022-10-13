package uz.pdp.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.restservice.model.GatewayEntity;

import java.util.List;

public interface GatewayRepository extends JpaRepository<GatewayEntity, Long> {
    List<GatewayEntity> findAllByIdNot(long id);
}
