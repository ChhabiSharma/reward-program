package com.retailer.rewardprogram.controller;

import com.retailer.rewardprogram.dto.RewardPointsRequestDTO;
import com.retailer.rewardprogram.dto.RewardPointsResponseDTO;
import com.retailer.rewardprogram.dto.error.ErrorResponse;
import com.retailer.rewardprogram.service.IRewardPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chhabi Sharma
 * This class will manage all the resources for rewards program.
 */
@RestController
@RequestMapping("/api/v1")
public class RewardPointsController {

    final IRewardPointsService iRewardPointService;

    @Autowired
    RewardPointsController(IRewardPointsService iRewardPointService1) {
        this.iRewardPointService = iRewardPointService1;
    }

    @PostMapping(value = "/reward-points")
    public ResponseEntity<List<RewardPointsResponseDTO>> calculateRewardPoints(
            @RequestBody List<RewardPointsRequestDTO> rewardPointsRequestDTO) {

        final List<ErrorResponse> errorResponse = new ArrayList<>();

        iRewardPointService.validateInput(rewardPointsRequestDTO, errorResponse);
        List<RewardPointsResponseDTO> responseDTO = iRewardPointService.calculateRewardPoints(rewardPointsRequestDTO, errorResponse);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
