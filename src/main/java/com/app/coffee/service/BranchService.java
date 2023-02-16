package com.app.coffee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.coffee.payload.request.CreateBranchRequest;
import com.app.coffee.payload.request.UpdateBranchRequest;
import com.app.coffee.payload.response.BranchResponse;

import jakarta.validation.Valid;

public interface BranchService {
    public List<BranchResponse> getAllBranches();
    public Page<BranchResponse> getAllBranches(String key, Pageable pageable);
    public BranchResponse getBranchById(UUID categoryId);
    public void deleteBranch(UUID categoryId);
    public BranchResponse createBranch(@Valid CreateBranchRequest createBranchRequest);
    public void updateBranch(UUID categoryId, @Valid UpdateBranchRequest updateBranchRequest);
    public Page<BranchResponse> getAllBranchesByName(String search, Pageable pageable);
    public long getCountBranch();
}
