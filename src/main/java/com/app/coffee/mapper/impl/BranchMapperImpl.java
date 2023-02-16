package com.app.coffee.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.coffee.entity.Branch;
import com.app.coffee.entity.Tag;
import com.app.coffee.mapper.BranchMapper;
import com.app.coffee.mapper.TagMapper;
import com.app.coffee.mapper.UserMapper;
import com.app.coffee.payload.request.CreateBranchRequest;
import com.app.coffee.payload.request.UpdateBranchRequest;
import com.app.coffee.payload.response.BranchResponse;

@Component
public class BranchMapperImpl implements BranchMapper {

    @Autowired
    private TagMapper tagMapper;

    
    @Autowired
    private UserMapper userMapper;

    @Override
    public BranchResponse toBranchResponse(Branch branch) {
        if (branch == null) {
            return null;
        }
        return BranchResponse.builder()
                .id(branch.getId())
                .name(branch.getName())
                .address(branch.getAddress())
                .description(branch.getDescription())
                .createdAt(branch.getCreatedAt())
                .createdBy(branch.getCreatedBy())
                .lastModifiedAt(branch.getLastModifiedAt())
                .lastModifiedBy(branch.getLastModifiedBy())
                .build();
    }

    @Override
    public Branch toBranch(CreateBranchRequest createBranchRequest) {
        if (createBranchRequest == null) {
            return null;
        }
        Branch branch =  Branch.builder()
                .name(createBranchRequest.getName())
                .address(createBranchRequest.getAddress())
                .description(createBranchRequest.getDescription())
                .build();
                return branch;
    }

    @Override
    public void updateBranch(UpdateBranchRequest updateBranchRequest, Branch branch) {
        if (updateBranchRequest == null) {
            return;
        }
        branch.setName(updateBranchRequest.getName());
        branch.setAddress(updateBranchRequest.getAddress());
        branch.setDescription(updateBranchRequest.getDescription());
    }
}
