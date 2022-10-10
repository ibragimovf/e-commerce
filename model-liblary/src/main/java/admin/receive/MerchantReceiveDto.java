package admin.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantReceiveDto {
    private String name;
    @JsonProperty("gateway_merchant_id")
    private long gatewayMerchantId;
    @JsonProperty("agent_merchant_id")
    private long agentMerchantId;
}
