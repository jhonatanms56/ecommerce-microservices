package com.ecommerce.repository;

import com.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Optional;

@Repository
public class UserRepository {

    private final DynamoDbTable<User> table;

    public UserRepository(DynamoDbEnhancedClient enhancedClient,
                          @Value("${aws.dynamodb.tableName}") String tableName) {
        this.table = enhancedClient.table(tableName, TableSchema.fromBean(User.class));
    }

    public Optional<User> findById(String userId) {
        Key key = Key.builder().partitionValue(userId).build();
        return Optional.ofNullable(table.getItem(key));
    }

    public User save(User user) {
        table.putItem(user);
        return user;
    }

    public void deleteById(String userId) {
        Key key = Key.builder().partitionValue(userId).build();
        table.deleteItem(key);
    }
}
