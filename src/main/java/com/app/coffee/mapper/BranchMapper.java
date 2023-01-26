package com.app.coffee.mapper;

import com.app.coffee.entity.Branch;
import com.app.coffee.payload.request.CreateBranchRequest;
import com.app.coffee.payload.request.UpdateBranchRequest;
import com.app.coffee.payload.response.BranchResponse;

public interface BranchMapper {
    public BranchResponse toBranchResponse(Branch branch);
    public Branch toBranch(CreateBranchRequest createBranchRequest);
    public void updateBranch (UpdateBranchRequest updateBranch, Branch branch);

}
