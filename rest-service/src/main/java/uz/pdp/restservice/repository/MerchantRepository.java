package uz.pdp.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.restservice.model.MerchantEntity;

import java.util.List;

public interface MerchantRepository extends JpaRepository<MerchantEntity,Long> {
    List<MerchantEntity> findAllByIdNot(long id);
}
