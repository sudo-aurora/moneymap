package com.demo.MoneyMap.beans;

import com.demo.MoneyMap.beans.enums.AssetType;
import com.demo.MoneyMap.beans.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Set;

/**
 * Entity representing a Stock (equity) asset.
 * Stocks are equity shares in publicly traded companies.
 * 
 * Stock-specific characteristics:
 * - Can receive DIVIDENDS
 * - Traded on stock exchanges
 * - Typically have ticker symbols (e.g., AAPL, MSFT, GOOGL)
 * - Quantity usually in whole shares (some brokers allow fractional)
 */
@Entity
@DiscriminatorValue("STOCK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StockAsset extends Asset {

    /**
     * Stock exchange where this stock is traded.
     * e.g., NYSE, NASDAQ, LSE, NSE, BSE
     */
    @Column(name = "exchange", length = 20)
    private String exchange;

    /**
     * Sector the company belongs to.
     * e.g., Technology, Healthcare, Finance
     */
    @Column(name = "sector", length = 50)
    private String sector;

    /**
     * Dividend yield percentage (if applicable).
     */
    @Column(name = "dividend_yield", precision = 10, scale = 4)
    private BigDecimal dividendYield;

    /**
     * Whether fractional shares are allowed for this stock.
     */
    @Column(name = "fractional_allowed")
    @Builder.Default
    private Boolean fractionalAllowed = true;

    // ============== POLYMORPHIC METHODS ==============

    @Override
    public AssetType getType() {
        return AssetType.STOCK;
    }

    /**
     * Stocks allow: BUY, SELL, DIVIDEND, TRANSFER_IN, TRANSFER_OUT
     * DIVIDEND is specific to stocks and mutual funds.
     */
    @Override
    public Set<TransactionType> getAllowedTransactionTypes() {
        return EnumSet.of(
            TransactionType.BUY,
            TransactionType.SELL,
            TransactionType.DIVIDEND,
            TransactionType.TRANSFER_IN,
            TransactionType.TRANSFER_OUT
        );
    }

    /**
     * Validate quantity - stocks can have fractional shares if allowed.
     */
    @Override
    public boolean isQuantityValid(BigDecimal qty) {
        if (!super.isQuantityValid(qty)) {
            return false;
        }
        // If fractional not allowed, check for whole numbers
        if (Boolean.FALSE.equals(fractionalAllowed)) {
            return qty.stripTrailingZeros().scale() <= 0;
        }
        return true;
    }

    @Override
    public BigDecimal getMinimumQuantityIncrement() {
        // Stocks typically allow 2 decimal places for fractional shares
        return Boolean.TRUE.equals(fractionalAllowed) 
            ? new BigDecimal("0.01") 
            : BigDecimal.ONE;
    }

    @Override
    public String getTypeDescription() {
        return "Equity shares in publicly traded companies. " +
               "Can receive dividends and are traded on stock exchanges.";
    }
}
