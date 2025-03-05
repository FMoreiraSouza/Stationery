package com.example.stationery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class StationeryApplication

fun main(args: Array<String>) {
	runApplication<StationeryApplication>(*args)
}
