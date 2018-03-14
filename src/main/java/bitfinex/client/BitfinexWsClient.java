package bitfinex.client;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.Closeable;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;


@ClientEndpoint
public class BitfinexWsClient implements Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BitfinexWsClient.class);

    private String wsUri;

    private Session userSession = null;

    private final List<Consumer<String>> callbacks;

    private final CountDownLatch connectionReadyLatch;

    public BitfinexWsClient(String wsUri) {
        this.wsUri = wsUri;
        this.callbacks = Lists.newCopyOnWriteArrayList();
        this.connectionReadyLatch = new CountDownLatch(1);
    }

    @OnMessage
    public void onMessage(String message) {
        if (StringUtils.isNotEmpty(message) && message.contains("error")) {
            LOGGER.error("Got error message from bitfinex: " + message);
            return;
        }
        try {
            callbacks.forEach(c -> c.accept(message));
        } catch (Exception ex) {
            LOGGER.error("Processing message: {} caused exception: ", message, ex);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        LOGGER.info("Opened bitfinex ws session");
        connectionReadyLatch.countDown();
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        LOGGER.info("Closed bitfinex ws session, reason: " + reason);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        LOGGER.error("Error occured: ", t);
    }

    /**
     * Adds new callback, which will be invoked on new message from bitfinex
     * @param callback callback to add
     */
    public void addCallback(Consumer<String> callback) {
        callbacks.add(callback);
        LOGGER.info("Added callback to bitfinexWsClient: " + callback.getClass().getSimpleName());
    }

    /**
     * Removes callback, which will be invoked on new message from bitfinex
     * @param callback callback to remove
     */
    public void removeCallback(Consumer<String> callback) {
        callbacks.remove(callback);
        LOGGER.info("Removed callback from bitfinexWsClient: " + callback.getClass().getSimpleName());
    }

    /**
     * Sends message to server
     * @param message message to send
     */
    public void sendMessage(final String message) {
        if(!isConnected() || userSession.getAsyncRemote() == null) {
            LOGGER.warn("Client is not connected - message will not be sent");
            return;
        }

        userSession.getAsyncRemote().sendText(message);
        LOGGER.debug("Send a message to bfx: " + message);
    }

    /**
     * Connects to bitfinex
     */
    public void connect() throws Exception {
        URI bitfinexURI = new URI(wsUri);
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        userSession = container.connectToServer(this, bitfinexURI);
        connectionReadyLatch.await();
    }

    /**
     * Close connection
     */
    public void close() {
        if(userSession == null) {
            LOGGER.warn("Session will not be closed, because it doesnt exist anymore");
            return;
        }

        try {
            userSession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Socket closed"));
        } catch (Exception ex) {
            LOGGER.error("Closing user session caused exception: ", ex);
        }
    }

    public boolean isConnected() {
        return userSession != null;
    }

    public String getWsUri() {
        return wsUri;
    }
}
