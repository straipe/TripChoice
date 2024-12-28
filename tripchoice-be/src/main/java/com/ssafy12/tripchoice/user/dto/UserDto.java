package com.ssafy12.tripchoice.user.dto;

import com.ssafy12.tripchoice.auth.UserRole;
import lombok.Builder;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Builder
public record UserDto(String id, String email, String name, String role, String image) {
    public UserDto {
        checkNotNull(id);
        checkArgument(isNotEmpty(email));
        checkArgument(isNotEmpty(name));
        checkArgument(isNotEmpty(role));
    }
}
