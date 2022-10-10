package uz.pdp.restservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "db.gateway")
public class GatewayEntity extends BaseModelEntity {

    @OneToMany(mappedBy = "gatewayEntity")
    private List<GatewayMerchantEntity> gatewayMerchantEntities;

    public boolean isPaynet(){
        return getId() == 10;
    }

    public boolean isPayme(){
        return getId() == 20;
    }

}
