package com.revature.documents;
import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;


import java.util.List;


//This class represents all the fields of a Set which are stored in DynamoDB
@Data
@DynamoDbBean
public class Set {

    private String id;
    private String name;
    private List<Tag> tags;
    private List<Card> cards;
    private String author;
    private boolean isPublic;
    private int views;
    private int plays;
    private int studies;
    private int favorites;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSet_name() {
        return name;
    }

    public void setSet_name(String set_name) {
        this.name = set_name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean is_public() {
        return isPublic;
    }

    public void set_public(boolean is_public) {
        this.isPublic = is_public;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public int getStudies() {
        return studies;
    }

    public void setStudies(int studies) {
        this.studies = studies;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }
}

