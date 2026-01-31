package com.demo.MoneyMap.entity;

import com.demo.MoneyMap.entity.enums.AssetType;
import com.demo.MoneyMap.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

/**
 * Entity representing a Mutual Fund asset.
 * Mutual funds are professionally managed investment pools.
 * 
 * Mutual Fund-specific characteristics:
 * - Has NAV (Net Asset Value) which determines unit price
 * - Can be equity funds, debt funds, hybrid, etc.
 * - Can pay dividends (dividend option) or reinvest (growth option)
 * - Managed by AMC (Asset Management Company)
 * - Has expense ratio
 */
@Entity
@DiscriminatorValue("MUTUAL_FUND")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MutualFundAsset extends Asset {

    /**
     * Fund category.
     * e.g., Large Cap, Mid Cap, Small Cap, Index Fund, Debt Fund, Hybrid
     */
    @Column(name = "fund_category", length = 50)
    private String fundCategory;

    /**
     * Asset Management Company (AMC) managing the fund.
     * e.g., Vanguard, BlackRock, Fidelity, HDFC AMC
     */
    @Column(name = "amc_name", length = 100)
    private String amcName;

    /**
     * Fund plan type.
     * GROWTH: Returns reinvested, DIVIDEND: Periodic payouts
     */
    @Column(name = "plan_type", length = 20)
    @Builder.Default
    private String planType = "GROWTH";

    /**
     * Annual expense ratio (percentage).
     */
    @Column(name = "expense_ratio", precision = 6, scale = 4)
    private BigDecimal expenseRatio;

    /**
     * NAV (Net Asset Value) date - when the NAV was last updated.
     */
    @Column(name = "nav_date")
    private LocalDate navDate;

    /**
     * Risk level of the fund.
     * e.g., Low, Moderate, High, Very High
     */
    @Column(name = "risk_level", length = 20)
    private String riskLevel;

    /**
     * Minimum investment amount for this fund.
     */
    @Column(name = "min_investment", precision = 19, scale = 4)
    private BigDecimal minInvestment;

    // ============== POLYMORPHIC METHODS ==============

    @Override
    public AssetType getType() {
        return AssetType.MUTUAL_FUND;
    }

    /**
     * Mutual funds allow: BUY, SELL, DIVIDEND (for dividend plans), TRANSFER_IN, TRANSFER_OUT
     * DIVIDEND only applies to dividend option funds.
     */
    @Override
    public Set<TransactionType> getAllowedTransactionTypes() {
        // If it's a dividend plan, allow DIVIDEND transactions
        if ("DIVIDEND".equalsIgnoreCase(planType)) {
            return EnumSet.of(
                TransactionType.BUY,
                TransactionType.SELL,
                TransactionType.DIVIDEND,
                TransactionType.TRANSFER_IN,
                TransactionType.TRANSFER_OUT
            );
        }
        // Growth plans don't receive dividends
        return EnumSet.of(
            TransactionType.BUY,
            TransactionType.SELL,
            TransactionType.TRANSFER_IN,
            TransactionType.TRANSFER_OUT
        );
    }

    /**
     * Mutual funds are measured in units with high precision (up to 4 decimals).
     */
    @Override
    public boolean isQuantityValid(BigDecimal qty) {
        if (!super.isQuantityValid(qty)) {
            return false;
        }
        // Mutual fund units can have up to 4 decimal places
        return qty.stripTrailingZeros().scale() <= 4;
    }

    @Override
    public BigDecimal getMinimumQuantityIncrement() {
        // Mutual funds typically allow 4 decimal places for units
        return new BigDecimal("0.0001");
    }

    @Override
    public String getTypeDescription() {
        return "Professionally managed investment pool. " +
               "Invests in stocks, bonds, or other assets based on fund objective.";
    }

    /**
     * Check if this is a dividend-paying fund.
     */
    public boolean isDividendPlan() {
        return "DIVIDEND".equalsIgnoreCase(planType);
    }
}
