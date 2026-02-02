package com.demo.MoneyMap.beans;

import com.demo.MoneyMap.entity.enums.AssetType;
import com.demo.MoneyMap.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Abstract base entity representing an investment asset within a portfolio.
 * Uses Single Table Inheritance (STI) pattern for different asset types.
 * 
 * Subclasses:
 * - StockAsset: Equity shares in publicly traded companies
 * - CryptoAsset: Digital/virtual currencies
 * - GoldAsset: Physical gold or gold-related investments
 * - MutualFundAsset: Professionally managed investment funds
 * 
 * This design demonstrates:
 * - Inheritance and Polymorphism (OOP concepts)
 * - Single Table Inheritance (JPA pattern)
 * - Template Method pattern (via abstract methods)
 */
@Entity
@Table(name = "assets")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "asset_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String symbol;  // e.g., AAPL, BTC, GLD

    // Note: asset_type is the discriminator column, mapped via @DiscriminatorColumn
    // We still keep this for querying and display purposes
    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type", insertable = false, updatable = false)
    private AssetType assetType;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    @Column(name = "purchase_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal purchasePrice;

    @Column(name = "current_price", precision = 19, scale = 4)
    private BigDecimal currentPrice;

    @Column(name = "current_value", precision = 19, scale = 4)
    private BigDecimal currentValue;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();

    @Column(length = 500)
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Calculate and return the current value of the asset.
     */
    @PrePersist
    @PreUpdate
    public void calculateCurrentValue() {
        if (quantity != null && currentPrice != null) {
            this.currentValue = quantity.multiply(currentPrice);
        } else if (quantity != null && purchasePrice != null) {
            this.currentValue = quantity.multiply(purchasePrice);
        }
    }

    /**
     * Calculate the profit/loss for this asset.
     */
    public BigDecimal getProfitLoss() {
        if (currentValue == null || purchasePrice == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal costBasis = purchasePrice.multiply(quantity);
        return currentValue.subtract(costBasis);
    }

    /**
     * Calculate the profit/loss percentage for this asset.
     */
    public BigDecimal getProfitLossPercentage() {
        if (purchasePrice == null || purchasePrice.compareTo(BigDecimal.ZERO) == 0 || quantity == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal costBasis = purchasePrice.multiply(quantity);
        if (costBasis.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return getProfitLoss().divide(costBasis, 4, java.math.RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
    }

    /**
     * Utility method to add a transaction to the asset.
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setAsset(this);
    }

    // ============== ABSTRACT METHODS (Polymorphism) ==============

    /**
     * Get the asset type for this specific asset.
     * Implemented by each subclass to return its type.
     */
    public abstract AssetType getType();

    /**
     * Get the set of allowed transaction types for this asset.
     * Each asset type can have different allowed transactions.
     */
    public abstract Set<TransactionType> getAllowedTransactionTypes();

    /**
     * Check if a specific transaction type is allowed for this asset.
     */
    public boolean isTransactionTypeAllowed(TransactionType transactionType) {
        return getAllowedTransactionTypes().contains(transactionType);
    }

    /**
     * Validate the quantity for this asset type.
     * Can be overridden by subclasses with specific rules (e.g., whole units for gold).
     * @return true if quantity is valid, false otherwise
     */
    public boolean isQuantityValid(BigDecimal qty) {
        return qty != null && qty.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Get the minimum quantity increment for this asset type.
     * Default is 0.00000001 (8 decimals), subclasses can override.
     */
    public BigDecimal getMinimumQuantityIncrement() {
        return new BigDecimal("0.00000001");
    }

    /**
     * Get a description specific to this asset type.
     */
    public abstract String getTypeDescription();
}
