package com.javateam.memberProject.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateDTO extends MemberDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 회원 패쓰워드(수정) */
	private String password1;
	
	/** 회원 패쓰워드(확인) */
	private String password2;

	public MemberUpdateDTO(MemberDTO memberDTO) {

		this.setId(memberDTO.getId());
		this.setPassword(memberDTO.getPassword());
		this.setName(memberDTO.getName());
		this.setGender(memberDTO.getGender());
		this.setEmail(memberDTO.getEmail());
		this.setMobile(memberDTO.getMobile());
		this.setPhone(memberDTO.getPhone());
		this.setZip(memberDTO.getZip());
		this.setAddress1(memberDTO.getAddress1());
		this.setAddress2(memberDTO.getAddress2());
		this.setBirthday(memberDTO.getBirthday());
		this.setJoindate(memberDTO.getJoindate());
		this.setEnabled(memberDTO.getEnabled());
	}

	@Override
	public String toString() {
		return "MemberUpdateDTO [password1=" + password1 + ", password2=" + password2 + ", getId()=" + getId()
				+ ", getPassword()=" + getPassword() + ", getName()=" + getName() + ", getGender()=" + getGender()
				+ ", getEmail()=" + getEmail() + ", getMobile()=" + getMobile() + ", getPhone()=" + getPhone()
				+ ", getZip()=" + getZip() + ", getAddress1()=" + getAddress1() + ", getAddress2()=" + getAddress2()
				+ ", getBirthday()=" + getBirthday() + ", getJoindate()=" + getJoindate() + ", getEnabled()="
				+ getEnabled() + "]";
	}

}
