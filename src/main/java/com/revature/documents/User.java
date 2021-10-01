package com.revature.documents;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.ArrayList;
import java.util.List;

@Data
@DynamoDbBean
public class User {
    private String id;
    private String username;
    private String profilePicture;
    private int points;
    private int wins;
    private int losses;
    private String registrationDate;
    private List<String> gameRecords;
    private List<UserSetDoc> createdSets = new ArrayList<>();
    private List<UserSetDoc> favoriteSets = new ArrayList<>();

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<String> getGameRecords() {
        return gameRecords;
    }

    public void setGameRecords(List<String> gameRecords) {
        this.gameRecords = gameRecords;
    }

    public List<UserSetDoc> getCreatedSets() {
        return createdSets;
    }

    public void setCreatedSets(List<UserSetDoc> createdSets) {
        this.createdSets = createdSets;
    }

    public List<UserSetDoc> getFavoriteSets() {
        return favoriteSets;
    }

    public void setFavoriteSets(List<UserSetDoc> favoriteSets) {
        this.favoriteSets = favoriteSets;
    }

    //This class represents the fields of a Set that the User Document cares about storing (All but Cards)
    @Data
    @DynamoDbBean
    public static class UserSetDoc{
        private String id;
        private String setName;
        private List<Tag> tags;
        private String author;
        private boolean isPublic;
        private int views = 0;
        private int plays = 0;
        private int studies = 0;
        private int favorites = 0;

        public UserSetDoc(Set set) {
            id = set.getId();
            setName = set.getSetName();
            tags = set.getTags();
            author = set.getAuthor();
            isPublic = set.isPublic();
        }

        public UserSetDoc() {
            super();
        }
    }


}
