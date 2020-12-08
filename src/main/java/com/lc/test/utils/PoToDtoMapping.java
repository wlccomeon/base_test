package com.lc.test.utils;

import com.lc.test.entity.User;
import com.lc.test.entity.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author wlc
 * @description
 * @date 2020/11/18 0018 14:36
 */
@Mapper
public interface PoToDtoMapping {
    /**
     * 转换器实例
     */
    PoToDtoMapping INSTANCE = Mappers.getMapper(PoToDtoMapping.class);
    /**
     * 将User转换为UserDTO
     * @param user
     * @return
     */
    UserDTO copyUserToUserDTO(User user);
}
