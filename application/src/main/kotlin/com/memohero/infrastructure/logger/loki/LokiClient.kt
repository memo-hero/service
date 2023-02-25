package com.memohero.infrastructure.logger.loki

import com.memohero.infrastructure.http.IHttpClient
import java.net.ConnectException

class LokiClient(
    private val configuration: LokiConfiguration,
    private val client: IHttpClient,
    private val circuitBreaker: CircuitBreaker = CircuitBreaker(2)
) {
    suspend fun push(log: LokiLog) {
        try {
            if (circuitBreaker.isCircuitOpen()) {
                println("WARNING: Circuit breaker for LokiClient is open")
                return
            }

            client.makePost("http://${ configuration.endpoint }/loki/api/v1/push", log.toString())
            circuitBreaker.success()
        }
        catch (ex: ConnectException) {
            circuitBreaker.failure()
            println("Cannot connect to Loki. Service will be degraded.")
            println(ex.message)
        }
        catch (ex: Exception) {
            println(ex.message)
        }
    }
}

