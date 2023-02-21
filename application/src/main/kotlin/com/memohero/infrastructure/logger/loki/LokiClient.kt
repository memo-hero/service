package com.memohero.infrastructure.logger.loki

import com.memohero.infrastructure.http.IHttpClient

class LokiClient(
    private val configuration: LokiConfiguration,
    private val client: IHttpClient,
) {
    suspend fun push(log: LokiLog) {
        client.makePost("http://${ configuration.endpoint }/loki/api/v1/push", log.toString())
    }
}