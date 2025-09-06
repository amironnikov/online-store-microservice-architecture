package ru.amironnikov.order.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import ru.amironnikov.order.dto.ProductDto;

@Tag(name = "API products", description = "Order-service products API")
public interface ProductsOpenApi {

    @Operation(
            summary = "Products list",
            description = "Products list fro orders")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "429", description = "Too many requests"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Flux<ProductDto> getAllProducts();
}
