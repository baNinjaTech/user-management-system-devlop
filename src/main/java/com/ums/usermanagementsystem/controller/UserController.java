package com.ums.usermanagementsystem.controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import com.ums.usermanagementsystem.entity.reponse.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.ums.usermanagementsystem.service.UserService;
import com.ums.usermanagementsystem.vo.UserVo;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("users")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<BaseResponse> save(@Valid @RequestBody UserVo userVo, Errors errors) {
       return ResponseEntity.ok(userService.save(userVo,errors));
    }

    @Cacheable("test-endpoint")
    @GetMapping("user/{id}")
    public UserVo get(@PathVariable Integer id) {
        return userService.get(id);
    }

    @GetMapping(value = "/all", headers = {"Accept-version=v1"})
    public List<UserVo> getAll() {
        return userService.getAll();
    }

    //@GetMapping("user/{pageNo}/{pageSize}")
    @GetMapping(value = "/all", headers = {"Accept-version=v2"})
    public Page<UserVo> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "10") int size,
                               @RequestParam(name = "sort", defaultValue = "asc") String sort,
                               @RequestParam(name = "field", defaultValue = "id") String field,
                               @RequestParam(name = "name", required = false) String name) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort, field));
        return userService.getAllPagable(pageable,name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable Integer id, @RequestBody UserVo userVo) {
        BaseResponse response = userService.update(id, userVo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Integer id) {
        BaseResponse baseResponse = userService.delete(id);
        return ResponseEntity.ok(baseResponse);
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public String handleEntityExistsException(EntityExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException e) {
        return e.getMessage();
    }
}
