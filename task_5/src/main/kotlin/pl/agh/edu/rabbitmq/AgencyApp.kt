package pl.agh.edu.rabbitmq

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.agh.edu.rabbitmq.listener.ExchangeListener
import pl.agh.edu.rabbitmq.writer.ExchangeWriter
import pl.agh.edu.rabbitmq.writer.QueueWriter
import java.util.UUID

fun main() {
    println("Give agency id: ")
    val agencyId = readln()

    val writerByServiceType = mapOf(
        "people" to QueueWriter("requests.people"),
        "load" to QueueWriter("requests.load"),
        "satellite" to QueueWriter("requests.satellite")
    )

    val exchangeWriter = ExchangeWriter("spaceExchange")

    val idExchangeListener = ExchangeListener("spaceExchange", "agencies.$agencyId") {
        val splitMessage = it.split(":")
        println("Finished request for: " + splitMessage[1])
        println("Request id: " + splitMessage[2])
        println("Worker id: " + splitMessage[3])
    }
    val adminAgenciesListener = ExchangeListener("spaceExchange", "admin.agencies") {
        println("Message from administration to agencies: $it")
    }
    val adminAllListener = ExchangeListener("spaceExchange", "admin.all") {
        println("Message from administration to all: $it")
    }

    GlobalScope.launch {
        idExchangeListener.run()
    }
    GlobalScope.launch {
        adminAgenciesListener.run()
    }
    GlobalScope.launch {
        adminAllListener.run()
    }

    Runtime.getRuntime().addShutdownHook(Thread {
        println("Shutting down...")
        idExchangeListener.closeConnection()
        adminAgenciesListener.closeConnection()
        adminAllListener.closeConnection()
        writerByServiceType.values.forEach { it.closeConnection() }
    })

    while (true) {
        println("Request format: serviceType:serviceData (serviceType: people/load/satellite)")
        println("Give request: ")
        val request = readln()
        val splitRequest = request.split(":")
        if (splitRequest.size < 2) {
            println("Give valid request")
        } else {
            if (!writerByServiceType.containsKey(splitRequest[0])) {
                println("No such service type!")
            } else {
                val message = agencyId + ":" + UUID.randomUUID() + ":" + splitRequest[1]
                writerByServiceType[splitRequest[0]]!!.sendMessage(message)
                exchangeWriter.sendMessage(message, "agencies.request")
            }
        }
    }
}