package com.retailer.rewardprogram.dto;

import lombok.Data;

import java.util.List;

@Data
public class RewardPointsRequestDTO {
    private String customerId;
    private String startDate;
    private String endDate;
    private List<TransactionDTO> transactions;
}
