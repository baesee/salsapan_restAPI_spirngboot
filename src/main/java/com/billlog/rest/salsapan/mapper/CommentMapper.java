package com.billlog.rest.salsapan.mapper;

import com.billlog.rest.salsapan.model.SalsaCommunity;
import com.billlog.rest.salsapan.model.comment.SalsaComment;
import com.billlog.rest.salsapan.model.comment.SalsaCommentManage;
import com.billlog.rest.salsapan.model.file.FileManage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    //게시글의 댓글 가져오기
    /*
    @Select("SELECT * FROM salsa_comment" +
            " WHERE comment_idx = #{comment_manage_idx}" +
            " AND use_yn = 'Y' " +
            " LIMIT #{page} , #{count}")*/
    @Select("SELECT " +
            " comment.* " +
            ", user.user_name " +
            ", IFNULL(( SELECT file_download_uri FROM salsa_file WHERE file_idx = user.att_file_id AND use_yn = 'Y' ) ,'') AS image_url " +
            " FROM " +
            " salsa_comment comment" +
            " LEFT JOIN " +
            " salsa_user user " +
            " on comment.writer_user_idx = user.user_idx " +
            " WHERE comment_idx = #{comment_manage_idx} " +
            " AND use_yn = 'Y' " +
            " ORDER BY comment_sn DESC " )
    List<SalsaComment> getCommentsByCommentIdx(@Param("comment_manage_idx") int comment_manage_idx);
//    List<SalsaComment> getCommentsByCommentIdx(@Param("comment_manage_idx") int comment_manage_idx,@Param("page") int page, @Param("count") int count);

    //댓글 매니지 먼트 글 생성
    int createCommentManage(SalsaCommentManage salsaCommentManage);

    //댓글 작성
    int createComment(SalsaComment salsaComment);

}
