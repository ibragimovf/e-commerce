package uz.pdp.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.restservice.model.MerchantEntity;

public interface MerchantRepository extends JpaRepository<MerchantEntity,Long> {
}
