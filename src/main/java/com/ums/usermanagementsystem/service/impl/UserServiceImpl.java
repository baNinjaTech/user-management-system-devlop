package com.ums.usermanagementsystem.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ums.usermanagementsystem.entity.Address;
import com.ums.usermanagementsystem.entity.reponse.BaseResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ums.usermanagementsystem.dao.UserDao;
import com.ums.usermanagementsystem.entity.User;
import com.ums.usermanagementsystem.service.UserService;
import com.ums.usermanagementsystem.vo.UserVo;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    private RestTemplate restTemplate;

    BaseResponse baseResponse = new BaseResponse();

    @Override
    @Transactional
    public BaseResponse save(UserVo userVo, Errors errors) {

        String msg = "";
        Gson gson = new Gson();
        if (errors.hasErrors()) {
            for (ObjectError error1 : errors.getAllErrors()) {
                msg += ((FieldError) error1).getField() + " " + error1.getDefaultMessage() + ", ";
            }
            baseResponse.setResultMessage(msg);
        } else if (userVo.getAddresses() != null && userVo.getAddresses().size() > 5) {
            baseResponse.setResultMessage("Address should not more then five.");
        } else if (userVo.getAddresses() != null && userVo.getAddresses().size() > 5) {
            baseResponse.setResultMessage("Address should not more then five.");
        } else if (!isValidAddress(userVo.getAddresses())) {
            baseResponse.setResultMessage("Only One address should be primary address.");
        } else {
            User user = new User();
            userVo.setCreatedDate(LocalDateTime.now());
            BeanUtils.copyProperties(userVo, user);
            List<Address> newAddress = new ArrayList<>();
//            final List<Address> addresses = userVo.getAddresses();
//            for (Address address1 : addresses) {
//                Object forEntity = restTemplate.getForEntity("https://viacep.com.br/ws/"+"01001000"+"/json/", Object.class).getBody();
//                JsonArray jsonArray = (JsonArray)(gson.fromJson(gson.toJson(forEntity), JsonArray.class));
//                if (!jsonArray.isJsonNull()){
//                    JsonObject asJsonObject = (JsonObject) jsonArray.get(1);
//                    JsonElement jsonElement = asJsonObject.get("cep");
//                    address1.setZipCode(jsonElement.getAsString());
//                    newAddress.add(address1);
//                }
//            }
            userVo.setAddresses(newAddress);
            userDao.save(user);
            baseResponse.setResultMessage("Data is saved");
        }
        return baseResponse;

    }

    @Override
    @Transactional
    public BaseResponse update(Integer customerId, UserVo userVo) {
        Optional<User> customerOptional = userDao.findById(customerId);

        if (customerOptional.isPresent()) {
            User user = customerOptional.get();

            if (userVo.getUserFirst_name()!= null) {
                user.setUserFirst_name(userVo.getUserFirst_name());
            }

            if (userVo.getUserLast_name()!= null) {
                user.setUserLast_name(userVo.getUserLast_name());
            }

            if (userVo.getMobileNo() != null) {
                user.setMobileNo(userVo.getMobileNo() );
            }

            if (userVo.getCpfCnpj() != null) {
                user.setCpfCnpj(userVo.getCpfCnpj());
            }

            if (userVo.getEmail() != null) {
                user.setEmail(userVo.getEmail());
            }

            if (userVo.getGender() != null) {
                user.setGender(userVo.getGender());
            }

            if (userVo.getType() != null) {
                user.setType(userVo.getType());
            }
            userVo.setUpdatedDate(LocalDateTime.now());
            userDao.save(user);
            baseResponse.setResultMessage("Data Updated successfully");
            return baseResponse;
        } else {
            throw new RuntimeException("Error while update customer");
        }
    }


    @Override
    @Transactional
    public BaseResponse delete(Integer id) {
        userDao.deleteById(id);
        return new BaseResponse("Data is Deleted successfully");

    }

    @Override
    @Transactional
    public UserVo get(Integer id) {
        Optional<User> userOptional = userDao.findById(id);
        UserVo userVo = null;
        if (userOptional.isPresent()) {
            userVo = new UserVo();
            BeanUtils.copyProperties(userOptional.get(), userVo);
        } else {
            throw new EntityNotFoundException();
        }

        return userVo;
    }

    @Override
    @Transactional
    public List<UserVo> getAll() {
        List<User> customerList = userDao.findAll();
        List<UserVo> customerVoList = new ArrayList<>();
        if (customerList != null && !customerList.isEmpty()) {
            for (User customer : customerList) {
                UserVo customerVo = new UserVo();
                BeanUtils.copyProperties(customer, customerVo);
                customerVoList.add(customerVo);
            }
        }
        return customerVoList;
    }

    @Override
    public Page<UserVo> getAllPagable(PageRequest pageable, String name) {
        Page<User> customerList = userDao.findAll(pageable);
        List<UserVo> customerVoList = new ArrayList<>();
        if (customerList != null && !customerList.isEmpty()) {
            for (User customer : customerList) {
                UserVo customerVo = new UserVo ();
                BeanUtils.copyProperties(customer, customerVo);
                customerVoList.add(customerVo);
            }
        }
        Page<UserVo> pages = new PageImpl<>(customerVoList, pageable, customerVoList.size());
        return pages;
    }

    private Boolean isValidAddress(List<Address> addressList) {

        int count = 0;
        for (Address address : addressList) {
            if (addressList.size() == 1) {
                address.setIsPrimaryAddress(true);
                return true;
            }
            if (address.getIsPrimaryAddress() != null && address.getIsPrimaryAddress()) {
                count++;
            }

        }
        if (count == 0 && !addressList.isEmpty()) {
            addressList.get(0).setIsPrimaryAddress(true);
            return true;
        }
        if (count > 1 || count == 0) {
            return false;
        }
        return true;
    }

}

