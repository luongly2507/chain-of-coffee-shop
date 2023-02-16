package com.app.coffee.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.coffee.entity.Tag;
import com.app.coffee.exception.BadRequestException;
import com.app.coffee.exception.ConflictException;
import com.app.coffee.exception.ResourceNotFoundException;
import com.app.coffee.mapper.TagMapper;
import com.app.coffee.payload.request.CreateTagRequest;
import com.app.coffee.payload.request.UpdateTagRequest;
import com.app.coffee.payload.response.TagResponse;
import com.app.coffee.repository.TagRepository;
import com.app.coffee.service.TagService;

import jakarta.validation.Valid;

@Service
@Transactional
public class TagServiceImpl implements TagService{

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagMapper tagMapper;

    // Get Mapping - Get All
    @Override
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream().map(
            tag -> tagMapper.toTagResponse(tag)
        ).toList();
    }

    // Get Mapping - Get By Page
    @Override
    public Page<TagResponse> getAllTags(String key, UUID branchId, Pageable pageable) {
        if (key != null) {
            return getAllTagsByName(key,branchId, pageable);
        } else {
            return tagRepository.findAllByBranchID(branchId, pageable).map(tag->tagMapper.toTagResponse(tag));

        }
    }

    // Get Mapping - Get By Id
    @Override
    public TagResponse getTagById(UUID tagId) {
        return tagMapper.toTagResponse(
            tagRepository.findById(tagId)
                    .orElseThrow(() -> new ResourceNotFoundException("Tag Not Found"))); // Check tag is present
    }

    // Post Mapping - Create Tag
    @Override
    public TagResponse createTag(@Valid CreateTagRequest createTagRequest) {
        Tag tag = tagMapper.toTag(createTagRequest);
        // Check tag is not null
        if (tag == null) {
            throw new BadRequestException("Bad Request");
        }
      
        return tagMapper.toTagResponse(tagRepository.save(tag));       
    }

    // Put Mapping - Update Tag
    @Override
    public void updateTag(UUID tagId, @Valid UpdateTagRequest updateTagRequest) {
        Tag tag = tagRepository.findById(tagId)
            .orElseThrow(
                ()-> new ResourceNotFoundException("Tag Not Found."));
        
        tagMapper.updateTag(updateTagRequest, tag);
        tagRepository.save(tag);
    }

    // Delete Mapping -
    @Override
    public void deleteTag(UUID tagId) {
        if (tagRepository.existsById(tagId)) {
            System.out.println("TAG ID: " + tagId);
            tagRepository.deleteById(tagId);
        } else {
            throw new ResourceNotFoundException("Tag Not Found");  // Check tag is present
        }
    }

    @Override
    public Page<TagResponse> getAllTagsByName(String search, UUID branchId, Pageable pageable) {
        return  tagRepository.findByName(search.toLowerCase(),branchId, pageable).map(tag->tagMapper.toTagResponse(tag));
    }

    @Override
    public List<TagResponse> getAllTagsByBranchId(UUID branchId) {
        System.out.println("BranchID: " + branchId);
        return tagRepository.findAllTagsByBranchID(branchId).stream().map(tag->tagMapper.toTagResponse(tag)).toList();
    }


}
