version = "0.1.0-SNAPSHOT"

extra["name"] = "Charon Activity Diagram Module"
extra["description"] = "Diagram sub module"

dependencies {
    implementation(project(":org.eclipse.glsp.server"))
    implementation(project(":org.eclipse.glsp.server.websocket"))
    implementation(project(":org.eclipse.glsp.layout"))
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
            srcDirs("src/main/java", "src/main/src-gen")
        }
        kotlin {
            srcDirs("src/main/java", "src/main/src-gen")
        }
    }
}