.PHONY: build-and-deploy
build-and-deploy:
	./gradlew build
	sudo cp ./build/libs/chatapp.war /usr/share/tomcat10/webapps

compose:
	docker-compose down
	docker image rm ws-server
	docker-compose up --build

.PHONY: default
default: build-and-deploy
