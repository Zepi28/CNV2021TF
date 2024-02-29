import com.google.api.gax.core.ExecutorProvider;
import com.google.api.gax.core.InstantiatingExecutorProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.Flow;

public class PubSub {

    public static void main(String[] args) {
        ExecutorProvider executorProvider = InstantiatingExecutorProvider.newBuilder().setExecutorThreadCount(1).build();

        Subscriber sub = Subscriber.newBuilder("AnswersSub", new MessageReceiveHandler())
                                    .setExecutor(executorProvider)
                                    .build();
        sub.startAsync().awaitRunning();
        subscriber.awaitTerminated();
    }
}
