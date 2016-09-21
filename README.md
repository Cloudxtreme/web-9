# web
Web Tools

## Startup
 
	$ cat run.sh 
	cd web
	git reset --hard && git pull 
	cp -r ../config/* src/main/resources/
	mvn package && java -jar target/deploy-0.0.1-SNAPSHOT.jar
