package com.javateam.memberProject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javateam.memberProject.dao.FileDAO;
import com.javateam.memberProject.domain.UploadFile;
import com.javateam.memberProject.util.FileUploadUtil;

import lombok.extern.slf4j.Slf4j;

// 10.19
@Service
@Slf4j
public class ImageService {
    
    private final Path rootLocation;
    
    // application.properties 파일 경로 정보
    // imageUpload.path=C:/lsh/project/upload/image/ 등록/읽어오기 
    @Autowired
    public ImageService(@Value("${imageUpload.path}") String uploadPath) {
       
    	log.info("PATH :: " + uploadPath);
        this.rootLocation = Paths.get(uploadPath);
    }
    
    @Autowired
    ImageStoreService imageStoreService;
    
    /**
     * 모든 파일 읽어오기
     * 
     * @return 파일들
     */
    public Stream<Integer> loadAll() {
    	
        List<UploadFile> files = imageStoreService.findAll();
        return files.stream().map(file -> file.getId());
    }
    
    /**
     * 파일 읽어오기
     * 
     * @param fileId 파일 ID
     * @return 업로드 파일 객체
     */
    public UploadFile load(int fileId) {
    	
    	return imageStoreService.findOneById(fileId);
    }
    
    /**
     * 파일 자원(resource) 로딩
     * 
     * @param fileName 파일명
     * @return 파일 자원(resource)
     * @throws Exception
     */
    public Resource loadAsResource(String fileName) throws Exception {
       
    	try {
    		
            if (fileName.toCharArray()[0] == '/') {
                fileName = fileName.substring(1);
            }
            
            Path file = loadPath(fileName);
            Resource resource = new UrlResource(file.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new Exception("파일을 읽을 수 없습니다 : " + fileName);
            }
            
        } catch (Exception e) {
            throw new Exception("파일을 읽을 수 없습니다 : " + fileName);
        } //
    	
    }
    
    /**
     * 경로 읽어오기
     *  
     * @param fileName 파일명
     * @return 파일 경로
     */
    private Path loadPath(String fileName) {
        return rootLocation.resolve(fileName);
    }
    
    /**
     * 업로드 파일 저장
     * 
     * @param file 업로드 파일
     * @return 업로드 파일 객체
     * @throws Exception
     */
    public UploadFile store(MultipartFile file) throws Exception {
    	
        try {
            if (file.isEmpty()) {
                throw new Exception("업로드 파일이 비어 있어서 저장에 실패하였습니다 : " + file.getOriginalFilename());
            }
            
            String saveFileName = FileUploadUtil.fileSave(rootLocation.toString(), file);
            
            if (saveFileName.toCharArray()[0] == '/') {
                saveFileName = saveFileName.substring(1);
            }
            
            Resource resource = loadAsResource(saveFileName);
            
            // 업로드 파일 객체 구성
            UploadFile saveFile = new UploadFile();
            saveFile.setSaveFileName(saveFileName);
            saveFile.setFileName(file.getOriginalFilename());
            saveFile.setContentType(file.getContentType());
            
            log.info("Root 경로 : {}", rootLocation);
            
            String tempPath = rootLocation.toString()
            		.replace(File.separatorChar, '/') + File.separator + saveFileName;
            saveFile.setFilePath(tempPath.replace("\\", "/"));
            
            log.info("이미지 파일 저장경로 : {}", saveFile.getFilePath());
            
            saveFile.setFileSize(resource.contentLength());
            saveFile.setRegDate(new Date());
            saveFile = imageStoreService.save(saveFile); // 저장
            
            return saveFile;
            
        } catch (IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        }
        
    } //
    
}