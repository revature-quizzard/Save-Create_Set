package com.revature.get_sets;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

import java.util.List;

@Data
@DynamoDBTable(tableName = "Sets")
public class SetDto {

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private List<String> tags;

    @DynamoDBAttribute
    private String author;

    @DynamoDBAttribute
    private boolean is_public;

}