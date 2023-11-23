start-standalone-grid:
	java -jar .\selenium-server-4.15.0.jar standalone

print-path:
	echo $(dir $(abspath $(firstword $(MAKEFILE_LIST))))

current_dir := $(dir $(abspath $(firstword $(MAKEFILE_LIST))))

start-event-bus:
	java -jar $(current_dir)selenium-server-4.15.0.jar event-bus

start-session-queue:
	java -jar $(current_dir)selenium-server-4.15.0.jar sessionqueue

start-extend-grid:
	java -jar selenium-server-<version>.jar standalone --config config.toml

allure.results.directory:=target/allure-results
test:
	mvn test -Dallure.results.directory=$(allure.results.directory)

report:
	mvn allure:report