package com.billlog.rest.salsapan.controller.common;

import com.billlog.rest.salsapan.mapper.FileMapper;
import com.billlog.rest.salsapan.model.file.FileUploadProperties;
import com.billlog.rest.salsapan.model.file.FileUploadResponse;
import com.billlog.rest.salsapan.model.response.ListResult;
import com.billlog.rest.salsapan.model.response.SingleResult;
import com.billlog.rest.salsapan.service.FileUploadDownloadService;
import com.billlog.rest.salsapan.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Api(tags = {"9 - 2. File"})
@RestController
@RequestMapping(value = "/v1/file")
public class FileUploadController {
    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileUploadDownloadService service;
    private final ResponseService responseService;
    private final FileUploadProperties fileUploadProperties;

    public FileUploadController(ResponseService responseService, FileUploadProperties fileUploadProperties) {
        this.responseService = responseService;
        this.fileUploadProperties = fileUploadProperties;
    }

    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "Content-Type", value = "폼데이터 속성", required = true, dataType = "String", paramType = "header", defaultValue = "multipart/form-data")
    })
    @PostMapping("/uploadFile")
    public SingleResult<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file, int inputBoardIdx) {

        String filename = service.storeFile(file); //파일명 중복을 막기 위해 storeFile() 에서 UUID를 이용해서 파일명을 변경한다.
        String org_filename = file.getOriginalFilename();

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/file")
                .path("/downloadFile/")
                .path(filename)
                .toUriString();

        FileUploadResponse fp = new FileUploadResponse(filename, org_filename,fileDownloadUri, file.getContentType(), file.getSize());
        fp.setFile_path(fileUploadProperties.getUploadDir()); // application.properties 의 file-upload-dir 의 경로 인 ./upload를 가져온다.
        fp.setUse_yn("Y");  //사용여부는 최초 등록시에는 무조건 Y
        fp.setType("C");    //해당하는 타입의 게시판 정보
        fp.setBoard_idx(inputBoardIdx); //해당 타입의 게시판의 글번호


        int resultCheck = fileMapper.insertFiletoDb(fp);

        if(resultCheck > 0) {
            return responseService.getSingleResult(fp);
        }else{
            return (SingleResult<FileUploadResponse>) responseService.getFailResult(1008,"파일업로드 및 데이터 입력중 에러가 발생하였습니다.");
        }
    }


    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "Content-Type", value = "폼데이터 속성", required = true, dataType = "String", paramType = "header", defaultValue = "multipart/form-data")
    })
    @PostMapping("/uploadMultipleFiles")
    public ListResult<SingleResult<FileUploadResponse>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
                                                                            int inputBoardIdx){

//        return responseService.getListResult(Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList()));
        return responseService.getListResult(Arrays.asList(files).stream().map(file -> uploadFile(file,inputBoardIdx)).collect(Collectors.toList()));

    }

    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        // Load file as Resource
        Resource resource = service.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.err.println("Could not determine file type >>> " + ex.getMessage());
            //logger.info("Could not determine file type.");

        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
