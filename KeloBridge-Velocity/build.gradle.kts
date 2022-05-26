plugins {
    kotlin("jvm") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.koisv"
version = "0.2-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.0.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.0.1")
    implementation(kotlin("stdlib"))
    implementation("com.mojang:brigadier:1.0.18")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
        filteringCharset = "UTF-8"
    }
    shadowJar {
        archiveClassifier.set("build")
    }
    create<Copy>("velo-dist") {
        from (shadowJar)
        into(".\\")
    }
}