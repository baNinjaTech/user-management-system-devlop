package com.ums.usermanagementsystem.vo;

import com.ums.usermanagementsystem.entity.User;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Pattern;

@Data
public class AddressVo {


	private Integer addressId ;
	@NonNull
	private String number ;
	@NonNull
	private String street ;
	@NonNull
	private String neighborhood ;
	@NonNull
	private String city ;
	@NonNull
	@Pattern(regexp = "^\\\\d{6}$", message = "Enter valid zipcode.")
	private String zipCode ;
	@NonNull
	private String state ;

	private User user;

	private Boolean isPrimaryAddress;
}