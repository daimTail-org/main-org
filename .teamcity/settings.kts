import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
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

    vcsRoot(HttpsBbdatacenterQaTeamcityComScmDaimDaimtsJunitGitRefsHeadsMain)
    vcsRoot(HttpsGitlabComTcqaTestDtsFirstGitRefsHeadsMain)
    vcsRoot(HttpsDaimTsygBitbucketOrgDaimtsygSimpleJunitGitRefsHeadsMain)
    vcsRoot(HttpsGitlabComTcqaTeamDtsSecondGitRefsHeadsMain)

    buildType(First)
}

object First : BuildType({
    name = "first"

    vcs {
        root(HttpsGitlabComTcqaTestDtsFirstGitRefsHeadsMain)
        root(HttpsGitlabComTcqaTeamDtsSecondGitRefsHeadsMain)
        root(HttpsDaimTsygBitbucketOrgDaimtsygSimpleJunitGitRefsHeadsMain)
        root(DslContext.settingsRoot)
        root(HttpsBbdatacenterQaTeamcityComScmDaimDaimtsJunitGitRefsHeadsMain)
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
        commitStatusPublisher {
            vcsRootExtId = "${HttpsGitlabComTcqaTestDtsFirstGitRefsHeadsMain.id}"
            publisher = gitlab {
                gitlabApiUrl = "https://gitlab.com/api/v4/"
            }
            param("tokenId", "tc_token_id:CID_8e04b49e66d6912ae30e9a3eda807dcf:1:669cccd8-9f3d-4976-bc33-f16ba485be30")
            param("authType", "storedToken")
        }
        commitStatusPublisher {
            vcsRootExtId = "${HttpsDaimTsygBitbucketOrgDaimtsygSimpleJunitGitRefsHeadsMain.id}"
            publisher = bitbucketCloud {
                authType = storedToken {
                    tokenId = "tc_token_id:CID_f4cb672de564546697cce8c3054cec9a:1:8f0dcf46-b9ca-4e87-a5b3-46793f40189d"
                }
            }
        }
        commitStatusPublisher {
            vcsRootExtId = "${DslContext.settingsRoot.id}"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "credentialsJSON:7948a9c9-1a47-4f3c-a3b1-ab177aebc5cb"
                }
            }
        }
        commitStatusPublisher {
            vcsRootExtId = "${HttpsBbdatacenterQaTeamcityComScmDaimDaimtsJunitGitRefsHeadsMain.id}"
            publisher = bitbucketServer {
                url = "https://bbdatacenter.qa.teamcity.com"
                authType = storedToken {
                    tokenId = "tc_token_id:CID_0068ce16fc1dd8ddb6d870099cc31113:1:c9694b5b-0bc1-46a8-8301-1c1bb6332026"
                }
            }
        }
    }
})

object HttpsBbdatacenterQaTeamcityComScmDaimDaimtsJunitGitRefsHeadsMain : GitVcsRoot({
    name = "https://bbdatacenter.qa.teamcity.com/scm/daim/daimts-junit.git#refs/heads/main"
    url = "https://bbdatacenter.qa.teamcity.com/scm/daim/daimts-junit.git"
    branch = "refs/heads/main"
    authMethod = token {
        userName = "admin"
        tokenId = "tc_token_id:CID_0068ce16fc1dd8ddb6d870099cc31113:1:c9694b5b-0bc1-46a8-8301-1c1bb6332026"
    }
})

object HttpsDaimTsygBitbucketOrgDaimtsygSimpleJunitGitRefsHeadsMain : GitVcsRoot({
    name = "https://daimTsyg@bitbucket.org/daimtsyg/simple-junit.git#refs/heads/main"
    url = "https://daimTsyg@bitbucket.org/daimtsyg/simple-junit.git"
    branch = "refs/heads/main"
    authMethod = token {
        userName = "daimTsyg"
        tokenId = "tc_token_id:CID_f4cb672de564546697cce8c3054cec9a:1:8f0dcf46-b9ca-4e87-a5b3-46793f40189d"
    }
})

object HttpsGitlabComTcqaTeamDtsSecondGitRefsHeadsMain : GitVcsRoot({
    name = "https://gitlab.com/tcqa-team/dts-second.git#refs/heads/main"
    url = "https://gitlab.com/tcqa-team/dts-second.git"
    branch = "refs/heads/main"
    authMethod = token {
        userName = "oauth2"
        tokenId = "tc_token_id:CID_b57b41be2d2391dd9cdb0b879628ae55:1:2b91ef33-2314-4ae9-a642-9ad865e0cfd4"
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
