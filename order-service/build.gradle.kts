plugins {
    id ("org.springframework.boot") version "3.5.4"
    id ("io.spring.dependency-management") version "1.1.7"
    id ("java")
    id ("org.liquibase.gradle") version "2.2.0"
    id ("com.google.protobuf") version "0.9.5"
}

group = "ru.amironnikov"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

var postgresqlVersion = "42.7.7"
var liquibaseCoreVersion = "4.2.2"
var r2dbcPostgresVersion = "1.0.5.RELEASE"
val grpcVersion = "1.66.0"
val grpcClientVersion = "3.1.0.RELEASE"
val protobufJavaVersion = "4.27.3"
val annotationApiVersion = "1.3.2"

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.3.0")

    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j:3.3.0")

    implementation("org.postgresql:r2dbc-postgresql:$r2dbcPostgresVersion")

    //grpc
    implementation("net.devh:grpc-client-spring-boot-starter:$grpcClientVersion")
    implementation("com.google.protobuf:protobuf-java:$protobufJavaVersion")
    implementation("javax.annotation:javax.annotation-api:$annotationApiVersion")

    //liquibase
    liquibaseRuntime ("org.liquibase:liquibase-core:${liquibaseCoreVersion}")
    liquibaseRuntime ("org.postgresql:postgresql:${postgresqlVersion}")
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

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.27.3"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc")
            }
        }
    }
}

