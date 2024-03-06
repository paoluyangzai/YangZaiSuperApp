plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.yangzai.superutil"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
//必须配置main
sourceSets {
    create("main") {
        java.srcDir("src/main/java")
    }
}
//打包源码
val sourcesJar by tasks.registering(Jar::class) {
    //如果没有配置main会报错
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}
publishing {
    //配置maven仓库
    repositories {
        maven {
            //当前项目根目录
            url = uri("$rootDir")
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(sourcesJar)
            afterEvaluate { artifact(tasks.getByName("bundleReleaseAar")) }
            groupId = "com.yangzai.superapp" //groupId 随便取 , 这个是依赖库的组 id
            artifactId = "superutil" //artifactId 随便取 , 依赖库的名称（jitpack 都不会使用到）
            version = "1.0.0"
        }

    }
}