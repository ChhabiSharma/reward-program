package com.retailer.rewardprogram.service.impl;

import com.retailer.rewardprogram.dto.RewardPointsRequestDTO;
import com.retailer.rewardprogram.dto.RewardPointsResponseDTO;
import com.retailer.rewardprogram.dto.TransactionDTO;
import com.retailer.rewardprogram.dto.error.ErrorResponse;
import com.retailer.rewardprogram.service.IRewardPointsService;
import com.retailer.rewardprogram.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardPointsServiceImpl implements IRewardPointsService {

    @Override
    public void validateInput(List<RewardPointsRequestDTO> rewardPointsRequestDTO, List<ErrorResponse> errorResponse) {

        for (RewardPointsRequestDTO rewards : rewardPointsRequestDTO) {
            List<String> errors = new ArrayList<>();
            ErrorResponse errorResponseObj = new ErrorResponse();
            errorResponseObj.setCustomerId(rewards.getCustomerId());

            if (StringUtils.isEmpty(rewards.getCustomerId())) {
                errors.add("Customer ID must not be blank");
            }
            if (StringUtils.isEmpty(rewards.getStartDate())) {
                errors.add("Start date must not be null");
            } else if (!(rewards.getStartDate().matches("\\d{4}-\\d{2}-\\d{2}"))) {
                errors.add("Start date must be in the format YYYY-MM-DD");
            }
            if (StringUtils.isEmpty(rewards.getStartDate())) {
                errors.add("End date must not be null");
            } else if (!(rewards.getEndDate().matches("\\d{4}-\\d{2}-\\d{2}"))) {
                errors.add("End date must be in the format YYYY-MM-DD");
            }
            if (Objects.isNull(rewards.getTransactions())) {
                errors.add("Transactions must not be null");
            }
            for (TransactionDTO transactionDTO : rewards.getTransactions()) {
                if (StringUtils.isEmpty(transactionDTO.getTransactionId())) {
                    errors.add("Transaction ID must not be blank");
                }
                if (Objects.isNull(transactionDTO.getAmount())) {
                    errors.add("Amount must not be null");
                } else if (transactionDTO.getAmount() <= 0) {
                    errors.add("Amount must be greater than zero");
                }
                if (StringUtils.isEmpty(transactionDTO.getDate())) {
                    errors.add("Date must not be null");
                } else if (!(transactionDTO.getDate().matches("\\d{4}-\\d{2}-\\d{2}"))) {
                    errors.add("Date must be in the format YYYY-MM-DD");
                }
            }
            if (errors.size() != 0) {
                errorResponseObj.setMessage("Validation Failed");
            }
            errorResponseObj.setErrors(errors);
            errorResponse.add(errorResponseObj);
        }

    }

    @Override
    public List<RewardPointsResponseDTO> calculateRewardPoints(final List<RewardPointsRequestDTO> rewardPointsRequestDTO, final List<ErrorResponse> errorResponse) {

        List<RewardPointsResponseDTO> rewardsResponse = new ArrayList<>();
        removeMatchingCustomers(rewardPointsRequestDTO, errorResponse);

        for (ErrorResponse errors : errorResponse) {
            if (errors.getErrors().size() > 0) {
                RewardPointsResponseDTO rewardPointsResponseDTO = RewardPointsResponseDTO.builder()
                        .errorResponse(errors)
                        .build();
                rewardsResponse.add(rewardPointsResponseDTO);
            }
        }

        for (RewardPointsRequestDTO rewards : rewardPointsRequestDTO) {
            int totalRewardPoints = calculateTotalRewards(rewards.getTransactions());
            Map<String, Integer> rewardPointsPerMonth = calculateRewardsPerMonth(rewards.getTransactions());

            RewardPointsResponseDTO rewardPointsResponseDTO = RewardPointsResponseDTO.builder()
                    .customerId(rewards.getCustomerId())
                    .startDate(rewards.getStartDate())
                    .endDate(rewards.getEndDate())
                    .rewardsPerMonth(rewardPointsPerMonth)
                    .totalRewards(totalRewardPoints)
                    .build();

            rewardsResponse.add(rewardPointsResponseDTO);
        }

        return rewardsResponse;
    }

    private Map<String, Integer> calculateRewardsPerMonth(List<TransactionDTO> transactions) {
        Map<String, Integer> rewardsPerMonth = transactions.stream()
                .collect(Collectors.groupingBy(transaction -> DateUtils.getMonthYear(transaction.getDate()),
                                                Collectors.summingInt(this::calculateTransactionRewards)));

        rewardsPerMonth = rewardsPerMonth.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));

        return rewardsPerMonth;
    }

    private int calculateTotalRewards(List<TransactionDTO> transactions) {
        return transactions.stream()
                .mapToInt(this::calculateTransactionRewards)
                .sum();
    }

    private int calculateTransactionRewards(TransactionDTO transaction) {
        double amount = transaction.getAmount();
        if (amount > 100) {
            return 2 * (int) (amount - 100) + (int) Math.min(50, amount - 50);
        } else if (amount > 50) {
            return (int) (amount - 50);
        } else {
            return 0;
        }
    }

    private void removeMatchingCustomers(List<RewardPointsRequestDTO> rewardPointsRequests,
                                                List<ErrorResponse> errorResponses) {
        Iterator<RewardPointsRequestDTO> iterator = rewardPointsRequests.iterator();

        while (iterator.hasNext()) {
            RewardPointsRequestDTO requestDTO = iterator.next();
            String customerId = requestDTO.getCustomerId();

            for (ErrorResponse errorResponse : errorResponses) {
                if ((customerId.equals(errorResponse.getCustomerId())
                        && !errorResponse.getErrors().isEmpty())) {
                    iterator.remove();
                    break;
                }
            }
        }
    }
}
