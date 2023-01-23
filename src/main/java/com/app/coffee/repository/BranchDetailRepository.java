package com.app.coffee.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.coffee.entity.BranchDetail;
import com.app.coffee.entity.BranchDetailId;

@Repository
public interface BranchDetailRepository extends JpaRepository<BranchDetail,BranchDetailId> {
    
}
