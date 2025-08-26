package com.app.resourceforge.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.app.resourceforge.audit.Auditable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User extends Auditable<String>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private String username;
	
	@JsonIgnore
	private String password;

	private String role;
	
	@NotNull
	private String name;
	
	@NotNull
	private String email;
	
	@NotNull
	private String mobile;
	
	private Integer superCompanyId;

	private Integer coId;

	private boolean enabled;
	
	private boolean accountNotExpired;

	private String otp;
	private String imageLink;

	private LocalDateTime otpExprTime;


	public User(@NotNull String username, @NotNull String password, String role, @NotNull String name,
			@NotNull String email, @NotNull String mobile,Integer superCompanyId, Integer coId, boolean enabled, boolean accountNotExpired) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.superCompanyId = superCompanyId;
		this.coId = coId;
		this.enabled = enabled;
		this.accountNotExpired = accountNotExpired;
	}

	@Transient
	private String companyName;

	

}
