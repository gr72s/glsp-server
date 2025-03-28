version = "0.1.0-SNAPSHOT"

extra["name"] = "Charon Activity Diagram Module"
extra["description"] = "Diagram sub module"

dependencies {
    implementation(project(":server"))
    implementation(project(":server.websocket"))
    implementation(project(":layout"))
    implementation("org.eclipse.elk:org.eclipse.elk.alg.layered:0.8.1") {
        exclude("log4j", "log4j")
    }
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "src/main/workflow-gen")
        }
        kotlin {
            srcDirs("src/main/java", "src/main/workflow-gen")
        }
    }
}