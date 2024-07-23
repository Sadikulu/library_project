package com.lib.mapper;

import com.lib.domain.User;
import com.lib.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);
}
