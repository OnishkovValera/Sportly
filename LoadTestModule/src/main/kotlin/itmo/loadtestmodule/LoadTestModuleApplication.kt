package itmo.loadtestmodule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LoadTestModuleApplication

fun main(args: Array<String>) {
    runApplication<LoadTestModuleApplication>(*args)
}
