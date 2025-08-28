package ru.amironnikov.image.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import ru.amironnikov.image.common.StatusResponse;

import java.io.IOException;
import java.util.UUID;

@Tag(name = "API images", description = "Product images API")
public interface OpenApi {

    @Operation(
            summary = "Load image",
            description = "Load image to server for product with ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Image load success"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    StatusResponse loadImage(
            UUID id,
            byte[] imageBytes) throws IOException;


    @Operation(
            summary = "Get image",
            description = "Get image from server for product ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Image get success"),
            @ApiResponse(responseCode = "404", description = "Image not found")
    })
    byte[] getImage(@PathVariable UUID id);
}
