rootProject.name = "glsp-server"

pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/gradle-plugin")
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":graph")
project(":graph").projectDir = file("plugins/graph")
include(":layout")
project(":layout").projectDir = file("plugins/layout")
include(":server")
project(":server").projectDir = file("plugins/server")
include(":server.emf")
project(":server.emf").projectDir = file("plugins/server.emf")
include(":server.websocket")
project(":server.websocket").projectDir = file("plugins/server.websocket")

include(":example.workflow")
project(":example.workflow").projectDir = file("examples/example.workflow")