package com.app.coffee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Entity(name = "branch_detail")
public class BranchDetail {
    @EmbeddedId
    private BranchDetailId id;

    @ManyToOne
    @MapsId("users_id")
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne
    @MapsId("branch_id")
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column
    private String description;
}
