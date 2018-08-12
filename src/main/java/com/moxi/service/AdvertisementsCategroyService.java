package com.moxi.service;

import com.moxi.model.content.AdvertisementsCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * ProjectName: moxi
 * User: quent
 * Date: 2018/8/11
 * Time: 19:44
 */
@Mapper
public interface AdvertisementsCategroyService {

    @Select("select * from `moxi`.`advertisements_category` where id = #{id};")
    AdvertisementsCategory findById(AdvertisementsCategory advertisementsCategory);

    @Select({
            "<script>",
            "select * from `moxi`.`advertisements_category`",
            "where state = 1",
            "<when test = 'name != null'>",
            "and name like concat('%',#{name},'%')",
            "</when>",
            "order by addDate desc limit #{start},#{end}",
            "</script>"
    })
    List<AdvertisementsCategory> list(AdvertisementsCategory advertisementsCategory);

    @Select({
            "<script>",
            "select count(*) from `moxi`.`advertisements_Category`",
            "where state = 1",
            "<when test = 'name != null'>",
            "and name like concat('%',#{name},'%')",
            "</when>",
            "</script>"
    })
    int count(AdvertisementsCategory advertisementsCategory);

    @Insert("insert into `moxi`.`advertisements_category` (`id`,`name`,`description`,`image`,`addDate`,`state`) values (null,#{name},#{description},#{image},now(),1;")
    int insert(AdvertisementsCategory advertisementsCategory);

    @Update("update `moxi`.`advertisements_category` set `name` = #{name},`description` = #{description},`image` = #{image} where `id` = #{id};")
    int update(AdvertisementsCategory advertisementsCategory);

    @Update("update `moxi`.`advertisements_category` set `state` = #{state} where `id` = #{id};")
    int updateState(AdvertisementsCategory advertisementsCategory);
}
