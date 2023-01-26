package com.app.coffee.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@Embeddable
public class BranchDetailId {
    @Column (name = "branch_id")
    private UUID branchId;
    @Column (name = "users_id")
    private UUID userID;
}
