package com.retailer.rewardprogram.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class TransactionDTO {

    private String transactionId;
    private double amount;
    private String date;
}
