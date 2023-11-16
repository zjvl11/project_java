package com.javateam.memberProject.domain;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
		
    /* Spring Security related fields */
    private List<Role> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
	
    public CustomUser(Users users) {
		this.username = users.getId();
		this.password = users.getPw();
		this.enabled = users.getEnabled()==1 ?  true : false;
	}

    // 추가
 	public CustomUser(String username, String password, boolean enabled) {
 		this.username = username;
 		this.password = password;
 		this.enabled = enabled;
 	}

}
