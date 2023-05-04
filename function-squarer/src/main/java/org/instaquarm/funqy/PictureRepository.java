package org.instaquarm.funqy;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
@Startup
public class PictureRepository {

    public final static String TIME_COL = "time";
    public final static String IMAGE_COL = "image";
    public final static String KEY_COL = "key";

    public final static String KEY_VALUE = "instaquarm";

    public final static String TABLE = "instaquarm-pictures";
    private final DynamoDbClient dynamo;

    PictureRepository(DynamoDbClient dynamoDB) {
        System.out.println("Initializing client");
        this.dynamo = dynamoDB;
    }

    public void persist(byte[] image) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put(TIME_COL, AttributeValue.builder().n(Long.toString(System.currentTimeMillis())).build());
        item.put(KEY_COL, AttributeValue.builder().s(KEY_VALUE).build());
        item.put(IMAGE_COL, AttributeValue.builder().b(SdkBytes.fromByteArray(image)).build());
        PutItemRequest req = PutItemRequest.builder()
                .tableName(TABLE)
                .item(item)
                .build();
        dynamo.putItem(req);
    }
}
