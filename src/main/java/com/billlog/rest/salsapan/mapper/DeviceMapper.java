package com.billlog.rest.salsapan.mapper;

import com.billlog.rest.salsapan.model.SalsaDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface DeviceMapper {

    //디바이스 정보를 등록 / 변경한다.
    int updateDeviceInfo(SalsaDevice salsaDevice);

    // 도시 번호와 동일한 사용자 목록 가져오기.
    //인포 정보가 등록 되고 그 글과 같은 도시 지역을 관심지역으로 설정한 사용자에게 푸쉬를 날린다.
    @Select(" SELECT device.device_token " +
            " FROM salsa_user user " +
            " RIGHT JOIN salsa_device device " +
            " ON user.user_id = device.user_id " +
            " WHERE user.user_city = #{city}")
    List<String> findMsgReciverUsersByCity(@Param("city") String city);

    // 코멘트 IDX 값을 이용하여 글 커뮤니티 글 작성자의 user_idx를 찾아내 토큰 값을 가져온다.
    @Select(" SELECT device.device_token " +
            " FROM salsa_user user " +
            " INNER JOIN salsa_community community ON user.user_idx = community.writer_user_idx " +
            " LEFT JOIN salsa_device device ON user.user_id = device.user_id " +
            " WHERE community.comment_idx = #{comment_idx}")
    String findMsgReciverUserByCommentIdx(@Param("comment_idx") int comment_idx);

    //유저 idx로 사용자의 name을 가져와 토큰값을 받은 뒤 권한이 변경된 사용자에게 푸쉬를 날린다.
    @Select(" SELECT device.device_token " +
            " FROM salsa_user user " +
            " RIGHT JOIN salsa_device device " +
            " ON user.user_id = device.user_id " +
            " WHERE user.user_idx = #{user_idx}")
    String findMsgReciverUsersByUserIdx(@Param("user_idx") String user_idx);
}

