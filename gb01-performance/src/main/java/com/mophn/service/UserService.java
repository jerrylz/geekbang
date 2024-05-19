package com.mophn.service;

import com.mophn.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;


    public List<Map<String, String>> getDataList() {
        return userDAO.getDataList();
    }
}
