package com.example.demo.bean.mapper;

import com.example.demo.bean.model.DgUserInfo;
import com.example.demo.bean.model.DgUserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DgUserInfoMapper {
    long countByExample(DgUserInfoExample example);

    int deleteByExample(DgUserInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(DgUserInfo record);

    int insertSelective(DgUserInfo record);

    List<DgUserInfo> selectByExample(DgUserInfoExample example);

    DgUserInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DgUserInfo record, @Param("example") DgUserInfoExample example);

    int updateByExample(@Param("record") DgUserInfo record, @Param("example") DgUserInfoExample example);

    int updateByPrimaryKeySelective(DgUserInfo record);

    int updateByPrimaryKey(DgUserInfo record);
}