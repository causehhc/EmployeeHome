package com.csi.emphome.demo.service.user;

import com.csi.emphome.demo.service.user.dto.UserListQuery;
import com.csi.emphome.demo.service.user.dto.UserSearchData;
import com.csi.emphome.demo.service.user.dto.UserTemp;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface UserService {
    HashMap<String, Object> fetchListFunc(UserListQuery data);
    HashMap<String, Object> createListItemFunc(UserTemp data);
    HashMap<String, Object> deleteListItemFunc(List<UserTemp> data);
    HashMap<String, Object> updateListItemFunc(UserTemp data);
    HashMap<String, Object> checkSameNameFunc(UserTemp data);
    HashMap<String, Object> fetchListItemFunc(UserSearchData data);
}
