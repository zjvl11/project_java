package com.javateam.memberProject.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="upload_file_tbl")
public class UploadFile {
    
    @Id
    // @GeneratedValue
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    			    generator = "UPLOADFILE_SEQ_GENERATOR")
    @SequenceGenerator(
    	    name = "UPLOADFILE_SEQ_GENERATOR",
    	    sequenceName = "image_upload_file_seq",
    	    initialValue = 1,
    	    allocationSize = 1)
    @Column(name = "id")
    int id;

    @Column(name = "filename")
    String fileName;
    
    @Column(name="save_filename")
    String saveFileName;
    
    @Column(name="file_path")
    String filePath;
    
    @Column(name="content_type")
    String contentType;
    
    @Column(name="file_size")
    long fileSize;
    
    @Column(name="regdate")
    Date regDate;
    
}