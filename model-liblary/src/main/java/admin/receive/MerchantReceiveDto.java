package admin.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MerchantReceiveDto {

    @NotNull(message = "must not be empty")
    private String name;

    @JsonProperty("gateway_merchant_id")
    private long gatewayMerchantId;

    @JsonProperty("agent_merchant_id")
    private long agentMerchantId;
}
