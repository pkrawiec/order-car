FROM jenkinsci/blueocean
 
RUN /usr/local/bin/install-plugins.sh git matrix-auth workflow-aggregator docker-workflow credentials-binding
 
ENV JENKINS_USER admin
ENV JENKINS_PASS admin
 
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false