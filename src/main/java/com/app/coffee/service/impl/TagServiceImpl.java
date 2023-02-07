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
    public Page<TagResponse> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable)
        .map(tag->tagMapper.toTagResponse(tag));
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
         // Check name is unique
        if (tagRepository.existsByName(createTagRequest.getName())) {
            throw new ConflictException("Already Name Exists.");
        }
        return tagMapper.toTagResponse(tagRepository.save(tag));       
    }

    // Put Mapping - Update Tag
    @Override
    public void updateTag(UUID tagId, @Valid UpdateTagRequest updateTagRequest) {
        Tag tag = tagRepository.findById(tagId)
            .orElseThrow(
                ()-> new ResourceNotFoundException("Tag Not Found."));
          // Check name is unique
          if (!updateTagRequest.getName().equals(tag.getName())) { // compare request name and repository name
            if (tagRepository.existsByName(updateTagRequest.getName())) {
                throw new ConflictException("Already Name Exists.");
            }
        }
        tagMapper.updateTag(updateTagRequest, tag);
        tagRepository.save(tag);
    }

    // Delete Mapping -
    @Override
    public void deleteTag(UUID tagId) {
        if (tagRepository.existsById(tagId)) {
            tagRepository.deleteById(tagId);
        } else {
            throw new ResourceNotFoundException("Tag Not Found");  // Check tag is present
        }
    }

    @Override
    public Page<TagResponse> getAllTagsByName(String search, Pageable pageable) {
        return  tagRepository.findByName(search.toLowerCase(),pageable).map(tag->tagMapper.toTagResponse(tag));
    }


}
