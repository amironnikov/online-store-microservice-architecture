plugins {
    id("java")
    id ("org.springframework.boot") version "3.5.4"
    id ("io.spring.dependency-management") version "1.1.7"
}

group = "ru.amironnikov"
version = "1.0.0"

repositories {
    mavenCentral()
}

var cloudVersion = "4.3.0"

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway:$cloudVersion")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:$cloudVersion")
}

