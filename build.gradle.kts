// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) version libs.versions.androidGradlePlugin apply false
    alias(libs.plugins.android.library) version libs.versions.androidGradlePlugin apply false
    alias(libs.plugins.kotlin.android) version libs.versions.kotlin apply false
    alias(libs.plugins.dagger.hilt.android) version libs.versions.hilt apply false
}