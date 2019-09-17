package com.billlog.rest.salsapan.mapper;

import com.billlog.rest.salsapan.model.SalsaInfo;
import com.billlog.rest.salsapan.model.file.FileUploadResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InfoMapper {

    //정보제공성 게시글의 모든 목록 가져오기
    @Select("SELECT info.* " +
            " , ( SELECT city_name FROM salsa_city WHERE city_idx = info.city ) as city_nm " +
            " FROM salsa_info info" +
            " WHERE type = #{type}" +
            " AND use_yn = 'Y' " +
            " ORDER BY info_idx DESC" +
            " LIMIT #{page} , #{count}")
    List<SalsaInfo> getInfoArticleAll(@Param("type") String type, @Param("page") int page, @Param("count") int count);

    //인포 인덱스로 인포정보 특정 인포 글 가져오기
    @Select("SELECT info.* \n" +
            " , ( SELECT city_name FROM salsa_city WHERE city_idx = info.city ) as city_nm \n" +
            "        FROM salsa_info info \n" +
            "        WHERE info_idx = #{info_idx}" +
            "        AND type = #{type}")
    SalsaInfo getInfoArticleByIdx(@Param("info_idx") int info_idx, @Param("type") String type);

    //정보제공성 게시글의 총 글의 수
    @Select("SELECT COUNT(info_idx) FROM salsa_info")
    int getInfoTotalCount();

    //정보제공성 게시글 등록
    //동적 쿼리를 사용하기 위해 InfoMapper.xml 에서 쿼리를 작성하였고,
    //해당 메소드 명 'createInfoArticle'이 id 값과 매칭이 되어 호출이 될 수 있다.
    int createInfoArticle(SalsaInfo salsaInfo);

    //정보제공성 게시글의 수정
    /* 동적 쿼리를 사용하기 위해 InfoMapper.xml 에서 쿼리를 작성하였고, 해당 메소드 명 'modifyInfoArticleByIdx'이 id 값과 매칭이 되어 호출이 될 수 있다.
    @Update("UPDATE salsa_info SET title = #{title} ,content = #{content} " +
            "WHERE info_idx = #{info_idx}")
     */
    int modifyInfoArticleByIdx(SalsaInfo salsaInfo);

    //정보제공성 게시글의 사용 여부 변경 ( 삭제 )
    @Delete("UPDATE salsa_info SET use_yn='N' WHERE info_idx = #{info_idx}")
    boolean deleteInfoArticleByIdx(@Param("info_idx") int info_idx);
}
