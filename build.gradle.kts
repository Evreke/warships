val ktorVersion: String by project
val kotlinVersion = "1.6.21"
val logbackVersion: String by project

plugins {
  kotlin("multiplatform") version "1.6.21"
  kotlin("plugin.serialization") version "1.6.21"
  id("org.jetbrains.compose") version "1.2.0-alpha01-dev683"
  application
}

group = "ru.evreke"
version = "0.0.1"

repositories {
  mavenCentral()
  maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
  maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
  google()
}

kotlin {
  jvm {
    withJava()
  }
  js(IR) {
    browser { }
    binaries.executable()
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
      }
    }

    val commonTest by getting {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-test-common:$kotlinVersion")
        implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:$kotlinVersion")
      }
    }

    val jvmMain by getting {
      dependencies {
        implementation("io.ktor:ktor-serialization:$ktorVersion")
        implementation("io.ktor:ktor-server-core:$ktorVersion")
        implementation("io.ktor:ktor-server-netty:$ktorVersion")
        implementation("io.ktor:ktor-server-websockets:$ktorVersion")
        implementation("ch.qos.logback:logback-classic:$logbackVersion")
        implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
        implementation("io.ktor:ktor-server-cors:$ktorVersion")
        implementation("io.ktor:ktor-server-compression:$ktorVersion")
        implementation(compose.runtime)
      }
    }

    val jvmTest by getting {
      dependsOn(commonTest)
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
      }
    }

    val jsMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-js:$ktorVersion")
        implementation("io.ktor:ktor-client-json:$ktorVersion")
        implementation("io.ktor:ktor-client-serialization:$ktorVersion")
        implementation("io.ktor:ktor-client-websockets:$ktorVersion")

        implementation(compose.web.core)
        implementation(compose.runtime)
      }
    }

    val jsTest by getting {
      dependsOn(commonTest)
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-test-common:$kotlinVersion")
        implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:$kotlinVersion")
      }
    }
  }
}

application {
  mainClass.set("ru.evreke.ApplicationKt")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

tasks {
  getByName<ProcessResources>("jvmProcessResources") {
    dependsOn(getByName("jsBrowserProductionWebpack"))
    val jsBrowserProductionWebpack = getByName<org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack>("jsBrowserProductionWebpack")
    from(File(jsBrowserProductionWebpack.destinationDirectory, jsBrowserProductionWebpack.outputFileName))
  }

  getByName<JavaExec>("run") {
    dependsOn(getByName<Jar>("jvmJar"))
    classpath(getByName<Jar>("jvmJar"))
  }
}
