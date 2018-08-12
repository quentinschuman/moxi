package com.moxi.model.content;

import com.moxi.model.BaseObject;

import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * ProjectName: moxi
 * User: quent
 * Date: 2018/8/11
 * Time: 21:08
 */
public class Advertisements extends BaseObject{
    private long id;
    private String title;
    private String description;
    private long category;
    private String categoryName;
    private String categoryImage;
    private String image;
    private String content;
    private Date addDate;
    private Date updateDate;
    private int state;
    private int commendState;

    private int browses;
    private int likes;
    private int comments;
    private int score;

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setCommendState(int commendState) {
        this.commendState = commendState;
    }

    public void setBrowses(int browses) {
        this.browses = browses;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getCategory() {
        return category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public Date getAddDate() {
        return addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public int getState() {
        return state;
    }

    public int getCommendState() {
        return commendState;
    }

    public int getBrowses() {
        return browses;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public int getScore() {
        return score;
    }
}
