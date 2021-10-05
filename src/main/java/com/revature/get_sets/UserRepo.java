package com.revature.get_sets;

import com.revature.documents.User;
import com.revature.exceptions.ResourceNotFoundException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class UserRepo {

    private final DynamoDbTable<User> userTable;

    /**
     *
     * This repo will search a  by ID and get A list of sets by tags
     * @param
     * @return
     *
     * */


    public UserRepo() {
        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        userTable = dbClient.table("Users", TableSchema.fromBean(User.class));

    }

    /**
     * Getting a user by IiD
     * @param id
     * @return User
     */
    public User getUserById(String id){

        AttributeValue val = AttributeValue.builder().s(id).build();
        Expression filter = Expression.builder().expression("#a = :b") .putExpressionName("#a", "id") .putExpressionValue(":b", val).build();
        ScanEnhancedRequest request = ScanEnhancedRequest.builder().filterExpression(filter).build();

        return userTable.scan(request).stream().findFirst().orElseThrow(ResourceNotFoundException::new).items().get(0);

    }
}
