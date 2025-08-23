plugins {
    id ("org.springframework.boot") version "3.5.4"
    id ("io.spring.dependency-management") version "1.1.7"
    id ("java")
}

group = "ru.amironnikov"
version = "1.0.0"

repositories {
    mavenCentral()
}

val annotationApiVersion = "8.5.17"
val swaggerVersion= "2.6.0"

dependencies {

    //common
    implementation(project(":common-lib"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerVersion")
    implementation("io.minio:minio:$annotationApiVersion")
}

