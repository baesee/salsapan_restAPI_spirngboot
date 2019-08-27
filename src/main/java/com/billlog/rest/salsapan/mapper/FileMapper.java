package com.billlog.rest.salsapan.mapper;

import com.billlog.rest.salsapan.model.file.FileUploadResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    //이미지 등록
    /*
    @Insert("INSERT INTO salsa_user(`user_id`, `user_pwd`, `user_name`, `user_city`, `user_gender`, `user_email`, `user_role`, `user_team`) " +
            "VALUES (#{user_id}, #{user_pwd}, #{user_name}, #{user_city}, #{user_gender}, #{user_email}, #{user_role}, #{user_team})")
    */
    @Insert("insert into salsa_file(`file_name`,`org_filename`,`file_path`,`regdate`,`type`,`use_yn`,`board_idx`,`file_type`,`size`,`file_download_uri`) " +
            "values(#{file_name},#{org_filename},#{file_path},now(),#{type},#{use_yn},#{board_idx},#{file_type},#{size},#{file_download_uri})")
    int insertFiletoDb(FileUploadResponse fileUploadResponse);
}
