package com.example.demo.service;

import com.example.demo.Repository.RewardRepository;
import com.example.demo.entity.Reward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RewardService {
    private final RewardRepository rewardRepository;
    @Autowired
    public RewardService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }
    public List<Reward> getAllRewards() {
        return rewardRepository.findAll();
    }
    public Optional<Reward> getRewardById(Long id) {
        return rewardRepository.findById(id);
    }
    public Optional<Reward> getRewardByCustomerId(Long customerId) {
        return rewardRepository.findByCustomerId(customerId);
    }
    @Transactional
    public Reward saveReward(Reward reward) {
        return rewardRepository.save(reward);
    }
    @Transactional
    public Reward updateReward(Reward reward) {
        // First check if the reward exists
        if (reward.getId() != null && rewardRepository.existsById(reward.getId())) {
            return rewardRepository.save(reward);
        }
        return null;
    }
    @Transactional
    public void deleteReward(Long id) {
        rewardRepository.deleteById(id);
    }
    @Transactional
    public Reward addPoints(Long id, int points) {
        Optional<Reward> rewardOpt = rewardRepository.findById(id);
        if (rewardOpt.isPresent()) {
            Reward reward = rewardOpt.get();
            reward.setPoints(reward.getPoints() + points);
            return rewardRepository.save(reward);
        }
        return null;
    }
}
