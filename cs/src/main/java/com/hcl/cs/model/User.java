package com.hcl.cs.model;

import java.util.ArrayList;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import javax.persistence.OneToMany;



import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USERID")
	private Long userId;
    @NotEmpty(message = "Username should not be empty")
    @Column(name="USERNAME")
	private String userName;
    @NotEmpty(message = "Password should not be empty")
    @Column(name="USERPASSWORD")
	private String userPassword;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Pet> pets = new ArrayList <>(); 
	
	 
}
