package com.example.addressbook.dao;

import com.example.addressbook.entity.Params;
import com.example.addressbook.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdminDao extends Mapper<Admin> {
    @Select("select * from admin where name = #{name} limit 1")
    Admin findByName(@Param("name") String name);
    List<Admin> findBySearch(@Param("params") Params params);
    List<Admin> find(@Param("params") Params params);
}