package pl.agh.edu.zookeeper;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

public class EventHandler implements Watcher {

    private final Supplier<ZooKeeper> zooKeeperSupplier;
    private final String watchedNode;
    private final ProcessExecutor processExecutor;

    public EventHandler(Supplier<ZooKeeper> zooKeeperSupplier, String watchedNode, ProcessExecutor processExecutor) {
        this.zooKeeperSupplier = zooKeeperSupplier;
        this.watchedNode = watchedNode;
        this.processExecutor = processExecutor;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        var eventType = watchedEvent.getType();
        if (Objects.equals(watchedEvent.getPath(), watchedNode)) {
            switch (eventType) {
                case NodeCreated -> {
                    System.out.println("Watched znode created, trying to start app");
                    try {
                        processExecutor.startProcess();
                    } catch (IOException e) {
                        System.out.println("Problem with starting app");
                    }
                }
                case NodeDeleted -> {
                    System.out.println("Watched znode deleted, closing app");
                    processExecutor.stopProcess();
                }
            }
        } else if (StringUtils.startsWith(watchedEvent.getPath(), watchedNode)) {
            if (EventType.NodeDeleted.equals(eventType) || EventType.NodeCreated.equals(eventType)) {
                try {
                    System.out.println("Watched znode children count: " +
                            zooKeeperSupplier.get().getAllChildrenNumber(watchedNode));
                } catch (KeeperException e) {
                    System.out.println("Keeper exception occurred");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
