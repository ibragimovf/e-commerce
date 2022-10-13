package uz.pdp.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.restservice.model.GatewayMerchantEntity;

import java.util.List;

public interface GatewayMerchantRepository extends JpaRepository<GatewayMerchantEntity, Long> {
    List<GatewayMerchantEntity> findAllByGatewayEntityId(long gatewayEntity_id);

    List<GatewayMerchantEntity> findAllByIdNot(long id);
}
