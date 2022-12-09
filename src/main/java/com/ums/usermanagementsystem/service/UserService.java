package com.ums.usermanagementsystem.service;

import java.util.List;

import com.ums.usermanagementsystem.entity.reponse.BaseResponse;
import com.ums.usermanagementsystem.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.Errors;

public interface UserService {

	BaseResponse save(UserVo userVo, Errors errors) ;
	BaseResponse update(Integer id, UserVo userVo);
	BaseResponse delete(Integer id);
	UserVo get(Integer id);
	List<UserVo> getAll();

	Page<UserVo> getAllPagable(PageRequest pageable, String name);

}

