plugins {
    id ("org.springframework.boot") version "3.5.4"
    id ("io.spring.dependency-management") version "1.1.7"
    id("java")
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
    mavenLocal()
}

var postgresqlVersion = "42.7.7"
dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.3.0")

    implementation ("org.postgresql:postgresql:${postgresqlVersion}")
}
