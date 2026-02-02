package com.demo.MoneyMap.mapper;

import com.demo.MoneyMap.dto.request.AssetRequestDTO;
import com.demo.MoneyMap.dto.response.AssetResponseDTO;
import com.demo.MoneyMap.beans.*;
import org.springframework.stereotype.Component;

/**
 * Mapper class for Asset entity and DTOs.
 * Handles conversions between Asset entity (and its subtypes) and DTOs.
 * 
 * Demonstrates polymorphism - can work with any Asset subtype.
 */
@Component
public class AssetMapper {

    /**
     * Convert Asset entity (any subtype) to AssetResponseDTO.
     * Uses polymorphism to handle all asset types.
     */
    public AssetResponseDTO toResponseDTO(Asset entity) {
        if (entity == null) {
            return null;
        }

        AssetResponseDTO.AssetResponseDTOBuilder builder = AssetResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .symbol(entity.getSymbol())
                .assetType(entity.getType())
                .assetTypeDisplayName(entity.getType() != null ? entity.getType().getDisplayName() : null)
                .typeDescription(entity.getTypeDescription())
                .quantity(entity.getQuantity())
                .purchasePrice(entity.getPurchasePrice())
                .currentPrice(entity.getCurrentPrice())
                .currentValue(entity.getCurrentValue())
                .purchaseDate(entity.getPurchaseDate())
                .portfolioId(entity.getPortfolio() != null ? entity.getPortfolio().getId() : null)
                .portfolioName(entity.getPortfolio() != null ? entity.getPortfolio().getName() : null)
                .profitLoss(entity.getProfitLoss())
                .profitLossPercentage(entity.getProfitLossPercentage())
                .notes(entity.getNotes())
                .allowedTransactionTypes(entity.getAllowedTransactionTypes())
                .minimumQuantityIncrement(entity.getMinimumQuantityIncrement())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt());

        // Add type-specific fields using instanceof pattern matching (Java 16+)
        if (entity instanceof StockAsset stock) {
            builder.exchange(stock.getExchange())
                   .sector(stock.getSector())
                   .dividendYield(stock.getDividendYield())
                   .fractionalAllowed(stock.getFractionalAllowed());
        } else if (entity instanceof CryptoAsset crypto) {
            builder.blockchainNetwork(crypto.getBlockchainNetwork())
                   .walletAddress(crypto.getWalletAddress())
                   .stakingEnabled(crypto.getStakingEnabled())
                   .stakingApy(crypto.getStakingApy());
        } else if (entity instanceof GoldAsset gold) {
            builder.goldForm(gold.getGoldForm())
                   .purity(gold.getPurity())
                   .weightUnit(gold.getWeightUnit())
                   .storageLocation(gold.getStorageLocation())
                   .isPhysical(gold.getIsPhysical());
        } else if (entity instanceof MutualFundAsset fund) {
            builder.fundCategory(fund.getFundCategory())
                   .amcName(fund.getAmcName())
                   .planType(fund.getPlanType())
                   .expenseRatio(fund.getExpenseRatio())
                   .navDate(fund.getNavDate())
                   .riskLevel(fund.getRiskLevel())
                   .minInvestment(fund.getMinInvestment());
        }

        return builder.build();
    }

    /**
     * Update existing Asset entity from AssetRequestDTO.
     * Updates common fields and type-specific fields.
     */
    public void updateEntityFromDTO(AssetRequestDTO dto, Asset entity) {
        if (dto == null || entity == null) {
            return;
        }

        // Update common fields
        entity.setName(dto.getName());
        entity.setSymbol(dto.getSymbol());
        entity.setQuantity(dto.getQuantity());
        entity.setPurchasePrice(dto.getPurchasePrice());
        if (dto.getCurrentPrice() != null) {
            entity.setCurrentPrice(dto.getCurrentPrice());
        }
        entity.setPurchaseDate(dto.getPurchaseDate());
        entity.setNotes(dto.getNotes());

        // Update type-specific fields
        if (entity instanceof StockAsset stock) {
            if (dto.getExchange() != null) stock.setExchange(dto.getExchange());
            if (dto.getSector() != null) stock.setSector(dto.getSector());
            if (dto.getDividendYield() != null) stock.setDividendYield(dto.getDividendYield());
            if (dto.getFractionalAllowed() != null) stock.setFractionalAllowed(dto.getFractionalAllowed());
        } else if (entity instanceof CryptoAsset crypto) {
            if (dto.getBlockchainNetwork() != null) crypto.setBlockchainNetwork(dto.getBlockchainNetwork());
            if (dto.getWalletAddress() != null) crypto.setWalletAddress(dto.getWalletAddress());
            if (dto.getStakingEnabled() != null) crypto.setStakingEnabled(dto.getStakingEnabled());
            if (dto.getStakingApy() != null) crypto.setStakingApy(dto.getStakingApy());
        } else if (entity instanceof GoldAsset gold) {
            if (dto.getGoldForm() != null) gold.setGoldForm(dto.getGoldForm());
            if (dto.getPurity() != null) gold.setPurity(dto.getPurity());
            if (dto.getWeightUnit() != null) gold.setWeightUnit(dto.getWeightUnit());
            if (dto.getStorageLocation() != null) gold.setStorageLocation(dto.getStorageLocation());
            if (dto.getIsPhysical() != null) gold.setIsPhysical(dto.getIsPhysical());
        } else if (entity instanceof MutualFundAsset fund) {
            if (dto.getFundCategory() != null) fund.setFundCategory(dto.getFundCategory());
            if (dto.getAmcName() != null) fund.setAmcName(dto.getAmcName());
            if (dto.getPlanType() != null) fund.setPlanType(dto.getPlanType());
            if (dto.getExpenseRatio() != null) fund.setExpenseRatio(dto.getExpenseRatio());
            if (dto.getNavDate() != null) fund.setNavDate(dto.getNavDate());
            if (dto.getRiskLevel() != null) fund.setRiskLevel(dto.getRiskLevel());
            if (dto.getMinInvestment() != null) fund.setMinInvestment(dto.getMinInvestment());
        }

        entity.calculateCurrentValue();
    }
}
