package com.zhandi.hackernews.dao;

import com.zhandi.hackernews.model.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {
    Admin selectAdmin(@Param("admin") Admin admin);
}
