package admin.receive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentMerchantReceiveDto {
    private String name;
    @JsonProperty("min_sum")
    private double minSum;
    @JsonProperty("max_sum")
    private double maxSum;
    private double commission;

    private long agentId;
    private long merchantId;
}
