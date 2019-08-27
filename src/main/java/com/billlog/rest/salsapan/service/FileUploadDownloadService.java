package com.billlog.rest.salsapan.service;

import com.billlog.rest.salsapan.advice.exception.CFileDownloadException;
import com.billlog.rest.salsapan.advice.exception.CFileUploadException;
import com.billlog.rest.salsapan.model.file.FileUploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadDownloadService {
    private final Path fileLocation;

    @Autowired
    public FileUploadDownloadService(FileUploadProperties prop) {
        this.fileLocation = Paths.get(prop.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception e) {
            throw new CFileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }
    }

    //파일 저장
    public String storeFile(MultipartFile file) {
        String saveFileName = "";
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // 파일명에 부적합 문자가 있는지 확인한다.
            if(fileName.contains(".."))
                throw new CFileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
            saveFileName = setRandomFileName(fileName);
            Path targetLocation = this.fileLocation.resolve(saveFileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return saveFileName;
        }catch(Exception e) {
            throw new CFileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
    }

    //파일명 중복을 막기위해 파일명을 랜덤하게 변경한다.
    public String setRandomFileName(String filename) {
        UUID uuid = UUID.randomUUID();

        try {
            String randomFilename = uuid.toString() + "_" + filename;

            return randomFilename;
        }catch(Exception e) {
            throw new CFileUploadException("파일명 랜덤값 셋팅에 실패하였습니다. 다시 시도하십시오.",e);
        }
    }

    //파일 다운로드
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return resource;
            }else {
                throw new CFileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        }catch(MalformedURLException e) {
            throw new CFileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
        }
    }


}