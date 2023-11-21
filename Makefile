start-standalone-grid:
	java -jar .\selenium-server-4.15.0.jar standalone

start-extend-grid:
	java -jar selenium-server-<version>.jar standalone --config config.toml

test:
	mvn test