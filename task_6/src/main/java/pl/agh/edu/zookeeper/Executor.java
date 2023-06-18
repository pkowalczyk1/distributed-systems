package pl.agh.edu.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import static org.apache.zookeeper.AddWatchMode.PERSISTENT_RECURSIVE;
import static org.apache.zookeeper.KeeperException.Code.NONODE;

public class Executor {

    private final ZooKeeper zooKeeper;
    private final String watchedNode;
    private boolean isWatchAdded = false;

    public Executor(String zooKeeperHost, String watchedNode, ProcessExecutor processExecutor)
            throws IOException {
        var eventHandler = new EventHandler(this::getZooKeeper, watchedNode, processExecutor);
        this.zooKeeper = new ZooKeeper(zooKeeperHost, 5_000, eventHandler);
        this.watchedNode = watchedNode;
        addWatchedEvent();
    }

    public static void main(String[] args) throws IOException {
        String host = args[0];
        String command = args[1];
        var processExecutor = new ProcessExecutor(command);
        Executor executor = new Executor(host, "/z", processExecutor);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Type 'list' to print all descendants of '/z' znode");
            String input = reader.readLine();
            if (Objects.equals(input, "list")) {
                executor.listDescendants();
            } else {
                System.out.println("Invalid command");
            }
        }
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void listDescendants() {
        try {
            List<String> children = zooKeeper.getChildren(watchedNode, false);
            printRecursively(children, watchedNode);
        } catch (KeeperException e) {
            if (e.code() == NONODE) {
                System.out.println("Node does not exist");
            } else {
                System.out.println("Keeper exception occurred");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printRecursively(List<String> children, String path) throws InterruptedException, KeeperException {
        for (var child : children) {
            var childPath = path + "/" + child;
            System.out.println(childPath);
            List<String> newChildren = zooKeeper.getChildren(childPath, false);
            printRecursively(newChildren, childPath);
        }
    }

    private void addWatchedEvent() {
        while (!isWatchAdded) {
            try {
                isWatchAdded = true;
                zooKeeper.addWatch(watchedNode, PERSISTENT_RECURSIVE);
            } catch (KeeperException | InterruptedException e) {
                isWatchAdded = false;
                e.printStackTrace();
            }
        }
    }
}
