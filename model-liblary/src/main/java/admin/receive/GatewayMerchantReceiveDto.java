package admin.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GatewayMerchantReceiveDto {

    @NotNull(message = "must not be empty")
    private String name;

    @NotNull(message = "gateway must be selected")
    @JsonProperty("gateway_id")
    private long gatewayId;
}
