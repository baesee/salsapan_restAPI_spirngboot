package com.billlog.rest.salsapan.mapper;

import com.billlog.rest.salsapan.model.SalsaNotice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoticeMapper {

    //공지사항 게시글 총 목록 가져오기
    @Select("SELECT * FROM salsa_notice")
    List<SalsaNotice> getNoticeArticleAll();

    //공지사항 인덱스로 공지사항 게시판의 특정글 가져오기
    @Select("SELECT * from salsa_notice where notice_idx = #{notice_idx}")
    SalsaNotice getNoticeByIdx(@Param("notice_idx") int notice_idx);

    //공지사항 게시판의 총 글의 수
    @Select("SELECT COUNT(notice_idx) FROM salsa_notice")
    int getNoticeTotalCount();

    //공지사항 게시판 글 등록
    int createNoticeArticle(SalsaNotice salsaNotice);

    //공지사항 게시글 수정
    int modifyNoticeArticleByIdx(SalsaNotice salsaNotice);

    //공지사항 게시글 사용 여부 변경 ( 삭제 )
    @Delete("UPDATE salsa_notice SET use_yn='N' WHERE notice_idx = #{notice_idx}")
    boolean deleteNoticeArticleByIdx(@Param("notice_idx") int notice_idx);

}
