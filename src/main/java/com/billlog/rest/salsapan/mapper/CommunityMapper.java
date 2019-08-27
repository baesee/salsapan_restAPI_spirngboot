package com.billlog.rest.salsapan.mapper;

import com.billlog.rest.salsapan.model.SalsaCommunity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommunityMapper {

    //커뮤니티 게시글 총 목록 가져오기
    @Select("SELECT" +
                " community_idx" +
                ", title" +
                ", writer" +
                ", regdate" +
                ", hit_count" +
                ", content" +
                ", (select count(*) from salsa_comment where board_idx = community_idx) as recommend_count " +
            "FROM salsa_community" )
    List<SalsaCommunity> getCommunityArticleAll();

    //커뮤니티 인덱스로 커뮤니티 게시판의 특정글 가져오기
    @Select("SELECT * from salsa_community where community_idx = #{community_idx}")
    SalsaCommunity getCommunityByIdx(@Param("community_idx") int community_idx);

    //커뮤니티 게시판의 총 글의 수
    @Select("SELECT COUNT(community_idx) FROM salsa_community")
    int getCommunityTotalCount();

    //커뮤니티 게시판 글 등록
    //@Insert("INSERT INTO salsa_community(`title`, `writer`, `regdate`, `content`) " +
    //        "VALUES (#{title}, #{writer}, #{regdate}, #{content})")
    int createCommunityArticle(SalsaCommunity salsaCommunity);

    //커뮤니티 게시글 수정
    //@Update("UPDATE salsa_community SET title = #{title} ,content = #{content} " +
    //       "WHERE community_idx = #{community_idx}")
    int modifyCommunityArticleByIdx(SalsaCommunity salsaCommunity);

    //커뮤니티 게시글 사용 여부 변경 ( 삭제 )
    @Delete("UPDATE salsa_community SET use_yn='N' WHERE community_idx = #{community_idx}")
    boolean deleteCommunityArticleByIdx(@Param("community_idx") int community_idx);

}
