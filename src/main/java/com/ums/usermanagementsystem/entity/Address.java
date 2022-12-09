package com.ums.usermanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="address")
public class Address {

	@Id
	@Column(name="address_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer addressId ;

	@Column(name="number")
	private String number ;

	@Column(name="street")
	private String street ;

	@Column(name="neighborhood")
	private String neighborhood ;

	@Column(name="city")
	private String city ;

	@Column(name="zip_code")
	private String zipCode ;

	@Column(name="state")
	private String state ;

	@Column(name="isPrimaryAddress")
	private Boolean isPrimaryAddress = false;

	@Version
	private Long version;
}