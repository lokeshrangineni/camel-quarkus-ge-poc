# camel-quarkus-ge-poc
This project is having two camel projects. One project is considered as core and other project is extension project. Extension project is having dependency of project core.


```shell
$ cd camel-quarkus-rest-core-example
# Below project builds and deploys the jar to maven local repository.
$ mvn clean install

$ cd ../camel-quarkus-rest-extension-example
$ mvn clean package
$ java -jar target/quarkus-app/quarkus-run.jar
```


