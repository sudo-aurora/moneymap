package com.demo.MoneyMap.beans;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a client's investment portfolio.
 * A portfolio contains multiple assets and tracks its total value.
 * Each portfolio belongs to exactly one client (one-to-one relationship).
 */
@Entity
@Table(name = "portfolios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, unique = true)
    private Client client;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Asset> assets = new ArrayList<>();

    @Column(name = "total_value", precision = 19, scale = 4)
    @Builder.Default
    private BigDecimal totalValue = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Utility method to add an asset to the portfolio.
     */
    public void addAsset(Asset asset) {
        assets.add(asset);
        asset.setPortfolio(this);
        recalculateTotalValue();
    }

    /**
     * Utility method to remove an asset from the portfolio.
     */
    public void removeAsset(Asset asset) {
        assets.remove(asset);
        asset.setPortfolio(null);
        recalculateTotalValue();
    }

    /**
     * Recalculates the total value of the portfolio based on all assets.
     */
    public void recalculateTotalValue() {
        this.totalValue = assets.stream()
                .map(Asset::getCurrentValue)
                .filter(value -> value != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
