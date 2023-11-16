package com.javateam.memberProject.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUploadUtil {
	
    /**
     * @param filePath
     * @param multipartFile
     * @return 생성된 파일명(유일 값)
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String fileSave(String uploadPath, MultipartFile file) throws IllegalStateException, IOException {
        
        File uploadPathDir = new File(uploadPath);
        
        if ( !uploadPathDir.exists() ){
            uploadPathDir.mkdirs();
        }
        
        // 파일 중복명 처리
        String genId = UUID.randomUUID().toString();
        genId = genId.replace("-", "");
        
        String originalfileName = file.getOriginalFilename();
        String fileExtension = getExtension(originalfileName);
        String saveFileName = genId + "." + fileExtension;
        
        String savePath = calcPath(uploadPath);
        
        File target = new File(uploadPath + savePath, saveFileName);
        
        FileCopyUtils.copy(file.getBytes(), target);
        
        return makeFilePath(uploadPath, savePath, saveFileName);
    }
    
    /**
     * 파일명으로부터 확장자 추출(반환)
     * 
     * @param fileName 확장자 포함한 파일명
     * @return 파일명에서 확장자 이름만 반환
     */
    public static String getExtension(String fileName) {
        int dotPosition = fileName.lastIndexOf('.');
        
        if (-1 != dotPosition && fileName.length() - 1 > dotPosition) {
            return fileName.substring(dotPosition + 1);
        } else {
            return "";
        }
    }
    
    /**
     * 이미지 경로(Path) 구성
     * 
     * @param uploadPath 업로드 경로
     * @return
     */
    private static String calcPath(String uploadPath) {
        
        Calendar cal = Calendar.getInstance();
        
        String yearPath = File.separator + cal.get(Calendar.YEAR);
        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
        String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
        
        makeDir(uploadPath, yearPath, monthPath, datePath);
        
        log.info(datePath);
        
        return datePath;
    }
    
    /**
     * 디렉토리(폴더) 구성
     * 
     * @param uploadPath 업로드 경로
     * @param paths
     */
    private static void makeDir(String uploadPath, String... paths) {
        
        log.info(paths[paths.length - 1] + " : " + new File(paths[paths.length - 1]).exists());
        
        if (new File(paths[paths.length - 1]).exists()) {
            return;
        }
        
        for (String path : paths) {
            File dirPath = new File(uploadPath + path);
            
            if (!dirPath.exists()) {
                dirPath.mkdir();
            }
        } // for
    }
    
    /**
     * 파일 경로 생성
     * 
     * @param uploadPath 업로드 경로
     * @param path 경로
     * @param fileName 파일명
     * @return 파일 경로
     * @throws IOException
     */
    private static String makeFilePath(String uploadPath, String path, String fileName) throws IOException {
       
    	String filePath = uploadPath + path + File.separator + fileName;
        return filePath.substring(uploadPath.length()).replace(File.separatorChar, '/');
    }
    
    /**
     * 썸네일 이미지 작성 
     * 
     * 주의) 참고로 현재 내부에서 사용하고 있지 않지만 본 이미지에 대한 썸네일 이미지 작성시 활용할 수 있습니다. 
     * 
     * @param uploadPath 업로드 경로
     * @param path 경로
     * @param fileName 파일명
     * @return 썸네일 경로
     * @throws Exception
     */
    private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception {
        
        BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
        
        BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
        
        String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;
        
        File newFile = new File(thumbnailName);
        String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
        
        ImageIO.write(destImg, formatName.toUpperCase(), newFile);
        
        return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
    } //
    
    // 10.20 
	public static String encodeFilename(String originalfileName) {
		
		String genId = UUID.randomUUID().toString();
        genId = genId.replace("-", "");
        
        String fileExtension = getExtension(originalfileName);
        String saveFilename = genId + "." + fileExtension;
        
        log.info("실제 저장될 업로드 파일명 : {}", saveFilename);
        
        return saveFilename;
	} //

}