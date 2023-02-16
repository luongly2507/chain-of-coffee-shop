package com.app.coffee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.coffee.payload.request.CreateTagRequest;
import com.app.coffee.payload.request.UpdateTagRequest;
import com.app.coffee.payload.response.TagResponse;

import jakarta.validation.Valid;

public interface TagService {
    public List<TagResponse> getAllTags();
    public Page<TagResponse> getAllTags(String key, UUID branchId, Pageable pageable);
    public TagResponse getTagById(UUID tagId);
    public void deleteTag(UUID tagId);
    public TagResponse createTag(@Valid CreateTagRequest createTagRequest);
    public void updateTag(UUID tagId, @Valid UpdateTagRequest updateTagRequest);
    public Page<TagResponse> getAllTagsByName(String search, UUID branchId, Pageable pageable);
    public List<TagResponse> getAllTagsByBranchId(UUID branchId);
}
