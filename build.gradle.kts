plugins {
    kotlin("jvm") version "1.9.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.test {
    useJUnitPlatform()
    reports {
        junitXml.required.set(true)
        html.required.set(true)
    }
    testLogging {
        events("passed", "skipped", "failed")
    }
}
