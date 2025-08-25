plugins {
    id ("org.springframework.boot") version "3.5.4"
    id ("io.spring.dependency-management") version "1.1.7"
    id ("java")
    id("me.champeau.jmh") version "0.7.3"
}

group = "ru.amironnikov"
version = "1.0.0"

repositories {
    mavenCentral()
}

val annotationApiVersion = "8.5.17"
val swaggerVersion= "2.6.0"
val ehcacheVersion = "3.10.8"
val jaxbApiVersion = "2.3.1"
val jaxbImplVersion = "2.3.6"

dependencies {

    //common
    implementation(project(":common-lib"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerVersion")

    //for offHeap cache
    implementation ("org.springframework.boot:spring-boot-starter-cache")
    implementation ("org.ehcache:ehcache:$ehcacheVersion")
    runtimeOnly ("jakarta.xml.bind:jakarta.xml.bind-api")
    implementation ("javax.xml.bind:jaxb-api:$jaxbApiVersion")
    implementation ("com.sun.xml.bind:jaxb-impl:$jaxbImplVersion")

    implementation("io.minio:minio:$annotationApiVersion")
}

jmh {
    excludes.addAll("*")
    includes.addAll("ru.amironnikov.image.ImageCachesBenchmark")
}
