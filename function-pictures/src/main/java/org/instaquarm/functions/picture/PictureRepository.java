package org.instaquarm.functions.picture;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;

@ApplicationScoped
@Startup
public class PictureRepository {

    public final static String TIME_COL = "time";
    public final static String IMAGE_COL = "image";

    public final static String TABLE = "instaquarm-pictures";

    private final DynamoDbClient dynamo;

    PictureRepository(DynamoDbClient dynamoDB) {
        this.dynamo = dynamoDB;
    }

    public void log() {
        System.out.println("Tables:");
        dynamo.listTables().tableNames().forEach(s -> System.out.println("Table : " + s));
        System.out.println("----");
    }

    public Picture getLast() {
        QueryResponse response = dynamo.query(request());
        if (response.hasItems()) {
            return create(response.items().get(0));
        }
        return null;
    }

    Picture create(Map<String, AttributeValue> stringAttributeValueMap) {
        Picture picture = new Picture();
        picture.image = stringAttributeValueMap.get(IMAGE_COL).b().asByteArray();
        return picture;
    }

    QueryRequest request() {
        return QueryRequest.builder()
                .tableName(TABLE)
                .scanIndexForward(false)
                .limit(1)
                .keyConditions(Map.of("key", Condition.builder().comparisonOperator("EQ").attributeValueList(AttributeValue.builder().s("instaquarm").build()).build()))
                .attributesToGet(TIME_COL, IMAGE_COL)
                .build();
    }
}
