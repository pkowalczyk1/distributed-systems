package pl.agh.edu.rabbitmq

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.agh.edu.rabbitmq.listener.ExchangeListener
import pl.agh.edu.rabbitmq.writer.ExchangeWriter

fun main() {
    val possibleReceivers = setOf("all", "workers", "agencies")
    val spaceListener = ExchangeListener("spaceExchange", "agencies.*") {
        println("Message received: $it")
    }
    val exchangeWriter = ExchangeWriter("spaceExchange")

    GlobalScope.launch {
        spaceListener.run()
    }

    Runtime.getRuntime().addShutdownHook(Thread {
        println("Shutting down...")
        exchangeWriter.closeConnection()
        spaceListener.closeConnection()
    })
    while (true) {
        println("Give whom you want to send message to (possible: all, agencies, workers): ")
        val receiver = readln()
        if (receiver !in possibleReceivers) {
            println("Invalid receiver given!")
        } else {
            println("Give message: ")
            val message = readln()
            exchangeWriter.sendMessage(message, "admin.$receiver")
        }
    }
}