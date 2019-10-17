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
                ", (select count(*) from salsa_comment where comment_idx = community.comment_idx AND use_yn = 'Y') as recommend_count " +
                ", ( SELECT user_name FROM salsa_user WHERE user_idx = writer_user_idx ) AS writer_user_name " +
            " FROM salsa_community as community" +
            " WHERE type = #{type}" +
            " AND use_yn = 'Y' " +
            " ORDER BY community_idx DESC " +
            " LIMIT #{page} , #{count}" )
    List<SalsaCommunity> getCommunityArticleAll(@Param("type") String type, @Param("page") int page, @Param("count") int count);

    //커뮤니티 인덱스로 커뮤니티 게시판의 특정글 가져오기
    @Select("SELECT community.* " +
            " , ( SELECT file_download_uri FROM salsa_file WHERE file_idx = user.att_file_id AND use_yn = 'Y') AS image_url " +
            " , ( SELECT user_name FROM salsa_user WHERE user_idx = user.user_idx ) AS writer_user_name " +
            " FROM salsa_community community " +
            " JOIN salsa_user user" +
            " ON community.writer_user_idx = user.user_idx" +
            " WHERE community_idx = #{community_idx} " +
            " AND type = #{type} " +
            " AND use_yn = 'Y' ")
    SalsaCommunity getCommunityByIdx(@Param("community_idx") int community_idx, @Param("type") String type);

    //커뮤니티 게시판의 총 글의 수
    @Select("SELECT COUNT(community_idx) FROM salsa_community")
    int getCommunityTotalCount();

    //커뮤니티 게시판 글 등록
    int createCommunityArticle(SalsaCommunity salsaCommunity);

    //커뮤니티 게시글 수정
    int modifyCommunityArticleByIdx(SalsaCommunity salsaCommunity);

    //커뮤니티 게시글 사용 여부 변경 ( 삭제 )
    @Delete("UPDATE salsa_community SET use_yn='N' WHERE community_idx = #{community_idx}")
    boolean deleteCommunityArticleByIdx(@Param("community_idx") int community_idx);

    //조회수(hit_count) 증가
    @Update("UPDATE salsa_community SET hit_count = ( hit_count + 1 ) WHERE community_idx = #{community_idx} AND type = #{type} ")
    int modifyCommunityHitCountByIdx(@Param("community_idx") int community_idx, @Param("type") String type);


}
