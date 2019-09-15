package com.lz.cloud.organization.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.cloud.organization.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
}