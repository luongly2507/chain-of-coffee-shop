package com.app.coffee.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.coffee.payload.request.CreateBranchRequest;
import com.app.coffee.payload.request.UpdateBranchRequest;
import com.app.coffee.service.BranchService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/branchs")
public class BranchController {
    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<?> getAllBranchs() {
        return ResponseEntity.ok(branchService.getAllBranchs());
    }

    @GetMapping("/page")
    public ResponseEntity<?> getAllBranchs(Pageable pageable) {
        return ResponseEntity.ok(branchService.getAllBranchs(pageable));
    }
    @GetMapping("/search")
    public ResponseEntity<?> getAllBranchsByName(@RequestParam String key, Pageable pageable) {
        return ResponseEntity.ok(branchService.getAllBranchsByName(key,pageable));
    }
    @GetMapping("/{branchId}")
    public ResponseEntity<?> getBranchById(
            @PathVariable UUID branchId) {
        return ResponseEntity.ok(branchService.getBranchById(branchId));
    }

    @PostMapping
    public ResponseEntity<?> createBranch(@RequestBody @Valid CreateBranchRequest createBranchRequest) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(branchService.createBranch(createBranchRequest).getId()).toUri();
        System.out.println(location);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{branchId}")
    public ResponseEntity<?> updateBranch(
            @PathVariable UUID branchId,
            @RequestBody @Valid UpdateBranchRequest updateBranchRequest) {
        branchService.updateBranch(branchId, updateBranchRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{branchId}")
    public ResponseEntity<?> deleteBranch(
            @PathVariable UUID branchId) {
        branchService.deleteBranch(branchId);
        return ResponseEntity.noContent().build();
    }

}
