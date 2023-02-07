package com.app.coffee.service;

import com.app.coffee.payload.response.UserResponse;

public interface AuthService {
    UserResponse getInformationUserFromEmail(String email);
}
