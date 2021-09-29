package com.revature.get_sets;


import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;


public class SetRepo {

//    private final DynamoDBMapper dbReader;
    private final DynamoDbTable<Set> setTable;

    public SetRepo() {
//        this.dbReader = dbReader;
        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        setTable = dbClient.table("books", TableSchema.fromBean(Set.class));
    }


    public SetRepo(DynamoDbTable<Set> bookTable){
        this.setTable = bookTable;
//        dbReader = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }



    public PageIterable<Set> getAllSets(){

//        Map<String, AttributeValue> queryInputs = new HashMap<>();
//        DynamoDBScanExpression query = new DynamoDBScanExpression()
//                .withExpressionAttributeValues(queryInputs);
//
//        List<Set> results = dbReader.scan(Set.class, query);

        return setTable.scan();

    }

}
