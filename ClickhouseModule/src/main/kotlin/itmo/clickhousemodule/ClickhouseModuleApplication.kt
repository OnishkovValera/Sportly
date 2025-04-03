package itmo.clickhousemodule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClickhouseModuleApplication

fun main(args: Array<String>) {
    runApplication<ClickhouseModuleApplication>(*args)
}
