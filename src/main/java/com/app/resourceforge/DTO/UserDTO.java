package com.app.resourceforge.DTO;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    private Integer id;
    private String username;
    private String password;
    private String role;
    private String name;
    private String email;
    private String mobile;
    private Integer superCompanyId;
    private Integer coId;
    private boolean enabled;
    private boolean accountNotExpired;
    private String otp;
    private LocalDateTime otpExprTime;
    private String imageLink;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

	
}
