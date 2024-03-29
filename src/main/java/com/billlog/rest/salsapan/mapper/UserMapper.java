package com.billlog.rest.salsapan.mapper;

import com.billlog.rest.salsapan.model.SalsaRole;
import com.billlog.rest.salsapan.model.SalsaUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    //유저 아이디(String)로 유저정보 가져오기
    @Select("Select *, (select role from salsa_role where role_idx = user_role) as user_role_nm, ( SELECT city_name FROM salsa_city WHERE city_idx = user_city ) AS user_city_nm, ( SELECT file_download_uri FROM salsa_file WHERE file_idx = att_file_id) AS image_url from salsa_user where user_id = #{user_id} and user_yn = 'Y' ")
    SalsaUser findByUsername(@Param("user_id") String user_id);

    @Select("Select *, (select role from salsa_role where role_idx = user_role) as user_role_nm, ( SELECT city_name FROM salsa_city WHERE city_idx = user_city ) AS user_city_nm, ( SELECT file_download_uri FROM salsa_file WHERE file_idx = att_file_id) AS image_url  from salsa_user where user_id = #{user_id} and provider = #{provider}  and user_yn = 'Y' ")
    SalsaUser findByUidAndProvider(@Param("user_id") String user_id, @Param("provider") String provider);

    //유저 인덱스(int)로 유저정보 가져오기
    @Select("Select *, (select role from salsa_role where role_idx = user_role) as user_role_nm, ( SELECT city_name FROM salsa_city WHERE city_idx = user_city ) AS user_city_nm, ( SELECT file_download_uri FROM salsa_file WHERE file_idx = att_file_id) AS image_url  from salsa_user where user_idx = #{user_idx}  and user_yn = 'Y' ")
    SalsaUser getUserById(@Param("user_idx") int user_idx);

    //유저 전체 목록 가져오기
//    @Select("select *, (select role from salsa_role where role_idx = user_role) as user_role_nm, ( SELECT city_name FROM salsa_city WHERE city_idx = user_city ) AS user_city_nm, ( SELECT file_download_uri FROM salsa_file WHERE file_idx = att_file_id) AS image_url from salsa_user")
    List<SalsaUser> getAll(@Param("type") String type, @Param("type_value") String type_value);

    //유저권한 전체 목록 가져오기
    @Select("select * from salsa_role WHERE user_yn ='Y' ")
    List<SalsaRole> getAllRoles();

    //유저 사용 여부 변경 ( 삭제 )
    @Delete("UPDATE salsa_user SET user_yn='N' WHERE user_idx = #{user_idx} and user_yn ='Y' ")
    boolean deleteUserById(@Param("user_idx") int user_idx);

    //유저 아이디 중복 체크
    @Select("select count(user_id) from salsa_user where user_id = #{user_id}")
    int userIdCheck(@Param("user_id") String user_id);

    //유저 이메일 중복 체크
    @Select("select count(user_email) from salsa_user where user_email = #{user_email}")
    int userEmailCheck(@Param("user_email") String user_email);

    //이메일 인증키 셋팅
    @Update("UPDATE salsa_user SET user_auth_key = #{user_auth_key} WHERE user_idx = #{user_idx}")
    boolean setEamilAuthTempKey(@Param("user_idx") int user_idx, @Param("user_auth_key") String user_auth_key);

    //이메일 인증 확인
    @Select("select count(*) from salsa_user where user_idx = #{user_idx} and user_auth_key = #{key}")
    int setEamilAuthConfirm(@Param("user_idx") int user_idx, @Param("key") String key);

    //유저 이름(닉네임) 중복 체크
    @Select("select count(user_name) from salsa_user where user_name = #{user_name}")
    int userNameCheck(@Param("user_name") String user_name);

    //유저 ID 찾기
    @Select("select user_id from salsa_user where user_name = #{user_name} and user_email= #{user_email}")
    String findUserId(@Param("user_name") String user_name, @Param("user_email") String user_email);

    //유저 PWD 찾기
    @Select("select user_pwd from salsa_user where user_id = #{user_id} and user_name = #{user_name} and user_email= #{user_email}")
    String findUserPwd(@Param("user_id") String user_id, @Param("user_name") String user_name, @Param("user_email") String user_email);

    //패스워드 변경
    @Update("UPDATE salsa_user SET user_pwd = #{user_pwd} WHERE user_idx = #{user_idx}")
    int modifyPasswordByIdx(@Param("user_idx") int user_idx, @Param("user_pwd") String user_pwd);

    //유저 등록
    int createUser(SalsaUser user);

    //유저 정보 수정
    int modifyUserById(SalsaUser user);

    //임시 패스워드 저장
    @Update("UPDATE salsa_user SET user_pwd = #{user_pwd} WHERE user_id = #{user_id} AND user_email = #{user_email} AND user_name = #{user_name}")
    int modifyUserByIdNameEmail(SalsaUser user);

    //소셜 - 카카오 고유 아이디 중복 체크
    @Select("select count(user_id) from salsa_user where user_id = #{user_id} and provider = #{provider} ")
    int kakaoIdCheck(@Param("user_id") String user_id, @Param("provider") String provider);

    @Insert("INSERT INTO salsa_user(`user_id`, `role_id`) " +
            "VALUES (#{user_id}, #{user_role}")
    void createUserRoleInit(SalsaUser user);

    //프리미엄 유저 전환 상태 변경
    @Update(" UPDATE salsa_user " +
            " SET user_premium_state = #{type} " +
            " WHERE user_idx = #{user_idx}")
    void modifyPremiumState(@Param("user_idx") int user_idx, @Param("type")String type);

    /**
     * WEB Temp
     * 1. 프리미엄 신청 유저 상태 값 변경 및 권한 수정
     */
    //1. 프리미엄 신청 유저 상태 값 변경 및 권한 수정
    void modifyPStateAndRole(@Param("user_idx") String user_idx, @Param("type")String type);


}
