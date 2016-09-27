
# web
Web Tools

## Startup
 
 	git clone https://github.com/oscm/web.git
 	
 	mkdir -p workspace/netkiller.cn/{www,news,bbs}.netkiller.cn/{development,testing,production}
 	
 	
	$ cat run.sh 
	cd web
	git reset --hard && git pull 
	cp -r ../config/* src/main/resources/
	mvn package && mvn spring-boot:run 
	
	$ cat startup.sh
	java -jar target/deploy-0.0.1-SNAPSHOT.jar &
