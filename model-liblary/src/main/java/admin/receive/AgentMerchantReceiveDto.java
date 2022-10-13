package admin.receive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentMerchantReceiveDto {

    @NotNull(message = "must not be empty")
    private String name;

    @NotNull(message = "must not be empty")
    @JsonProperty("min_sum")
    private double minSum;

    @NotNull(message = "must not be empty")
    @JsonProperty("max_sum")
    private double maxSum;

    @NotNull(message = "must not be empty")
    private double commission;

    @NotNull(message = "must not be empty")
    private long agentId;


}
