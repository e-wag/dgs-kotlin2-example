import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("kapt") version "1.7.10"
    id("com.netflix.dgs.codegen") version "5.2.5"
}

group = "com.ewag.dgs.kotlin2"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:5.0.5"))

    implementation("org.mapstruct:mapstruct:1.5.2.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.2.Final")
    kapt("com.ewag.mapstruct.netflix.dgs.spi:mapstruct-netflix-dgs-spi:1.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
    generateKotlinNullableClasses = true
    generateKotlinClosureProjections = true

    doFirst {
        packageName = "${project.group}.dgs.generated"
        schemaPaths = mutableListOf("${project.projectDir}/src/main/resources/graphql")
    }

    // Without the following line DGS Code Generation won't generate classes
    // after changes inside the schema
    outputs.doNotCacheIf(
        "Problems with code generation",
        { true } // -> true = ALWAYS
    )
}
