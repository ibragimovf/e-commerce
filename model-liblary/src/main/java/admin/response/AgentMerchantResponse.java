package admin.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentMerchantResponse {

    private long id;
    private String name;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;

}
