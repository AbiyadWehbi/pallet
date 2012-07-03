(ns pallet.crate.package.centos-test
  (:use
   pallet.crate.package.centos
   clojure.test
   [pallet.actions :only [package package-source]]
   [pallet.build-actions :only [build-actions]]
   [pallet.common.logging.logutils :only [logging-threshold-fixture]]))

(use-fixtures :once (logging-threshold-fixture))

(deftest add-repository-test
  (is
   (=
    (first
     (build-actions
         {:phase-context "add-repository"}
       (package "yum-priorities")
       (package-source
        "Centos 5.5 os x86_64"
        :yum
        {:url
         "http://mirror.centos.org/centos/5.5/os/x86_64/repodata/repomd.xml"
         :gpgkey "http://mirror.centos.org/centos/RPM-GPG-KEY-CentOS-5"
         :priority 50})))
    (first
     (build-actions
      {:is-64bit true}
      (add-repository)))))
  (is
   (=
    (first
     (build-actions
         {:phase-context "add-repository"}
       (package "yum-priorities")
       (package-source
        "Centos 5.4 updates i386"
        :yum {:url
              "http://mirror.centos.org/centos/5.4/os/i386/repodata/repomd.xml"
              :gpgkey "http://mirror.centos.org/centos/RPM-GPG-KEY-CentOS-5"
              :priority 50})))
    (first
     (build-actions
      {:is-64bit false}
      (add-repository :version "5.4" :repository "updates"))))))
