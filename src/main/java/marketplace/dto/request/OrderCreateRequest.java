package marketplace.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderCreateRequest(

        @NotEmpty
        List<OrderProductRequest> products
) {
}
