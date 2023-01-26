package com.app.coffee.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.coffee.entity.Branch;
import com.app.coffee.exception.BadRequestException;
import com.app.coffee.exception.ConflictException;
import com.app.coffee.exception.ResourceNotFoundException;
import com.app.coffee.mapper.BranchMapper;
import com.app.coffee.payload.request.CreateBranchRequest;
import com.app.coffee.payload.request.UpdateBranchRequest;
import com.app.coffee.payload.response.BranchResponse;
import com.app.coffee.repository.BranchRepository;
import com.app.coffee.service.BranchService;

import jakarta.validation.Valid;

@Service
@Transactional
public class BranchServiceImpl implements BranchService{

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchMapper branchMapper;
    
    // Get Mapping - Get All
    @Override
    public List<BranchResponse> getAllBranchs() {
        return branchRepository.findAll().stream().map(
            branch -> branchMapper.toBranchResponse(branch)
        ).toList();
    }

    // Get Mapping - Get By Page
    @Override
    public Page<BranchResponse> getAllBranchs(Pageable pageable) {
        return branchRepository.findAll(pageable)
        .map(branch->branchMapper.toBranchResponse(branch));
    }

    // Get Mapping - Get By Id
    @Override
    public BranchResponse getBranchById(UUID branchId) {
        return branchMapper.toBranchResponse(
            branchRepository.findById(branchId)
                    .orElseThrow(() -> new ResourceNotFoundException("Branch Not Found"))); // Check branch is present
    }

    // Post Mapping - Create Branch
    @Override
    public BranchResponse createBranch(@Valid CreateBranchRequest createBranchRequest) {
        Branch branch = branchMapper.toBranch(createBranchRequest);
        // Check branch is not null
        if (branch == null) {
            throw new BadRequestException("Bad Request");
        }
         // Check name is unique
        if (branchRepository.existsByName(createBranchRequest.getName())) {
            throw new ConflictException("Already Name Exists.");
        }
        return branchMapper.toBranchResponse(branchRepository.save(branch));       
    }

    // Put Mapping - Update Branch
    @Override
    public void updateBranch(UUID branchId, @Valid UpdateBranchRequest updateBranchRequest) {
        Branch branch = branchRepository.findById(branchId)
            .orElseThrow(
                ()-> new ResourceNotFoundException("Branch Not Found."));
          // Check name is unique
          if (!updateBranchRequest.getName().equals(branch.getName())) { // compare request name and repository name
            if (branchRepository.existsByName(updateBranchRequest.getName())) {
                throw new ConflictException("Already Name Exists.");
            }
        }
        branchMapper.updateBranch(updateBranchRequest, branch);
        branchRepository.save(branch);
    }

    // Delete Mapping -
    @Override
    public void deleteBranch(UUID branchId) {
        if (branchRepository.existsById(branchId)) {
            branchRepository.deleteById(branchId);
        } else {
            throw new ResourceNotFoundException("Branch Not Found");  // Check branch is present
        }
    }

    @Override
    public Page<BranchResponse> getAllBranchsByName(String search, Pageable pageable) {
        return  branchRepository.findByName(search.toLowerCase(),pageable).map(branch->branchMapper.toBranchResponse(branch));
    }
}
