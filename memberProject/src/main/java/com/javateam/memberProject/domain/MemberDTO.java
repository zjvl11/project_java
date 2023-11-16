package com.javateam.memberProject.domain;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j // 10.16
public class MemberDTO {
	
	/** 회원 아이디 */
	private String id;
	
	/** 회원 패쓰워드 */
	private String password;
	
	/** 회원 이름 */
	private String name;
	
	/** 회원 성별 */
	private String gender;
	
	/** 회원 이메일 */
	private String email;
	
	/** 회원 연락처(휴대폰) */
	private String mobile;
	
	/** 회원 연락처(지역전화) */
	private String phone;
	
	/** 회원 우편번호 */
	private String zip;
	
	/** 회원 도로명 주소 */
	private String address1;
	
	/** 회원 상세 주소 */
	private String address2;
	
	/** 회원 생일 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	
	/** 회원 가입일 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date joindate;
	
	/** 회원 활성화 여부 */
	private int enabled;
	
	
	
	// DTO => MultiValueMap
	public static MultiValueMap<String, String> toMap(MemberDTO memberDTO) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		Field[] fields = memberDTO.getClass().getDeclaredFields();
				
		for (Field fld : fields) {
			
			Method method = memberDTO.getClass().getDeclaredMethod("get"+StringUtils.capitalize(fld.getName()));
			
			if (fld.getName().equals("birthday") || fld.getName().equals("joindate")) {
			
				map.put(fld.getName(), Arrays.asList(new SimpleDateFormat("yyyy-MM-dd").format(method.invoke(memberDTO))));
				
			} else {
				map.put(fld.getName(), Arrays.asList(method.invoke(memberDTO).toString()));
			} //
		}
		
		return map;		
	}


	// 10.16
	public MemberDTO(Map<String, Object> requestMap) {
		
		Set<String> set = requestMap.keySet();
		Iterator<String> it = set.iterator();
		Field field; // reflection 정보 활용
		
		while (it.hasNext()) {
			
			 String fldName = it.next();
		
			 try {
		    		// DTO와 1:1 대응되는 필드들 처리 
			    	try {
							field = this.getClass().getDeclaredField(fldName);
							field.setAccessible(true);
							
							if (!fldName.equals("birthday") || !fldName.equals("joindate")) {
								field.set(this, requestMap.get(fldName));
							}
							
					} catch (NoSuchFieldException e) {
						
						// 만약 VO와 1:1 대응되지 않는 인자일 경우는 이 부분에서 입력처리합니다.
						log.info("인자와 필드가 일치하지 않습니다."); 
						
					} // try
					
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) { 
				e.printStackTrace();
			} // try
			 
		} // while	 
		
	} //
	
}	
