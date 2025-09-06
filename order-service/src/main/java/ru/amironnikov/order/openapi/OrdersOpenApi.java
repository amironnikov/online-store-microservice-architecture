package ru.amironnikov.order.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.amironnikov.order.dto.CreatedEntityResponse;
import ru.amironnikov.order.dto.OrderDto;
import ru.amironnikov.order.dto.OrderListDto;
import ru.amironnikov.order.dto.StatusResponse;

import java.util.UUID;

@Tag(name = "API orders", description = "Order-service API")
public interface OrdersOpenApi {

    @Operation(
            summary = "Create order",
            description = "Create order REST endpoint")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create order success"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "429", description = "Too many requests")
    })
    Mono<CreatedEntityResponse> create(@RequestBody OrderDto order);


    @Operation(
            summary = "Get all orders for user",
            description = "Get all orders for user by user-id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success return list"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "429", description = "Too many requests")
    })
    Flux<OrderListDto> getAll(@RequestParam UUID userId);

    @Operation(
            summary = "Cancel order",
            description = "Cancel order by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cancel success"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "429", description = "Too many requests")
    })
    Mono<StatusResponse> cancel(@PathVariable UUID id);
}
