package zkTest;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by wangyang on 2018/11/1.
 */
public class TestZk {


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        // 第一个参数：ZooKeeper服务器的连接地址，如果ZooKeeper是集群模式或伪集群模式（即ZooKeeper服务器有多个），那么每个连接地址之间使用英文逗号间隔，单个连接地址的语法格式为"主机IP:ZooKeeper服务器端口号"；
        // 第二个参数：session超时时长（单位：毫秒）
        // 第三个参数：用于监控目录节点数据变化和子目录状态变化的Watcher对象

        ZooKeeper zk = (new ZooKeeper("127.0.0.1:2181", 30000, new TestWatcher()));
        String node = "/node2";
        Stat stat = zk.exists(node, false);
        //删除目录节点：
//        zk.delete("/node2", -1);
        if (stat == null) {
            // 2.创建名为"/node2"的持久目录节点
            String createResult = zk.create(node, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            // 判断指定目录节点是否存在
            System.out.println(createResult);
        }
        byte[] data = zk.getData(node, false, stat);
        System.out.println(new String(data));
        zk.close();

    }

}

class TestWatcher implements Watcher {

    //监控被触发的事件
    public void process(WatchedEvent watchedEvent) {


        System.out.println("--------------");
        System.out.println(watchedEvent.getPath());
        //none:客户端与服务器成功建立会话
        System.out.println(watchedEvent.getType());
        System.out.println(watchedEvent.getState());
        /**
         * KeeperState的四种状态
         *   NodeCreated:节点创建事件类型
         *   NodeDeleted:节点被删除
         *   NodeDataChanged:节点被修改
         *   None:客户端与服务器成功建立会话
         *   NodeChildrenChanged：子节点列表发生变更
         */
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            System.out.println("zookeeper连接成功了。。。");
        }
        System.out.println("--------------");
    }
}

//参考：https://blog.csdn.net/peerless_hero/article/details/53746331
