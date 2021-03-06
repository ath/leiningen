(ns leiningen.deploy
  "Build and deploy jar to remote repository."
  (:require [lancet.core :as lancet])
  (:use [leiningen.core :only [abort repositories-for]]
        [leiningen.jar :only [jar]]
        [leiningen.pom :only [pom snapshot?]]
        [clojure.java.io :only [file]]))

(declare make-model make-artifact)

(defn- make-maven-project [project]
)

(defn- get-repository [project repository-name]
  (let [deploy-repositories (repositories-for project :kind :deploy-repositories)
        repositories (repositories-for project)
        repository (or (deploy-repositories repository-name) 
                       (repositories repository-name)
                       {:url repository-name})]
    #_(make-repository [repository-name repository])))

(defn deploy
  "Build jar and deploy to remote repository.

The target repository will be looked up in :repositories: snapshot
versions will go to the repo named \"snapshots\" while stable versions
will go to \"releases\". You can also deploy to another repository
in :repositories by providing its name as an argument.

  :repositories {\"java.net\" \"http://download.java.net/maven/2\"
                 \"snapshots\" {:url \"https://blueant.com/archiva/snapshots\"
                                :username \"milgrim\" :password \"locative\"}
                 \"releases\" {:url \"https://blueant.com/archiva/internal\"
                               :private-key \"etc/id_dsa\"}}

You can set authentication options keyed by repository name in
~/.lein/init.clj to avoid checking sensitive information into source
control:

  (def leiningen-auth {\"https://blueant.com/archiva/internal\"
                       {:passphrase \"vorpalbunny\"}})
"
  ([project repository-name])
  ([project]
     (deploy project (if (snapshot? project)
                       "snapshots"
                       "releases"))))
