package zkTest;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wangyang on 2018/11/1.
 */
public class EventTypeNone implements Watcher {
    private static final String host = "127.0.0.1:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException {
        ZooKeeper zk = new ZooKeeper(host, 5000, new EventTypeNone());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 程序比较简单，在运行程序之前需要下载zookeeper，然后部署一下，
     * 由于我是部署在本机，所以ip地址就是本机地址，还有就是需要CountDownLatch来阻塞主线程，
     * 因为zookeeper创建连接是异步，如果不阻塞主线程的话，在运行完 new Zookeeper之后便会结束，这样就看不到process函数被回调。
     *
     * @param watchedEvent
     */

    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent.getType());
        System.out.println(watchedEvent.getState());
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            System.out.println("连接到zookeeper上了。。");
        }
        countDownLatch.countDown();

    }
}
