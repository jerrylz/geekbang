package com.mophn.dao;

import com.mophn.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    List<Map<String, String>> getDataList();

    List<UserEntity> getDataList2();

    int updateUser(@Param("age") String age);
    int updatePro(@Param("prov") String prov);
}
