docker-up:
	docker-compose up -d

docker-stop:
	docker-compose stop

docker-down:
	docker-compose down

run:
	@make docker-up
	 sam build
	 sam local start-api --docker-network micronaut_security_serverless_sample_network --warm-containers EAGER

debug:
	@make docker-up
	 sam build
	 sam local start-api --docker-network micronaut_security_serverless_sample_network --warm-containers EAGER --debug-port 5000

build-graalvm:
	./gradlew buildNativeLambda
