package com.javateam.memberProject.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.javateam.memberProject.domain.UploadFile;
import com.javateam.memberProject.service.ImageService;
import com.javateam.memberProject.util.MediaUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("board")
@Slf4j
public class BoardImageRestController {
	
	@Autowired
    ImageService imageService;
	
	@Autowired
	ServletContext servletContext; 
	// 참고) jsp/servlet 에서는 application scope 객체의 원형
	// thymeleaf 역시 servlet이기 때문에 마찬가지로 
	// 웹 프로젝트에서 가장 광범위한 scope의 application 내장 객체의 원형
    
	/**
	 * 이미지 파일 로딩(읽어오기)
	 *  
	 * @param fileId 파일 ID
	 * @return
	 */
    @GetMapping("/image/{fileId}")
    public ResponseEntity<?> serveFile(@PathVariable int fileId) {
    	
        try {
        	
            UploadFile uploadedFile = imageService.load(fileId);
            HttpHeaders headers = new HttpHeaders();
            
            String fileName = uploadedFile.getFileName();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" 
            		+ new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

            if (MediaUtil.containsImageMediaType(uploadedFile.getContentType())) {
                
            	headers.setContentType(MediaType.valueOf(uploadedFile.getContentType()));
                
            } else {
            	
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            } //

            Resource resource = imageService.loadAsResource(uploadedFile.getSaveFileName());
            return ResponseEntity.ok().headers(headers).body(resource);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        
    }
    
    /**
     * 이미지 파일 저장
     * 
     * @param file 이미지 파일
     * @return
     */
    @PostMapping("/image")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
    	
    	log.info("summernote 이미지 파일 업로드 처리");
    	
        try {
            
        	UploadFile uploadedFile = imageService.store(file);
            return ResponseEntity.ok().body(servletContext.getContextPath() 
            							+ "/board/image/" + uploadedFile.getId());
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } //
        
    }

}