package admin.receive;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GatewayReceiveDto {

    @NotNull(message = "must not be empty")
    private String secretKey;

    @NotNull(message = "must not be empty")
    private String name;
}
