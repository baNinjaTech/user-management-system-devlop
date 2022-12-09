package com.ums.usermanagementsystem.vo;


import com.ums.usermanagementsystem.entity.Address;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserVo {

	private Integer userId ;

	@NotBlank(message = "Firstname is Mandatory")
	private String userFirst_name ;
	@NotBlank(message = "Lastname is Mandatory")
	private String userLast_name ;
	@NotBlank(message = "Gender is Mandatory")
	private String gender ;

	@Pattern(regexp = "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})",message = "Invalid cpf or Cnpj")
	private String cpfCnpj ;
	@NotBlank(message = "Type is Mandatory")
	private String type ;

	@Email(message = "Email is invalid")
	private String email ;

	@Pattern(regexp = "^\\d{10}$", message = "Mobile number is invalid.")
	private String mobileNo ;

	private LocalDateTime createdDate ;

	private LocalDateTime updatedDate ;

	private List<Address> addresses;

	public UserVo() {

	}
}