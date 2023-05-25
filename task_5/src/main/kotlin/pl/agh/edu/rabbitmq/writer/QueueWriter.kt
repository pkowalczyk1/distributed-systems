package pl.agh.edu.rabbitmq.writer

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import java.nio.charset.StandardCharsets.UTF_8

class QueueWriter(private val queueName: String) {

    private val channel: Channel
    private val connection: Connection

    init {
        val connectionFactory = ConnectionFactory()
        connectionFactory.host = "localhost"
        connection = connectionFactory.newConnection()
        channel = connection.createChannel()
        channel.queueDeclare(queueName, false, false, false, null)
    }

    fun sendMessage(message: String) =
        channel.basicPublish("", queueName, null, message.toByteArray(UTF_8))

    fun closeConnection() {
        channel.close()
        connection.close()
    }
}