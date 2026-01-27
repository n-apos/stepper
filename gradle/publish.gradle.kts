
apply(plugin = "maven-publish")
apply(plugin = "signing")


tasks.register<Jar>("dokkaJar") {
    archiveClassifier.set("javadoc")
    from(tasks.named("dokkaGenerateHtml"))
}

afterEvaluate {
    configure<PublishingExtension> {
        repositories {
            maven {
                name = "sonatype"
                val isSnapshot = version.toString().endsWith("SNAPSHOT")
                url = if (isSnapshot) {
                    uri("https://central.sonatype.com/repository/maven-snapshots/")
                } else {
                    uri("https://central.sonatype.com/repository/maven-releases/")
                }
                credentials {
                    username = System.getenv("SONATYPE_USERNAME") ?: project.findProperty("sonatypeUsername") as? String
                    password = System.getenv("SONATYPE_PASSWORD") ?: project.findProperty("sonatypePassword") as? String
                }
            }
        }
        publications.withType<MavenPublication> {
            artifact(tasks.named("dokkaJar"))
            artifact(tasks.named("sourcesJar"))
            pom {
                name.set("stepper")
                groupId = "com.n-apos.stepper"
                description.set("A simple stepper library for Compose Multiplatform.")
                url.set("https://github.com/n-apos/stepper")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("n-apos")
                        name.set("N-APOS")
                        email.set("contact@n-apos.com")
                    }
                }
                scm {
                    connection.set("scm:git:github.com/n-apos/stepper.git")
                    developerConnection.set("scm:git:ssh://github.com/n-apos/stepper.git")
                    url.set("https://github.com/n-apos/stepper/tree/main")
                }
            }
        }
    }

    configure<SigningExtension> {
        useGpgCmd()
        val publishing = project.extensions.getByType(PublishingExtension::class.java)
        publishing.publications.withType<MavenPublication>().configureEach {
            sign(this)
        }
    }
}