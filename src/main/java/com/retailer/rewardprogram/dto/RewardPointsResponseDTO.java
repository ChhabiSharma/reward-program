package com.retailer.rewardprogram.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.retailer.rewardprogram.dto.error.ErrorResponse;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RewardPointsResponseDTO {
    private ErrorResponse errorResponse;
    private String customerId;
    private String startDate;
    private String endDate;
    private Map<String, Integer> rewardsPerMonth;
    private int totalRewards;
}
