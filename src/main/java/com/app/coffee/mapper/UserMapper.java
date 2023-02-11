package com.app.coffee.mapper;

import com.app.coffee.entity.User;
import com.app.coffee.payload.request.CreateUserRequest;
import com.app.coffee.payload.request.UpdateUserRequest;
import com.app.coffee.payload.response.UserResponse;

public interface UserMapper {
    public UserResponse toUserResponse(User user);
    public User toUser(CreateUserRequest singUpRequest);
    public void updateUser (UpdateUserRequest updateUser, User user);

}
