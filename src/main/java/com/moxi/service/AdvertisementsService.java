package com.moxi.service;

import com.moxi.model.content.Advertisements;
import com.moxi.util.Constant;
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
public interface AdvertisementsService {

    @Select("select * from moxi.advertisements where id = #{id};")
    Advertisements findById(Advertisements advertisements);

    @Select({
            "<script>",
            "select a.*,c.name as categoryname,c.image as categoryimage from moxi.advertisements a",
            "left join moxi.advertisements_category c on a.category = c.id",
            "where a.state = 1",
            "<when test = 'title != null'>",
            "and a.title like concat('%',#{title},'%')",
            "</when>",
            "<when test = 'category != 0'>",
            "and category = #{category}",
            "</when>",
            "<when test = 'commendState != 0'>",
            "and commendState = #{commendState}",
            "</when>",
            "<when test = 'orderBy == \"" + Constant.OrderByAddDateAsc + "\"'>",
            "order by " + Constant.OrderByAddDateAsc + ",addDate desc",
            "</when>",
            "<when test = 'orderBy == \"" + Constant.OrderByAddDateDesc + "\"'>",
            "order by " + Constant.OrderByAddDateDesc,
            "</when>",
            "<when test = 'orderBy == \"" + Constant.OrderByBrowsesDesc + "\"'>",
            "order by " + Constant.OrderByBrowsesDesc + ",addDate desc",
            "</when>",
            "<when test = 'orderBy == \"" + Constant.OrderByCommentsDesc + "\"'>",
            "order by " + Constant.OrderByCommentsDesc + ",addDate desc",
            "</when>",
            "<when test = 'orderBy == \"" + Constant.OrderByLikesDesc + "\"'>",
            "order by " + Constant.OrderByLikesDesc + ",addDate desc",
            "</when>",
            "<when test = 'orderBy == \"" + Constant.OrderByScoreDesc + "\"'>",
            "order by " + Constant.OrderByScoreDesc + ",addDate desc",
            "</when>",
            "limit #{start},#{end}",
            "</script>"
    })
    List<Advertisements> list(Advertisements advertisements);

    @Select({
            "<script>",
            "select count(*) from moxi.advertisements a",
            "left join moxi.advertisements_category c on a.category = c.id",
            "where a.state = 1",
            "<when test = 'title != null'>",
            "and a.title like concat('%',#{title},'%')",
            "</when>",
            "<when test = 'category != 0'>",
            "and category = #{title}",
            "</when>",
            "<when test = 'commendState != 0'>",
            "and commendState = #{commendState}",
            "</when>",
            "</script>"
    })
    int count(Advertisements advertisements);

    @Insert("insert into `moxi`.`advertisements` (`id`,`title`,`description`,`category`,`image`,`content`,`addDate`,`updateDate`,`commendState`,`state`,`browses`,`likes`,`comments`,`score`) values (null,#{title},#{description},#{category},#{image},#{content},now(),now(),1,1,0,0,0,0);")
    int insert(Advertisements advertisements);

    @Update("update `moxi`.`advertisements` set `title` = #{title},`description` = #{description},`category` = #{category},`image` = #{image},`content` = #{content},`updateDate` = now() where `id` = #{id};")
    int update(Advertisements advertisements);

    @Update("update `moxi`.`advertisements` set `state` = #{state},`commendState` = #{commendState},`browses` = #{browses},`likes` = #{likes},`comments` = #{comments},`score` = #{score} where `id` = #{id};")
    int updateState(Advertisements advertisements);
}
