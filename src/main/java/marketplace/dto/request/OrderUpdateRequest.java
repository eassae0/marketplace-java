package marketplace.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderUpdateRequest(

        @NotEmpty
        List<OrderProductRequest> products
) {
}
