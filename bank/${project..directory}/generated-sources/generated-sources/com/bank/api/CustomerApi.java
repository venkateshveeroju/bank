/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.31).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.bank.api;

import com.bank.model.AccountNotFoundError;
import java.math.BigDecimal;
import com.bank.model.CustomerInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@Validated
public interface CustomerApi {

    @Operation(summary = "Retrieves Person information", description = "Retrieves Person information using customer id.", tags={ "Customer" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Person Infromation Retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerInfo.class))),
        
        @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountNotFoundError.class))) })
    @RequestMapping(value = "/customer/{customerId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<CustomerInfo> customerId(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("customerId") BigDecimal customerId);

}

