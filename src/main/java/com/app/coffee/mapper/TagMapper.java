package com.app.coffee.mapper;

import com.app.coffee.entity.Tag;
import com.app.coffee.payload.request.CreateTagRequest;
import com.app.coffee.payload.request.UpdateTagRequest;
import com.app.coffee.payload.response.TagResponse;

public interface TagMapper {
    public TagResponse toTagResponse(Tag tag);
    public Tag toTag(CreateTagRequest createTagRequest);
    public void updateTag (UpdateTagRequest updateTag, Tag tag);
}
