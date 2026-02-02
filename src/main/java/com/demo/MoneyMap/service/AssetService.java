package com.demo.MoneyMap.service;

import com.demo.MoneyMap.dto.request.AssetRequestDTO;
import com.demo.MoneyMap.dto.response.AssetResponseDTO;
import com.demo.MoneyMap.dto.response.PagedResponseDTO;
import com.demo.MoneyMap.beans.enums.AssetType;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for asset management operations.
 * Follows Interface Segregation Principle (ISP) from SOLID.
 */
public interface AssetService {

    /**
     * Create a new asset.
     *
     * @param requestDTO the asset data to create
     * @return the created asset
     */
    AssetResponseDTO createAsset(AssetRequestDTO requestDTO);

    /**
     * Get an asset by ID.
     *
     * @param id the asset ID
     * @return the asset details
     */
    AssetResponseDTO getAssetById(Long id);

    /**
     * Get an asset by ID with transactions.
     *
     * @param id the asset ID
     * @return the asset details with transactions
     */
    AssetResponseDTO getAssetByIdWithTransactions(Long id);

    /**
     * Get all assets with pagination.
     *
     * @param pageable pagination parameters
     * @return paginated list of assets
     */
    PagedResponseDTO<AssetResponseDTO> getAllAssets(Pageable pageable);

    /**
     * Get all assets in a specific portfolio.
     *
     * @param portfolioId the portfolio ID
     * @return list of assets
     */
    List<AssetResponseDTO> getAssetsByPortfolioId(Long portfolioId);

    /**
     * Get all assets in a portfolio with pagination.
     *
     * @param portfolioId the portfolio ID
     * @param pageable pagination parameters
     * @return paginated list of assets
     */
    PagedResponseDTO<AssetResponseDTO> getAssetsByPortfolioId(Long portfolioId, Pageable pageable);

    /**
     * Get all assets of a specific type.
     *
     * @param assetType the asset type
     * @return list of assets
     */
    List<AssetResponseDTO> getAssetsByType(AssetType assetType);

    /**
     * Get all assets of a specific type with pagination.
     *
     * @param assetType the asset type
     * @param pageable pagination parameters
     * @return paginated list of assets
     */
    PagedResponseDTO<AssetResponseDTO> getAssetsByType(AssetType assetType, Pageable pageable);

    /**
     * Get all assets for a client.
     *
     * @param clientId the client ID
     * @return list of assets
     */
    List<AssetResponseDTO> getAssetsByClientId(Long clientId);

    /**
     * Search assets by name or symbol.
     *
     * @param searchTerm the search term
     * @param pageable pagination parameters
     * @return paginated list of matching assets
     */
    PagedResponseDTO<AssetResponseDTO> searchAssets(String searchTerm, Pageable pageable);

    /**
     * Update an existing asset.
     *
     * @param id the asset ID
     * @param requestDTO the updated asset data
     * @return the updated asset
     */
    AssetResponseDTO updateAsset(Long id, AssetRequestDTO requestDTO);

    /**
     * Update asset's current price.
     *
     * @param id the asset ID
     * @param currentPrice the new current price
     * @return the updated asset
     */
    AssetResponseDTO updateAssetPrice(Long id, BigDecimal currentPrice);

    /**
     * Delete an asset permanently.
     *
     * @param id the asset ID
     */
    void deleteAsset(Long id);

    /**
     * Get total value by asset type.
     *
     * @param assetType the asset type
     * @return total value
     */
    BigDecimal getTotalValueByAssetType(AssetType assetType);

    /**
     * Get all available asset types.
     *
     * @return list of asset types with display names
     */
    List<AssetType> getAssetTypes();
}
