# Spring Boot EAR with Skinny WARs

An example project showing the problems deploying Spring Boot applications to Wildfly in an EAR deployment using skinny WARs.

This project consists of two web applications using Spring Boot, `web1` and `web2`, and an EAR containing the two applications
packaged as skinny WARs to avoid duplicating the dependencies and to speed up deployment.

## Problems

1. Wildfly does not find `SpringServletContainerInitializer` if it is in the EAR `lib` directory. This is 
discussed on https://issues.jboss.org/browse/WFLY-4205 and https://jira.spring.io/browse/SPR-12555. A fix should be in 
9.0.0.Beta1 upwards, but it did not work for me.
So I had to create  the `servlet-container-initializer-meta-inf` module and force it to be included in each `WEB-INF/lib` directory.
2. When `SpringServletContainerInitializer` is called it does not find Spring Boot `WebApplicationInitializer` classes in
`WEB-INF/classes`. When it interrogates the class loader, it only finds classes from the EAR `lib` folder. However, the 
WAR classes are on the class path and are found by Spring once it starts up. As a temporary work-around, I created a common Spring Boot
application class and included it as a common JAR.

## Further work

Spring loads a completely separate context for each WAR. I would like it to share a root context to speed up loading and allow
for shared components. There is an unanswered question on Stack Overflow http://stackoverflow.com/questions/31667392/spring-boot-ear-packaging and
I have not yet found a solution.

## Install

1. Download Wildfly 9.0.2.Final
2. Build the project using `mvn clean package`
3. Deploy the web-all.ear file to Wildfly.

You should be able to hit http://localhost:8080/web1 and http://localhost:8080/web2
