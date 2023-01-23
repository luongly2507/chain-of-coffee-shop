package com.app.coffee.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class BranchDetailId {
    @Column (name = "branch_id")
    private UUID branchId;
    @Column (name = "users_id")
    private UUID userID;
}
