plugins {
    id ("org.springframework.boot") version "3.5.4"
    id ("io.spring.dependency-management") version "1.1.7"
    id ("java")
    id ("org.liquibase.gradle") version "2.2.0"
}

group = "ru.amironnikov"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

var postgresqlVersion = "42.7.7"
var liquibaseCoreVersion = "4.2.2"
var r2dbcPostgresVersion = "1.0.5.RELEASE"

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.postgresql:r2dbc-postgresql:$r2dbcPostgresVersion")

    //common
    implementation(project(":common-lib"))

    //liquibase
    liquibaseRuntime ("org.liquibase:liquibase-core:${liquibaseCoreVersion}")
    liquibaseRuntime ("org.postgresql:postgresql:${postgresqlVersion}")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

liquibase {
    activities {
        register("main") {
            arguments = mapOf(
                "logLevel" to "info",
                "changeLogFile" to "src/main/resources/db/db.changelog-master.xml",
                "url" to "jdbc:postgresql://localhost:5432/order",
                "username" to "postgres",
                "password" to "postgres",
                "driver" to "org.postgresql.Driver"
            )
        }
    }
    runList = "main"
}

tasks.test {
    useJUnitPlatform()
}