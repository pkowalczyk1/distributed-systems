package pl.agh.edu.rabbitmq.listener

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import java.nio.charset.StandardCharsets
import java.util.function.Consumer

class QueueListener(private val key: String,
                    private val messageHandler: Consumer<String>
) : Runnable {

    private val connection: Connection
    private val channel: Channel

    init {
        val connectionFactory = ConnectionFactory()
        connectionFactory.host = "localhost"
        connection = connectionFactory.newConnection()
        channel = connection.createChannel()
        channel.queueDeclare(key, false, false, false, null)
        channel.basicQos(1)
    }

    override fun run() {
        val consumer = object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties,
                                        body: ByteArray) {
                val message = String(body, StandardCharsets.UTF_8)
                messageHandler.accept(message)
                channel.basicAck(envelope.deliveryTag, false)
            }
        }
        channel.basicConsume(key, false, consumer)
    }

    fun closeConnection() {
        channel.close()
        connection.close()
    }
}