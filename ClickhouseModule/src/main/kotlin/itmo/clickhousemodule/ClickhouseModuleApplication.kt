package itmo.clickhousemodule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class ClickhouseModuleApplication

fun main(args: Array<String>) {
	runApplication<ClickhouseModuleApplication>(*args)
}
