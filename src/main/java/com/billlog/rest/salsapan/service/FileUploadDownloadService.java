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

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
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
    public String storeFile(MultipartFile file, String dir) {
        String saveFileName = "";
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // 파일명에 부적합 문자가 있는지 확인한다.
            if(fileName.contains(".."))
                throw new CFileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
            saveFileName = setRandomFileName(fileName, dir);

            Path targetLocation = this.fileLocation.resolve(dir);

            //디렉토리 존재 유무 체크
            if(!checkPath(targetLocation)){ //해당 dir이 없을 경우 디렉토리 생성
                makeDir(targetLocation);
            }

            targetLocation = this.fileLocation.resolve(dir+"/"+saveFileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return saveFileName;
        }catch(Exception e) {
            throw new CFileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
    }

    //파일명 중복을 막기위해 파일명을 랜덤하게 변경한다.
    public String setRandomFileName(String filename, String dir) {
        try {
            LocalDateTime now = LocalDateTime.now();
            int year = now.getYear();
            int month = now.getMonthValue();
            int day = now.getDayOfMonth();
            int hour = now.getHour();
            int minute = now.getMinute();
            int second = now.getSecond();
            int millis = now.get(ChronoField.MILLI_OF_SECOND); // Note: no direct getter available.
            dir = dir.toUpperCase();
            String randomFilename = dir + "_" + year + "" + month + "" + day + "" + hour + "" + minute + "" + second + "" + millis + "_" + filename;

            return randomFilename;
        }catch(Exception e) {
            throw new CFileUploadException("파일명 랜덤값 셋팅에 실패하였습니다. 다시 시도하십시오.",e);
        }
    }

    //파일 다운로드
    public Resource loadFileAsResource(String fileName) {
        try {

            String dir = fileName.split("_")[0]+"/"; // 파일명에 붙어있는 dir 경로를 추출하기 위해서
            Path filePath = this.fileLocation.resolve(dir + fileName).normalize();
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


    public boolean checkPath(Path pathName){
        File file = new File(pathName.toString());

        boolean chkVal = file.exists();

        return chkVal;
    }

    public void makeDir(Path pathName){
        File file = new File(pathName.toString());
        System.out.println("Make " + file.getAbsolutePath() + " -> " + file.mkdir());
        System.out.println();
    }

}