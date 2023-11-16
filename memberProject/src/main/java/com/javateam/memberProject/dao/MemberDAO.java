package com.javateam.memberProject.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javateam.memberProject.domain.MemberDTO;
import com.javateam.memberProject.domain.MemberUpdateDTO;
import com.javateam.memberProject.domain.Role;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MemberDAO {

	@Autowired
	SqlSession sqlSession;
	
	public void insertMember(MemberDTO memberDTO) {
		sqlSession.insert("mapper.Member.insertMember", memberDTO);
	}
	
	public void insertRole(Role role) {
		sqlSession.insert("mapper.Member.insertRole", role);
	}
	
	public boolean hasFld(String fld, String val) {
		
		Map<String, String> map = new HashMap<>();
		map.put("fld", fld);
		map.put("val", val);
		
		return (int)sqlSession.selectOne("mapper.Member.hasFld", map) == 1 ? true : false;
	} //
	
	public MemberDTO selectMember(String id) {
		
		return sqlSession.selectOne("mapper.Member.selectMember", id); 
	}
	
	// 10.6
	public boolean hasFldForUpdate(String id, String fld, String val) {
		
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("fld", fld);
		map.put("val", val);
		
		return (int)sqlSession.selectOne("mapper.Member.hasFldForUpdate", map) == 1 ? true : false;
	} //
	
	// 10.6
	public void updateMember(MemberDTO memberDTO) {
		sqlSession.insert("mapper.Member.updateMember", memberDTO);
	}
	
	// 10.11
	public int selectMembersCount() {
		return sqlSession.selectOne("mapper.Member.selectMembersCount");
	}
	
	// 10.11
	public List<MemberDTO> selectMembersByPaging(int page, int limit) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("page",  page);
		map.put("limit", limit);
		
		return sqlSession.selectList("mapper.Member.selectMembersByPaging", map);
	}
	
	// 10.12
	public List<Map<String, Object>> selectMembersWithRolesByPaging(int page, int limit) {
	
		Map<String, Integer> map = new HashMap<>();
		map.put("page",  page);
		map.put("limit", limit);
		
		return sqlSession.selectList("mapper.Member.selectMembersWithRolesByPaging", map);
	} //

	// 10.12
	public List<String> selectRolesById(String id) {
		
		return sqlSession.selectList("mapper.Member.selectRolesById", id);
	}
	
	// 10.12
	public void deleteRoleById(String id, String role) {
		
		Map<String, String> map = new HashMap<>();
		map.put("id",  id);
		map.put("role", role);
		
		sqlSession.delete("mapper.Member.deleteRoleById", map);
	}
	
	// 10.13
	public List<Map<String, Object>> selectMembersWithRolesBySearching(int page, int limit, String searchKey, String searchWord) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("page",  page);
		map.put("limit", limit);
		map.put("searchKey", searchKey);
		map.put("searchWord", searchWord);
		
		return sqlSession.selectList("mapper.Member.selectMembersWithRolesBySearching", map);
	} //
	
	// 10.13
	public int selectMembersCountBySearching(String searchKey, String searchWord) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("searchKey", searchKey);
		map.put("searchWord", searchWord);
		
		return (int)sqlSession.selectOne("mapper.Member.selectMembersCountBySearching", map);
	}
	
	// 10.13
	public void changeEnabled(String id, int enabled) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("enabled", enabled);
		
		log.info("상태 정보 : {}", enabled);
		
		sqlSession.update("mapper.Member.changeEnabled", map);
	}

	// 10.16
	public void deleteRolesById(String id) {
		
		sqlSession.delete("mapper.Member.deleteRolesById", id);
	}
	
	// 10.16
	public void deleteMemberById(String id) {
		
		sqlSession.delete("mapper.Member.deleteMemberById", id);
	}
	
}