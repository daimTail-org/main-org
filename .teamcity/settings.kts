import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

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

version = "2023.05"

project {

    vcsRoot(HttpsGitlabComTcqaTestDtsFirstGitRefsHeadsMain)

    buildType(MainBuild)
}

object MainBuild : BuildType({
    name = "main build"

    vcs {
        root(HttpsGitlabComTcqaTestDtsFirstGitRefsHeadsMain)
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }

    features {
        commitStatusPublisher {
            vcsRootExtId = "${DslContext.settingsRoot.id}"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = vcsRoot()
            }
        }
        commitStatusPublisher {
            vcsRootExtId = "${HttpsGitlabComTcqaTestDtsFirstGitRefsHeadsMain.id}"
            publisher = gitlab {
                gitlabApiUrl = "https://gitlab.com/api/v4"
                accessToken = "credentialsJSON:06695c17-4efd-48e9-9083-b37475145e1e"
            }
            param("authType", "token")
        }
    }
})

object HttpsGitlabComTcqaTestDtsFirstGitRefsHeadsMain : GitVcsRoot({
    name = "https://gitlab.com/tcqa-test/dts-first.git#refs/heads/main"
    url = "https://gitlab.com/tcqa-test/dts-first.git"
    branch = "refs/heads/main"
    authMethod = token {
        userName = "oauth2"
        tokenId = "tc_token_id:CID_8e04b49e66d6912ae30e9a3eda807dcf:1:669cccd8-9f3d-4976-bc33-f16ba485be30"
    }
})
