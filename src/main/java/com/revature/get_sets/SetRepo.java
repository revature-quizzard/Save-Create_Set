package com.revature.get_sets;

import com.revature.documents.Set;
import com.revature.documents.User;
import com.revature.exceptions.ResourceNotFoundException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class SetRepo {

    private final DynamoDbTable<Set> setTable;

    /**
    *
    * This repo will search a set by ID and get A list of sets by tags
     * @param
     * @return
    *
    * */


    public SetRepo() {
        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        setTable = dbClient.table("Sets", TableSchema.fromBean(Set.class));

    }



    public Set getSetById(String id){



        AttributeValue val = AttributeValue.builder().s(id).build();
        Expression filter = Expression.builder().expression("#a = :b") .putExpressionName("#a", "id") .putExpressionValue(":b", val).build();
        ScanEnhancedRequest request = ScanEnhancedRequest.builder().filterExpression(filter).build();


        return setTable.scan(request).stream().findFirst().orElseThrow(ResourceNotFoundException::new).items().get(0);

    }


    public List<Set> getAllSets(){

        return setTable.scan().items().stream().collect(Collectors.toList());
    }



//    public PageIterable<Set> getAllByTags(List<String> tags){
////
////        List<Set> sets = new ArrayList<>();
////
////        tags.forEach(i ->{
////
////            AttributeValue val = AttributeValue.builder().s(i).build();
////            Expression filter = Expression.builder().expression("#a = :b") .putExpressionName(":b", "tags.name") .putExpressionValue(":b", val).build();
////            ScanEnhancedRequest request = ScanEnhancedRequest.builder().filterExpression(filter).build();
////
////            List<Set> set = setTable.scan(request).stream().findFirst().orElseThrow(ResourceNotFoundException::new).items();
////
////            sets.addAll(set);
////
////
////        });
////
////         return sets;
//
//        return setTable.scan();
//    }

}
