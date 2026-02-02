package com.demo.MoneyMap.mapper;

import com.demo.MoneyMap.dto.request.ClientRequestDTO;
import com.demo.MoneyMap.dto.response.ClientResponseDTO;
import com.demo.MoneyMap.dto.response.PortfolioSummaryDTO;
import com.demo.MoneyMap.beans.Client;
import com.demo.MoneyMap.beans.Portfolio;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for Client entity and DTOs.
 * Handles conversions between Client entity and its DTOs.
 */
@Component
public class ClientMapper {

    /**
     * Convert ClientRequestDTO to Client entity.
     */
    public Client toEntity(ClientRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .active(true)
                .build();
    }

    /**
     * Convert Client entity to ClientResponseDTO (without portfolios).
     */
    public ClientResponseDTO toResponseDTO(Client entity) {
        if (entity == null) {
            return null;
        }

        return ClientResponseDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .active(entity.getActive())
                .portfolioCount(entity.getPortfolios() != null ? entity.getPortfolios().size() : 0)
                .portfolios(Collections.emptyList())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Convert Client entity to ClientResponseDTO (with portfolios).
     */
    public ClientResponseDTO toResponseDTOWithPortfolios(Client entity) {
        if (entity == null) {
            return null;
        }

        List<PortfolioSummaryDTO> portfolioSummaries = entity.getPortfolios() != null
                ? entity.getPortfolios().stream()
                        .map(this::toPortfolioSummaryDTO)
                        .collect(Collectors.toList())
                : Collections.emptyList();

        return ClientResponseDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .active(entity.getActive())
                .portfolioCount(portfolioSummaries.size())
                .portfolios(portfolioSummaries)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Update existing Client entity from ClientRequestDTO.
     */
    public void updateEntityFromDTO(ClientRequestDTO dto, Client entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
    }

    /**
     * Convert Portfolio to PortfolioSummaryDTO.
     */
    private PortfolioSummaryDTO toPortfolioSummaryDTO(Portfolio portfolio) {
        return PortfolioSummaryDTO.builder()
                .id(portfolio.getId())
                .name(portfolio.getName())
                .totalValue(portfolio.getTotalValue())
                .assetCount(portfolio.getAssets() != null ? portfolio.getAssets().size() : 0)
                .active(portfolio.getActive())
                .build();
    }
}
