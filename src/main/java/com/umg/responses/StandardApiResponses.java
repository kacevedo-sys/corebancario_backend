package com.umg.responses;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponses({
        @ApiResponse(
                responseCode = "401",
                description = ApiResponseMessages.UNAUTHORIZED,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CrudProblem.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = ApiResponseMessages.REGISTER_NOT_FOUND,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CrudProblem.class)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = ApiResponseMessages.INTERNAL_SERVER_ERROR,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CrudProblem.class)
                )
        )
})
public @interface StandardApiResponses {
}
