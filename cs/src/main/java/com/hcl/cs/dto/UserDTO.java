package com.hcl.cs.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

	private Long userId;
	@NotEmpty(message = "Username should not be empty")

	private String userName;
	@NotEmpty(message = "Password should not be empty")

	private String userPassword;
	@NotEmpty(message = "password should not be empty")
	private String confirmPassword;

}
