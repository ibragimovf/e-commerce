package uz.pdp.restservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "db.agent")
public class AgentEntity extends BaseModelEntity {

    @Column(unique = true)
    private String secretKey;

    @JsonIgnore
    @Column
    @OneToMany(mappedBy = "agentEntity")
    private List<AgentMerchantEntity> agentMerchantEntities;
}
