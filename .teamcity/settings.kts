import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.schedule

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2022.04"

project {

    buildType(EndBuild)
    buildType(BeginBuild)
    buildType(MiddleBuild)
}

object BeginBuild : BuildType({
    name = "1beginBuild"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }
})

object EndBuild : BuildType({
    name = "3endBuild"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            scriptContent = """echo "That's all, Folks!""""
        }
    }

    triggers {
        schedule {
            schedulingPolicy = daily {
                hour = 18
                minute = 15
            }
            triggerBuild = always()
            withPendingChangesOnly = false
            param("revisionRuleDependsOn", "Tw79771_MiddleBuild")

            enforceCleanCheckout = true
            enforceCleanCheckoutForDependencies = true
        }
    }

    dependencies {
        dependency(MiddleBuild) {
            snapshot {
                reuseBuilds = ReuseBuilds.ANY
            }

            artifacts {
                artifactRules = "*.jar"
            }
        }
    }
})

object MiddleBuild : BuildType({
    name = "2middleBuild"

    artifactRules = "target/"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            goals = "clean package"
        }
    }

    dependencies {
        snapshot(BeginBuild) {
            reuseBuilds = ReuseBuilds.ANY
        }
    }
})
