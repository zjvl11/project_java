package com.javateam.memberProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javateam.memberProject.domain.UploadFile;

public interface FileDAO extends JpaRepository<UploadFile, Integer> {
	 
	public UploadFile findOneByFileName(String fileName);
	
	public UploadFile findOneById(int id);
	
	// 10.25
	public void deleteById(int id);
	
}