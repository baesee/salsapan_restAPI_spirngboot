package com.billlog.rest.salsapan.mapper;

import com.billlog.rest.salsapan.model.common.SalsaCity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommonMapper {
    //도시 전체 목록 가져오기
    @Select("select * from salsa_city")
    List<SalsaCity> getCitys();
}
