package com.billlog.rest.salsapan.mapper;

import com.billlog.rest.salsapan.model.file.FileManage;
import com.billlog.rest.salsapan.model.file.FileUploadResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    int insertFileManage(FileManage fileManage);

    int modifyFileManage(FileManage fileManage);

    int insertFiletoDb(FileUploadResponse fileUploadResponse);
}

