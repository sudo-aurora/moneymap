package com.demo.MoneyMap.dto.response;

import com.demo.MoneyMap.beans.enums.AssetType;
import com.demo.MoneyMap.beans.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for asset response data.
 * Includes type-specific fields based on the asset type.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response payload containing asset information with type-specific details")
public class AssetResponseDTO {

    @Schema(description = "Unique identifier of the asset", example = "1")
    private Long id;

    @Schema(description = "Name of the asset", example = "Apple Inc.")
    private String name;

    @Schema(description = "Symbol/ticker of the asset", example = "AAPL")
    private String symbol;

    @Schema(description = "Type of the asset", example = "STOCK")
    private AssetType assetType;

    @Schema(description = "Display name of the asset type", example = "Stock")
    private String assetTypeDisplayName;

    @Schema(description = "Description of this asset type")
    private String typeDescription;

    @Schema(description = "Quantity of the asset held", example = "100.0")
    private BigDecimal quantity;

    @Schema(description = "Purchase price per unit", example = "150.00")
    private BigDecimal purchasePrice;

    @Schema(description = "Current market price per unit", example = "175.50")
    private BigDecimal currentPrice;

    @Schema(description = "Total current value of the asset", example = "17550.00")
    private BigDecimal currentValue;

    @Schema(description = "Date when the asset was purchased", example = "2024-01-15")
    private LocalDate purchaseDate;

    @Schema(description = "ID of the portfolio this asset belongs to", example = "1")
    private Long portfolioId;

    @Schema(description = "Name of the portfolio this asset belongs to", example = "Growth Portfolio")
    private String portfolioName;

    @Schema(description = "Profit or loss amount", example = "2550.00")
    private BigDecimal profitLoss;

    @Schema(description = "Profit or loss percentage", example = "17.00")
    private BigDecimal profitLossPercentage;

    @Schema(description = "Additional notes about the asset", example = "Long-term investment")
    private String notes;

    @Schema(description = "Transaction types allowed for this asset")
    private Set<TransactionType> allowedTransactionTypes;

    @Schema(description = "Minimum quantity increment for this asset type")
    private BigDecimal minimumQuantityIncrement;

    @Schema(description = "Timestamp when the asset was created")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the asset was last updated")
    private LocalDateTime updatedAt;

    // ============== STOCK-SPECIFIC FIELDS ==============

    @Schema(description = "[STOCK] Stock exchange", example = "NASDAQ")
    private String exchange;

    @Schema(description = "[STOCK] Sector of the company", example = "Technology")
    private String sector;

    @Schema(description = "[STOCK] Dividend yield percentage", example = "0.55")
    private BigDecimal dividendYield;

    @Schema(description = "[STOCK] Whether fractional shares are allowed", example = "true")
    private Boolean fractionalAllowed;

    // ============== CRYPTO-SPECIFIC FIELDS ==============

    @Schema(description = "[CRYPTO] Blockchain network", example = "Ethereum")
    private String blockchainNetwork;

    @Schema(description = "[CRYPTO] Wallet address")
    private String walletAddress;

    @Schema(description = "[CRYPTO] Whether staking is enabled", example = "true")
    private Boolean stakingEnabled;

    @Schema(description = "[CRYPTO] Annual staking yield percentage", example = "5.0")
    private BigDecimal stakingApy;

    // ============== GOLD-SPECIFIC FIELDS ==============

    @Schema(description = "[GOLD] Form of gold", example = "Physical")
    private String goldForm;

    @Schema(description = "[GOLD] Purity of gold", example = "24K")
    private String purity;

    @Schema(description = "[GOLD] Unit of measurement", example = "grams")
    private String weightUnit;

    @Schema(description = "[GOLD] Storage location", example = "Bank Locker")
    private String storageLocation;

    @Schema(description = "[GOLD] Whether this is physical gold", example = "true")
    private Boolean isPhysical;

    // ============== MUTUAL FUND-SPECIFIC FIELDS ==============

    @Schema(description = "[MUTUAL_FUND] Fund category", example = "Index Fund")
    private String fundCategory;

    @Schema(description = "[MUTUAL_FUND] Asset Management Company name", example = "Vanguard")
    private String amcName;

    @Schema(description = "[MUTUAL_FUND] Plan type", example = "GROWTH")
    private String planType;

    @Schema(description = "[MUTUAL_FUND] Annual expense ratio", example = "0.03")
    private BigDecimal expenseRatio;

    @Schema(description = "[MUTUAL_FUND] NAV date")
    private LocalDate navDate;

    @Schema(description = "[MUTUAL_FUND] Risk level", example = "Moderate")
    private String riskLevel;

    @Schema(description = "[MUTUAL_FUND] Minimum investment amount", example = "3000.00")
    private BigDecimal minInvestment;
}
