package com.app.coffee.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.coffee.entity.Tag;
import com.app.coffee.exception.ResourceNotFoundException;
import com.app.coffee.mapper.BranchMapper;
import com.app.coffee.mapper.TagMapper;
import com.app.coffee.payload.request.CreateTagRequest;
import com.app.coffee.payload.request.UpdateTagRequest;
import com.app.coffee.payload.response.TagResponse;
import com.app.coffee.repository.BranchRepository;

@Component
public class TagMapperImpl implements TagMapper {

    @Autowired
    private BranchMapper branchMapper;

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public TagResponse toTagResponse(Tag tag) {
        if (tag == null) {
            return null;
        }
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .status(tag.getStatus())
                .branch(branchMapper.toBranchResponse(tag.getBranch()))
                .createdAt(tag.getCreatedAt())
                .createdBy(tag.getCreatedBy())
                .lastModifiedAt(tag.getLastModifiedAt())
                .lastModifiedBy(tag.getLastModifiedBy())
                .build();
    }

    @Override
    public Tag toTag(CreateTagRequest createTagRequest) {
        if (createTagRequest == null) {
            return null;
        }
        return Tag.builder()
                .name(createTagRequest.getName())
                .status("Trống")
                .branch(branchRepository.findById(createTagRequest.getBranchID())
                        .orElseThrow(() -> new ResourceNotFoundException("Branch Not Found")))
                .build();
    }

    @Override
    public void updateTag(UpdateTagRequest updateTag, Tag tag) {
        if (updateTag == null) {
            return;
        }
        tag.setName(updateTag.getName());
        tag.setStatus(updateTag.getStatus());
    }

}
