package com.retailer.rewardprogram.dto.error;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
public class ErrorResponse {
    public String customerId;
    private String message;
    private List<String> errors;
}
