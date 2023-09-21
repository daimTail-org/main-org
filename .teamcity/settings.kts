import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.projectFeatures.gitlabConnection
import jetbrains.buildServer.configs.kotlin.triggers.vcs
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

    vcsRoot(HttpsGitlabComTcqaTestDtsFirstGitRefsHeadsMain1)

    buildType(Build)

    features {
        gitlabConnection {
            id = "PROJECT_EXT_5"
            displayName = "GitLab.com"
            applicationId = "f3d58005ced47daa610fe5bab7f24c68c99ade56afe3567244fb0c941b8743de"
            clientSecret = "credentialsJSON:dd59c551-eb3c-48bb-9a7a-29697c7556e7"
        }
    }
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(HttpsGitlabComTcqaTestDtsFirstGitRefsHeadsMain1)
    }

    steps {
        maven {
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object HttpsGitlabComTcqaTestDtsFirstGitRefsHeadsMain1 : GitVcsRoot({
    name = "https://gitlab.com/tcqa-test/dts-first.git#refs/heads/main (1)"
    url = "https://gitlab.com/tcqa-test/dts-first.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = token {
        userName = "oauth2"
        tokenId = "tc_token_id:CID_466468c2cb41d04e7b45ca8b04f5531f:1:1eeb93e7-50f4-418c-8309-d8511a33a42b"
    }
})
