package pl.agh.edu.rabbitmq.writer

import com.rabbitmq.client.BuiltinExchangeType.TOPIC
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import java.nio.charset.StandardCharsets.UTF_8

class ExchangeWriter(private val exchangeName: String) {

    private val channel: Channel
    private val connection: Connection

    init {
        val connectionFactory = ConnectionFactory()
        connectionFactory.host = "localhost"
        connection = connectionFactory.newConnection()
        channel = connection.createChannel()
        channel.exchangeDeclare(exchangeName, TOPIC)
    }

    fun sendMessage(message: String, key: String) =
        channel.basicPublish(exchangeName, key, null, message.toByteArray(UTF_8))

    fun closeConnection() {
        channel.close()
        connection.close()
    }
}