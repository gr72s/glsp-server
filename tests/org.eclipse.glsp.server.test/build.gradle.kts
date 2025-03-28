version = "0.1.0-SNAPSHOT"

extra["name"] = "Charon Activity Diagram Module"
extra["description"] = "Diagram sub module"

dependencies {
    implemention(project(":server"))
    implemention("org.junit.jupiter:junit-jupiter-engine:5.7.1")
    implemention("org.junit.jupiter:junit-jupiter-params:5.7.1")
}

tasks.test {
    useJUnitPlatform()
}