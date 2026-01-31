package com.demo.MoneyMap.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for client response data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response payload containing client information")
public class ClientResponseDTO {

    @Schema(description = "Unique identifier of the client", example = "1")
    private Long id;

    @Schema(description = "Client's first name", example = "John")
    private String firstName;

    @Schema(description = "Client's last name", example = "Doe")
    private String lastName;

    @Schema(description = "Client's full name", example = "John Doe")
    private String fullName;

    @Schema(description = "Client's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Client's phone number", example = "+1-555-123-4567")
    private String phone;

    @Schema(description = "Client's address", example = "123 Main St, New York, NY 10001")
    private String address;

    @Schema(description = "Whether the client is active", example = "true")
    private Boolean active;

    @Schema(description = "Number of portfolios owned by the client", example = "3")
    private Integer portfolioCount;

    @Schema(description = "List of portfolio summaries for this client")
    private List<PortfolioSummaryDTO> portfolios;

    @Schema(description = "Timestamp when the client was created")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the client was last updated")
    private LocalDateTime updatedAt;
}
