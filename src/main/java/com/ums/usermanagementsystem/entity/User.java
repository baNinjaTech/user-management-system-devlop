package com.ums.usermanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="users")
public class User {

	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId ;

	@Column(name="user_first_name")
	private String userFirst_name ;

	@Column(name="user_last_name")
	private String userLast_name ;

	@Column(name="gender")
	private String gender ;

	@Column(name="cpfCnpj")
	private String cpfCnpj ;

	@Column(name="type")
	private String type ;

	@Column(name="email")
	private String email ;

	@Column(name="mobile_no")
	private String mobileNo ;

	@Column(name="created_date")
	private LocalDateTime createdDate ;

	@Column(name="updated_date")
	private LocalDateTime updatedDate ;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="user_id",referencedColumnName = "user_id")
    private List<Address> addresses;

	@Version
	private Long version;

}