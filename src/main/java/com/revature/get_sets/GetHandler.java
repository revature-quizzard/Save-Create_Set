package com.revature.get_sets;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.documents.Set;
import com.revature.documents.SetDto;
import com.revature.documents.Tag;
import com.revature.documents.User;
import com.revature.exceptions.ResourceNotFoundException;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

public class GetHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();
    private final SetRepo setRepo;
    private final UserRepo userRepo;

    public GetHandler(){

        setRepo = new SetRepo();
        userRepo = new UserRepo();
    }
    public GetHandler(SetRepo setRepo, UserRepo userRepo){

        this.setRepo = setRepo;
        this.userRepo = userRepo;
    }

    /**
     *  This if else statement will check if the apiGatewayProxyRequestEvent.getQueryStringParameters().keySet().toString()
     *   is one of the parameters to look for sets. If none of these match then it will return all Sets.
     * @param apiGatewayProxyRequestEvent
     * @param context
     * @return APIGatewayProxyResponseEvent
     * @author Jose Tejada and Jack Raney
     */

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {

        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization");
        headers.put("Access-Control-Allow-Origin", "*");
        responseEvent.setHeaders(headers);

        List<String> queryValues = new ArrayList<>();
        List<String> pathValues = new ArrayList<>();
        if(apiGatewayProxyRequestEvent.getQueryStringParameters() != null) {
            logger.log("RECEIVED EVENT: " + apiGatewayProxyRequestEvent.getQueryStringParameters().keySet());
            queryValues = apiGatewayProxyRequestEvent.getQueryStringParameters().keySet().stream().collect(Collectors.toList());
        }

        if(apiGatewayProxyRequestEvent.getPathParameters() != null) {
            logger.log("RECEIVED EVENT: " + apiGatewayProxyRequestEvent.getPathParameters().keySet());
            pathValues = apiGatewayProxyRequestEvent.getPathParameters().keySet().stream().collect(Collectors.toList());
        }


        User caller = null;
        //Does this give a Username or Id?
        try{
            Object item = apiGatewayProxyRequestEvent.getRequestContext().getAuthorizer().get("claims");
            LinkedHashMap casted = (LinkedHashMap) item;
            String caller_id = (String) casted.get("sub");
            caller = userRepo.getUserById(caller_id);
        } catch(Exception e){
            responseEvent.setStatusCode(401);
            return responseEvent;
        }

        //String username = apiGatewayProxyRequestEvent.getRequestContext().getIdentity().getUser();
        //logger.log(username);

        if (pathValues.contains("id")) {

            String id = apiGatewayProxyRequestEvent.getPathParameters().get("id");

            try {
                Set set = setRepo.getSetById(id);
                if(caller.getUsername().equals(set.getAuthor()) || set.isPublic()) {
                    responseEvent.setBody(mapper.toJson(set));
                } else {
                    throw new ResourceNotFoundException();
                }
            } catch (ResourceNotFoundException e) {
                responseEvent.setStatusCode(400);
                return responseEvent;
            } catch (Exception e) {
                responseEvent.setStatusCode(500);
                return responseEvent;
            }



        } else if (queryValues.contains("tags")) {
            List<String> tagNames = mapper.fromJson(apiGatewayProxyRequestEvent.getQueryStringParameters().get("tags"), ArrayList.class );

            List<Set> respBody = new ArrayList<>();

            try {

                List<Set> sets = setRepo.getAllSets();
                List<Set> results = new ArrayList<>();
                for(Set s : sets) {
                    for(Tag t : s.getTags()) {
                        if(tagNames.contains(t.getTagName())) {
                            results.add(s);
                            break;
                        }
                    }
                }
                responseEvent.setBody(mapper.toJson(results));


            } catch (Exception e) {
                responseEvent.setStatusCode(500);
                return responseEvent;
            }

        } else {
            System.out.println("get all sets in the DB");
            List<SetDto> respBody = new ArrayList<>();

            try {
                List<Set> sets = setRepo.getAllSets();
                List<Set> result = new ArrayList<>();
                System.out.println(sets);
                //Filter out private sets owned by other users
                for(Set s : sets) {
                    if(s.isPublic() || caller.getUsername().equals(s.getAuthor())) {
                        result.add(s);
                    }
                }
                responseEvent.setBody(mapper.toJson(result));

            } catch (Exception e) {
                responseEvent.setStatusCode(500);
                return responseEvent;
            }}


            System.out.println(responseEvent);
            return responseEvent;


        }
    }
