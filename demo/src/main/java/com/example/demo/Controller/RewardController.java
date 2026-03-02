package com.example.demo.Controller;

import com.example.demo.entity.Reward;
import com.example.demo.service.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rewards")
@Tag(name = "Reward", description = "Reward management APIs")
public class RewardController {
    private final RewardService rewardService;
    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }
    @Operation(summary = "Get all rewards", description = "Returns all rewards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved rewards", content = @Content(schema = @Schema(implementation = Reward.class)))
    })
    @GetMapping
    public ResponseEntity<List<Reward>> getAllRewards() {
        List<Reward> rewards = rewardService.getAllRewards();
        return new ResponseEntity<>(rewards, HttpStatus.OK);
    }
    @Operation(summary = "Get reward by ID", description = "Returns a single reward by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the reward", content = @Content(schema = @Schema(implementation = Reward.class))),
            @ApiResponse(responseCode = "404", description = "Reward not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reward> getRewardById(@PathVariable Long id) {
        Optional<Reward> reward = rewardService.getRewardById(id);
        return reward.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Operation(summary = "Get reward by customer ID", description = "Returns a reward associated with a customer")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Reward> getRewardByCustomerId(@PathVariable Long customerId) {
        Optional<Reward> reward = rewardService.getRewardByCustomerId(customerId);
        return reward.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Operation(summary = "Create a new reward", description = "Creates a new reward")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reward created successfully", content = @Content(schema = @Schema(implementation = Reward.class)))
    })
    @PostMapping
    public ResponseEntity<Reward> createReward(@RequestBody Reward reward) {
        Reward createdReward = rewardService.saveReward(reward);
        return new ResponseEntity<>(createdReward, HttpStatus.CREATED);
    }
    @Operation(summary = "Update an existing reward", description = "Updates a reward by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reward updated successfully", content = @Content(schema = @Schema(implementation = Reward.class))),
            @ApiResponse(responseCode = "404", description = "Reward not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Reward> updateReward(@PathVariable Long id, @RequestBody Reward reward) {
        Optional<Reward> existingReward = rewardService.getRewardById(id);
        if (existingReward.isPresent()) {
            reward.setId(id);
            Reward updatedReward = rewardService.updateReward(reward);
            return new ResponseEntity<>(updatedReward, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Update reward points", description = "Updates only the points of a reward")
    @PatchMapping("/{id}/points")
    public ResponseEntity<Reward> updateRewardPoints(
            @PathVariable Long id,
            @RequestParam int points) {
        Optional<Reward> existingReward = rewardService.getRewardById(id);
        if (existingReward.isPresent()) {
            Reward reward = existingReward.get();
            reward.setPoints(points);
            Reward updatedReward = rewardService.updateReward(reward);
            return new ResponseEntity<>(updatedReward, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Delete a reward", description = "Deletes a reward by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reward deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Reward not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReward(@PathVariable Long id) {
        Optional<Reward> reward = rewardService.getRewardById(id);
        if (reward.isPresent()) {
            rewardService.deleteReward(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
