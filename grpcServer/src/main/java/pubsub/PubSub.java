package pubsub;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import firestore.Database;
import firestore.KeyCredential;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PubSub {
    static final String PROJECT_ID = "g08-t3n-v2021";
    static String TOPIC_NAME = null;
    static String SUBS_NAME = null;

    public PubSub() throws ExecutionException, InterruptedException {
        SUBS_NAME = Database.getParameter("subNameVision", "PubSub", Database.INFO_COLLECTION);
        TOPIC_NAME = Database.getParameter("topicNameVision", "PubSub", Database.INFO_COLLECTION);
    }

    public void publishMessage(String message)
            throws IOException, InterruptedException {
        TopicName topicName = TopicName.of(PROJECT_ID, TOPIC_NAME);

        Publisher publisher = null;
        try {
            // Create a publisher instance with default settings bound to the topic
            publisher = Publisher.newBuilder(topicName).build();

            ByteString data = ByteString.copyFromUtf8(message);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

            // Once published, returns a server-assigned message id (unique within the topic)
            publisher.publish(pubsubMessage);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (publisher != null) {
                // When finished with the publisher, shutdown to free up resources.
                publisher.shutdown();
                publisher.awaitTermination(3, TimeUnit.SECONDS);
            }
        }
    }
}