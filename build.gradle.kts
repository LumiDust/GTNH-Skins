plugins {
    id("com.gtnewhorizons.gtnhconvention")
}

version = "1.0.0"

tasks.withType<Jar>().configureEach {
    from("LICENSE")
}
