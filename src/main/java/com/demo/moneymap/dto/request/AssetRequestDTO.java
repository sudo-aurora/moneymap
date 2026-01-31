package com.demo.MoneyMap.dto.request;

import com.demo.MoneyMap.entity.enums.AssetType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for creating or updating an asset.
 * Includes type-specific optional fields for different asset types.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for creating or updating an asset. " +
        "Include type-specific fields based on the assetType selected.")
public class AssetRequestDTO {

    @NotBlank(message = "Asset name is required")
    @Size(max = 100, message = "Asset name cannot exceed 100 characters")
    @Schema(description = "Name of the asset", example = "Apple Inc.", required = true)
    private String name;

    @Size(max = 50, message = "Symbol cannot exceed 50 characters")
    @Schema(description = "Symbol/ticker of the asset", example = "AAPL")
    private String symbol;

    @NotNull(message = "Asset type is required")
    @Schema(description = "Type of the asset: STOCK, CRYPTO, GOLD, or MUTUAL_FUND", example = "STOCK", required = true)
    private AssetType assetType;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.00000001", message = "Quantity must be greater than 0")
    @Schema(description = "Quantity of the asset", example = "100.0", required = true)
    private BigDecimal quantity;

    @NotNull(message = "Purchase price is required")
    @DecimalMin(value = "0.0001", message = "Purchase price must be greater than 0")
    @Schema(description = "Purchase price per unit", example = "150.00", required = true)
    private BigDecimal purchasePrice;

    @DecimalMin(value = "0.0001", message = "Current price must be greater than 0")
    @Schema(description = "Current market price per unit", example = "175.50")
    private BigDecimal currentPrice;

    @PastOrPresent(message = "Purchase date cannot be in the future")
    @Schema(description = "Date when the asset was purchased", example = "2024-01-15")
    private LocalDate purchaseDate;

    @NotNull(message = "Portfolio ID is required")
    @Schema(description = "ID of the portfolio this asset belongs to", example = "1", required = true)
    private Long portfolioId;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    @Schema(description = "Additional notes about the asset", example = "Long-term investment")
    private String notes;

    // ============== STOCK-SPECIFIC FIELDS ==============

    @Size(max = 20, message = "Exchange cannot exceed 20 characters")
    @Schema(description = "[STOCK] Stock exchange (e.g., NYSE, NASDAQ)", example = "NASDAQ")
    private String exchange;

    @Size(max = 50, message = "Sector cannot exceed 50 characters")
    @Schema(description = "[STOCK] Sector of the company", example = "Technology")
    private String sector;

    @Schema(description = "[STOCK] Dividend yield percentage", example = "0.55")
    private BigDecimal dividendYield;

    @Schema(description = "[STOCK] Whether fractional shares are allowed", example = "true")
    private Boolean fractionalAllowed;

    // ============== CRYPTO-SPECIFIC FIELDS ==============

    @Size(max = 50, message = "Blockchain network cannot exceed 50 characters")
    @Schema(description = "[CRYPTO] Blockchain network", example = "Ethereum")
    private String blockchainNetwork;

    @Size(max = 200, message = "Wallet address cannot exceed 200 characters")
    @Schema(description = "[CRYPTO] Wallet address", example = "0x742d35Cc6634C0532925a3b844Bc9e7595f...")
    private String walletAddress;

    @Schema(description = "[CRYPTO] Whether staking is enabled", example = "true")
    private Boolean stakingEnabled;

    @Schema(description = "[CRYPTO] Annual staking yield percentage", example = "5.0")
    private BigDecimal stakingApy;

    // ============== GOLD-SPECIFIC FIELDS ==============

    @Size(max = 50, message = "Gold form cannot exceed 50 characters")
    @Schema(description = "[GOLD] Form of gold (Physical, ETF, Digital Gold)", example = "Physical")
    private String goldForm;

    @Size(max = 20, message = "Purity cannot exceed 20 characters")
    @Schema(description = "[GOLD] Purity of gold (e.g., 24K, 22K)", example = "24K")
    private String purity;

    @Size(max = 20, message = "Weight unit cannot exceed 20 characters")
    @Schema(description = "[GOLD] Unit of measurement (grams, ounces)", example = "grams")
    private String weightUnit;

    @Size(max = 100, message = "Storage location cannot exceed 100 characters")
    @Schema(description = "[GOLD] Storage location for physical gold", example = "Bank Locker")
    private String storageLocation;

    @Schema(description = "[GOLD] Whether this is physical gold", example = "true")
    private Boolean isPhysical;

    // ============== MUTUAL FUND-SPECIFIC FIELDS ==============

    @Size(max = 50, message = "Fund category cannot exceed 50 characters")
    @Schema(description = "[MUTUAL_FUND] Fund category (Large Cap, Index Fund, etc.)", example = "Index Fund")
    private String fundCategory;

    @Size(max = 100, message = "AMC name cannot exceed 100 characters")
    @Schema(description = "[MUTUAL_FUND] Asset Management Company name", example = "Vanguard")
    private String amcName;

    @Size(max = 20, message = "Plan type cannot exceed 20 characters")
    @Schema(description = "[MUTUAL_FUND] Plan type: GROWTH or DIVIDEND", example = "GROWTH")
    private String planType;

    @Schema(description = "[MUTUAL_FUND] Annual expense ratio percentage", example = "0.03")
    private BigDecimal expenseRatio;

    @Schema(description = "[MUTUAL_FUND] NAV date", example = "2024-01-15")
    private LocalDate navDate;

    @Size(max = 20, message = "Risk level cannot exceed 20 characters")
    @Schema(description = "[MUTUAL_FUND] Risk level (Low, Moderate, High)", example = "Moderate")
    private String riskLevel;

    @Schema(description = "[MUTUAL_FUND] Minimum investment amount", example = "3000.00")
    private BigDecimal minInvestment;
}
