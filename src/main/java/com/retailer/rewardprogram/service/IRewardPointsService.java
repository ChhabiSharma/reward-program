package com.retailer.rewardprogram.service;

import com.retailer.rewardprogram.dto.RewardPointsRequestDTO;
import com.retailer.rewardprogram.dto.RewardPointsResponseDTO;
import com.retailer.rewardprogram.dto.error.ErrorResponse;

import java.util.List;

public interface IRewardPointsService {
    List<RewardPointsResponseDTO> calculateRewardPoints(List<RewardPointsRequestDTO> rewardPointsRequestDTO, List<ErrorResponse> errorResponse);

    void validateInput(List<RewardPointsRequestDTO> rewardPointsRequestDTO, List<ErrorResponse> errorResponse);
}
