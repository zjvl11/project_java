package com.javateam.memberProject.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileUploadService {
	
	// 파일 업로드 경로	
	private final Path uploadPath;
	
	// 10.20
	private final int uploadFileMaxSize;
	
	// spring.servlet.multipart.max-file-size
	
	// application.properties 파일 경로 정보
    // fileupload.path=C:/lsh/project/upload/ 등록/읽어오기
	@Autowired
	// public FileUploadService(@Value("${fileupload.path}") String uploadPath) {
	public FileUploadService(@Value("${fileupload.path}") String uploadPath,
			@Value("${spring.servlet.multipart.max-file-size}") String uploadFileMaxSize) {
		
		log.info("PATH :: " + uploadPath);
		this.uploadPath = Paths.get(uploadPath);
		// 10M => 10 * 1024 * 1024
		
		int maxSize = 0;
		
		// MB => 숫자 치환		
		if (uploadFileMaxSize.contains("MB")) {
			
			String tempStr = uploadFileMaxSize.substring(0, uploadFileMaxSize.indexOf("MB"));
			maxSize = Integer.parseInt(tempStr) * (int)Math.pow(2, 10) * (int)Math.pow(2, 10);	
		}
		
		this.uploadFileMaxSize = maxSize; // 10.20
	}
	
	/**
	 * 파일 업로드 서비스
	 * 
	 * @param boardNum 게시글 번호
	 * @param file 업로드할 파일
	 * @param encodingFilename 업로드될 실제 파일명(암호화 처리)
	 * @return 업로드 결과 메시지
	 */
	public String storeUploadFile(int boardNum, MultipartFile file, String encodingFilename) {
		
		log.info("FileUploadService.storeUploadFile");
		
		String result = "";
		FileOutputStream fos = null;
		
	 	log.info("##### 자원 경로 : " + uploadPath.toString());
	 	
		// 업로드 파일 처리
	 	// 첨부 파일이 없을 때
		if (file.isEmpty() || file == null) {
			
			result = "첨부 파일이 없습니다.";
			log.error(result);
		
		} else { // 파일 유효성 점검
			
			// 저장 폴더 존재 점검
			if (Files.exists(uploadPath)) {
				
				log.info("파일 업로드 저장소(폴더)가 존재합니다.");
				 
			} else {
				
				result = "파일 업로드 저장소(폴더)가 존재하지 않습니다.";
				log.error(result);
				 
				// 폴더 생성
				try {
					Files.createDirectory(uploadPath);	
				} catch (Exception e) {
					result = "파일 업로드 저장소(폴더)가 생성되지 않았습니다.";
					log.error(result);
				}
			} //
		   
		    log.info("###### 게시글 번호 : {}", boardNum);
		     
		    try {
	    	 	// 업로드 파일 형식 변환(시작) : 추가
				log.info("실제 업로드 파일명 : {}", encodingFilename);
				// 업로드 파일 형식 변환(끝) : 추가
	    	 
	    	 	byte[] bytes = file.getBytes();
	    	 	
	    	 	log.info("### uploadPath : {}", uploadPath.toString());
	    	 	
	            File outFileName = new File(uploadPath.toString() + "/" + encodingFilename);
	            
	            // 10.20
	            // TODO
	            // 파일 업로드량 초과 점검
 	            // file.getSize() 비교
	            // 
//	            log.info("업로드 파일 크기 : {}", file.getSize());
//	            log.info("업로드 파일 최대 허용 크기(max quarter) : {}", uploadFileMaxSize);
//	            
//	            if (file.getSize() > uploadFileMaxSize) {
//	            	
//	            	result = "파일은 " + uploadFileMaxSize + "를 넘을 수 없습니다";
//	            	throw new Exception(result);
//	            }
	            
	        	// case-1
	            fos = new FileOutputStream(outFileName);
	            fos.write(bytes);
	            
	            // case-2
	            // FileCopyUtils.copy(file.getBytes(), outFileName);
	            
	            result = "파일이 업로드 되었습니다.";	
		         
		    } catch (IOException e) {
		    	 
		    	result = "파일 처리중 오류가 발생하였습니다. ";
		        log.error(result);
		        e.printStackTrace();
		        
		    } catch (Exception e) { // 10.20
		    	 
		        log.error(result);
		        e.printStackTrace();    
		         		        		 
		    } finally { // 자원 반납
		           
				try {    
				    if (fos!=null) fos.close();
				       
				} catch (IOException e) {
					result = "파일 처리중 오류가 발생하였습니다. ";
					log.error("FileUploadService storeUploadFile IOE : " + result);
				    e.printStackTrace();
				}
				 
		    } // try
		     
		} // 업로드 파일 처리
		
		return result;
	} //
	
	// 10.25
	/**
	 * 파일 삭제 서비스
	 * 
	 * @param file 삭제할 업로드 파일명(인코딩된 파일명)
	 * @return 삭제 결과 메시지
	 */
	public String deleteUploadFile(String encodingFilename) {
		
		log.info("FileUploadService.deleteUploadFile");
		String msg = ""; // 메시지
		
	 	try {
			
	 		Files.deleteIfExists(Paths.get(uploadPath + "/" + encodingFilename));
	 		msg = "파일 삭제에 성공하였습니다.";
			
		} catch (IOException e) {
			log.error("업로드 파일 삭제 에러 : " + e);
			msg = "파일 삭제에 실패하였습니다.";
			e.printStackTrace();
		}
		
		return msg;
	} //
	
	/**
	 * 삽입 이미지 파일 삭제 서비스
	 *
	 * @param encodingFilename 업로드된 파일 경로 + 파일명  ex) C:/lsh/works/project/upload/image/2023/10/20/a92e1a28f7e746b39afe7e83eb97a5d2.jpg
	 * @return 삭제 결과 메시지
	 */
	public String deleteImageFile(String encodingFilename) {
		
		log.info("FileUploadService.deleteImageFile");
		String msg = ""; // 메시지
		
	 	try {
			
	 		if (Files.deleteIfExists(Paths.get(encodingFilename)) == true) {
	 			msg = "파일 삭제에 성공하였습니다.";
	 		} else {
	 			msg = "파일 삭제에 실패하였습니다.";
	 		}
			
		} catch (IOException e) {
			log.error("업로드 파일 삭제 에러 : " + e);
			msg = "파일 삭제에 실패하였습니다.";
			e.printStackTrace();
		}
		
		return msg;
	}
	

}