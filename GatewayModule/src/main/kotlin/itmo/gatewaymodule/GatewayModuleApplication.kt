package itmo.gatewaymodule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GatewayModuleApplication

fun main(args: Array<String>) {
	runApplication<GatewayModuleApplication>(*args)
}
