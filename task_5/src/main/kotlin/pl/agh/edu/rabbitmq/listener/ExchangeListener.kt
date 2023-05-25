package pl.agh.edu.rabbitmq.listener

import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client.BuiltinExchangeType.TOPIC
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import java.nio.charset.StandardCharsets.UTF_8
import java.util.function.Consumer

class ExchangeListener(exchangeName: String,
                       key: String,
                       private val messageHandler: Consumer<String>
) : Runnable {

    private val channel: Channel
    private val connection: Connection
    private val queueName: String

    init {
        val connectionFactory = ConnectionFactory()
        connectionFactory.host = "localhost"
        connection = connectionFactory.newConnection()
        channel = connection.createChannel()
        channel.exchangeDeclare(exchangeName, TOPIC)
        queueName = channel.queueDeclare().queue
        channel.queueBind(queueName, exchangeName, key)
    }

    override fun run() {
        val consumer = object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: BasicProperties,
                                        body: ByteArray) {
                val message = String(body, UTF_8)
                messageHandler.accept(message)
                channel.basicAck(envelope.deliveryTag, false)
            }
        }
        channel.basicConsume(queueName, false, consumer)
    }

    fun closeConnection() {
        channel.close()
        connection.close()
    }
}