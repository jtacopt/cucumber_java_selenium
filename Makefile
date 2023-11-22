start-standalone-grid:
	java -jar .\selenium-server-4.15.0.jar standalone

start-extend-grid:
	java -jar selenium-server-<version>.jar standalone --config config.toml

allure.results.directory:=target/allure-results
test:
	mvn test -Dallure.results.directory=$(allure.results.directory)
