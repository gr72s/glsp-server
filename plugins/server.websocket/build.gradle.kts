version = "0.1.0-SNAPSHOT"

extra["name"] = "Charon Activity Diagram Module"
extra["description"] = "Diagram sub module"

dependencies {
    api(project(":server"))
    api(libs.eclipse.jetty.websocket.jakarta.server)
    api("org.eclipse.lsp4j:org.eclipse.lsp4j.websocket.jakarta:0.24.0")

}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "src/main/src-gen")
        }
        kotlin {
            srcDirs("src/main/java", "src/main/src-gen")
        }
    }
}