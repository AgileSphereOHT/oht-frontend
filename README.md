# OHT Rina Registration Frontend

# GitLab
https://oh-alpha-confluence.atlassian.net/wiki/display/OHA/Accessing+project+repos

##### Install homebrew (Type into Terminal)
- /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

##### Install Java SDK (Type into Terminal)
- brew update
- brew cask install java

##### IDE
- We use IntelliJ from JetBrains https://www.jetbrains.com/idea/
You can use other IDE if you wish, e.g. Eclipse, but make sure the formatting of code follow our standards.

##### Lombok
We use Lombok https://projectlombok.org/ which adds annotations to Java and simplify our code.
To allow IntelliJ to support Lombok install the plugin:
- Preferences > Plugins > Browse Repositories... > search Lombok > Install
- Preferences > search Annotation processes (ensure enabled for all)

##### JIRA
- https://agilesphereoht.atlassian.net

##### Confluence
- https://oh-alpha-confluence.atlassian.net/wiki/display/OHA/Overseas+Healthcare+Alpha

##### Default Users
- ramesh/ramesh123
- june/june123
- heather/heather123

# Artifactory repository access
#### Certificate
##### Download the self-signed cert on dev box
- echo -n | openssl s_client -connect ohartidev.overseashealthcaredev.co.uk:443 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > *overseashealthcaredev.co.uk.cer

##### Import into keystore
- sudo $JAVA_HOME/bin/keytool -import -keystore $JAVA_HOME/jre/lib/security/cacerts -alias overseashealthcaredev -file *.overseashealthcaredev.co.uk.cer
- password=changeit

##### Add to gradle properties (USERHOME/.gradle/gradle.properties)
- artifactory_user=your.artifactory.username
- artifactory_password=your.artifactory.password
  - login to artifactory click your user name top right, unlock page and copy Encrypted Password field
- org.gradle.daemon=true
- artifactory_url=https://ohartidev.overseashealthcaredev.co.uk/artifactory/
- org.gradle.jvmargs=-Djavax.net.ssl.keyStore=path.to.your.java(needs to match - java used in gradle IntelliJ)/jre/lib/security/cacerts -Djavax.net.ssl.keyStoreType=JKS -Djavax.net.ssl.keyStorePassword=changeit


