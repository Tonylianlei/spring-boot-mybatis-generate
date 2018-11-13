package com.example.demo.bean.mapper;

import com.example.demo.bean.model.DgUser;
import com.example.demo.bean.model.DgUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DgUserMapper {
    long countByExample(DgUserExample example);

    int deleteByExample(DgUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(DgUser record);

    int insertSelective(DgUser record);

    List<DgUser> selectByExample(DgUserExample example);

    DgUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DgUser record, @Param("example") DgUserExample example);

    int updateByExample(@Param("record") DgUser record, @Param("example") DgUserExample example);

    int updateByPrimaryKeySelective(DgUser record);

    int updateByPrimaryKey(DgUser record);
}