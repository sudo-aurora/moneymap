package com.demo.MoneyMap.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO for creating or updating a client.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for creating or updating a client")
public class ClientRequestDTO {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name cannot exceed 100 characters")
    @Schema(description = "Client's first name", example = "John", required = true)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    @Schema(description = "Client's last name", example = "Doe", required = true)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 150, message = "Email cannot exceed 150 characters")
    @Schema(description = "Client's email address", example = "john.doe@example.com", required = true)
    private String email;

    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    @Schema(description = "Client's phone number", example = "+1-555-123-4567")
    private String phone;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    @Schema(description = "Client's address", example = "123 Main St, New York, NY 10001")
    private String address;
}
