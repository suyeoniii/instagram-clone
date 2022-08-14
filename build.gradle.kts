import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.2"
	id("io.spring.dependency-management") version "1.0.12.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "com.gridgetest"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc:2.7.2")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.2")
	implementation("org.springframework.boot:spring-boot-starter-validation:2.7.2")
	implementation("org.springframework.boot:spring-boot-starter-web:2.7.2")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	/* jwt */
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	/* lombok */
	implementation("org.projectlombok:lombok:1.18.24")
	/* secutiry */
	implementation("org.springframework.boot:spring-boot-starter-security")

	implementation("org.springdoc:springdoc-openapi-data-rest:1.6.9")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.9")

	runtimeOnly("mysql:mysql-connector-java:8.0.29")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.2")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

