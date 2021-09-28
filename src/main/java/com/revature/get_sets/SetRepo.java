package com.revature.get_sets;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SetRepo {

    private final DynamoDBMapper dbReader;

    public SetRepo(){
        dbReader = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public List<SetDto> getAllSets(){

        Map<String, AttributeValue> queryInputs = new HashMap<>();
        DynamoDBScanExpression query = new DynamoDBScanExpression()
                .withExpressionAttributeValues(queryInputs);

        List<SetDto> results = dbReader.scan(SetDto.class, query);

        return results;

    }

}
