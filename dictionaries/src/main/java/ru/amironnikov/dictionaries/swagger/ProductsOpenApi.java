package ru.amironnikov.dictionaries.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.amironnikov.dictionaries.dto.ProductsDto;

@Tag(name = "API dictionaries: products")
public interface ProductsOpenApi {
    @Operation(summary = "GET Products list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error")
    })
    ProductsDto getProducts();
}
