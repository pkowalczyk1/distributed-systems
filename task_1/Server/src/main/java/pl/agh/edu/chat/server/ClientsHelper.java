package pl.agh.edu.chat.server;

import pl.agh.edu.chat.common.Client;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClientsHelper {

    private final ConcurrentMap<Integer, Client> clientById;

    private ClientsHelper() {
        this.clientById = new ConcurrentHashMap<>();
    }

    public static ClientsHelper getInstance() {
        return InstanceHolder.instance;
    }

    public Optional<Client> getClient(int clientId) {
        return Optional.ofNullable(clientById.get(clientId));
    }

    public void putClient(Client client) {
        clientById.put(client.id(), client);
    }

    public void removeClient(int clientId) {
        clientById.remove(clientId);
    }

    public Collection<Client> getClientsMatchingPredicate(Predicate<Client> predicate) {
        return clientById.values().stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    private static class InstanceHolder {
        private static final ClientsHelper instance = new ClientsHelper();
    }
}
