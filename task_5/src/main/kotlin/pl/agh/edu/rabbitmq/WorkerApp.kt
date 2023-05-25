package pl.agh.edu.rabbitmq

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.agh.edu.rabbitmq.listener.ExchangeListener
import pl.agh.edu.rabbitmq.listener.QueueListener
import pl.agh.edu.rabbitmq.writer.ExchangeWriter
import java.util.function.Consumer

fun main() {
    val possibleServiceTypes = setOf("people", "load", "satellite")

    println("Give worker id: ")
    val workerId = readln()

    var firstType = ""
    var secondType = ""
    println("Available service types: people, load, satellite")
    while (firstType == secondType || firstType !in possibleServiceTypes || secondType !in possibleServiceTypes) {
        println("Give first service type: ")
        firstType = readln()
        println("Give first service type: ")
        secondType = readln()
    }

    val exchangeWriter = ExchangeWriter("spaceExchange")
    val messageHandler = Consumer<String> {
        val splitMessage = it.split(":")
        if (splitMessage.size < 3) {
            println("Received wrong message format!")
        } else {
            println("Received request: $it")
            val topic = "agencies." + splitMessage[0]
            val message = "finished:" + splitMessage[2] + ":" + splitMessage[1] + ":" + workerId
            exchangeWriter.sendMessage(message, topic)
        }
    }

    val firstQueueListener = QueueListener("requests.$firstType", messageHandler)
    val secondQueueListener = QueueListener("requests.$secondType", messageHandler)

    val adminWorkersListener = ExchangeListener("spaceExchange", "admin.workers") {
        println("Message from administration to workers: $it")
    }
    val adminAllListener = ExchangeListener("spaceExchange", "admin.all") {
        println("Message from administration to all: $it")
    }

    GlobalScope.launch {
        firstQueueListener.run()
    }
    GlobalScope.launch {
        secondQueueListener.run()
    }
    GlobalScope.launch {
        adminWorkersListener.run()
    }
    GlobalScope.launch {
        adminAllListener.run()
    }

    Runtime.getRuntime().addShutdownHook(Thread {
        println("Shutting down...")
        exchangeWriter.closeConnection()
        firstQueueListener.closeConnection()
        secondQueueListener.closeConnection()
        adminAllListener.closeConnection()
        adminWorkersListener.closeConnection()
    })
}