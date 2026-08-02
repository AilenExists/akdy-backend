package dev.shaper.akdymall.modules.core

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.transport.rest5_client.low_level.Rest5Client
import dev.shaper.akdymall.services.es.data.EsProductImpl
import dev.shaper.akdymall.utils.ValueUtils.propertyGetter
import io.ktor.server.application.Application
import org.apache.http.HttpHost

import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.ktor.ext.getKoin

fun Application.configureElasticSearch() {
    val esClient = ElasticsearchClient.of {
        it.host("http://"+propertyGetter("elasticsearch.host") + ":" + propertyGetter("elasticsearch.port"))
            .usernameAndPassword(propertyGetter("elasticsearch.username"), propertyGetter("elasticsearch.password"))
    }

    loadKoinModules(module {
        single { esClient }
    })

    apply {
        EsProductImpl.init()
    }

}