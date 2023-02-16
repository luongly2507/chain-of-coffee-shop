package com.app.coffee.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.app.coffee.payload.request.CreateTagRequest;
import com.app.coffee.payload.request.UpdateTagRequest;
import com.app.coffee.service.TagService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tags")
@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
public class TagRestController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<?> getAllTag(@RequestParam(required = false) String key,@RequestParam UUID branchId,Pageable pageable) {
        return ResponseEntity.ok(tagService.getAllTags(key,branchId, pageable));
    }
    @GetMapping("/{tagId}")
    public ResponseEntity<?> getTagById(@PathVariable UUID tagId) {
             return ResponseEntity.ok(tagService.getTagById(tagId));
    }
    @GetMapping("/branch/{branchID}")
    public ResponseEntity<?> getAllTagsByBranch(@PathVariable UUID branchID) {
        System.out.println(branchID);
        return ResponseEntity.ok(tagService.getAllTagsByBranchId(branchID));
    }
    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody @Valid CreateTagRequest createTagRequest) {
        System.out.println(createTagRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tagService.createTag(createTagRequest).getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/{tagId}")
    public ResponseEntity<?> updateTagById(@PathVariable UUID tagId, @RequestBody @Valid UpdateTagRequest updateTagRequest) {
        System.out.println(updateTagRequest);
        tagService.updateTag(tagId, updateTagRequest);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{tagId}")
    public ResponseEntity<?> deleteTag(@PathVariable UUID tagId) {
        System.out.println(tagId);
        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }
}
