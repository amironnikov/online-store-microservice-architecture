plugins {
    id("java")
}

group = "ru.amironnikov"
version = "1.0.o"

repositories {
    mavenCentral()
}

var cloudVersion = "4.3.0"

dependencies {

    implementation("org.springframework.cloud:spring-cloud-starter-gateway:$cloudVersion")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:$cloudVersion")
}

