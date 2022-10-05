# Getting started
To start the application, you need to install [Docker](https://docs.docker.com/get-docker/) first.
Then open a command prompt inside of this folder.
First, build the Docker container using the following command:

	docker build --build-arg JAR_FILE=build/libs/\*.jar -t cwerl/complexityzoo:v0.1.1 .

The following command starts the application at `localhost:8080`:

	docker run -p 8080:8080 cwerl/complexityzoo:v0.1.1

On first launch, you can register an account under `localhost:8080/register`. After this, this is not possible anymore without inviting users.

The login data for the console, which can be reached under `localhost:8080/h2-console` are stored in `/src/main/application.properties`.

The whole backend code is stored in `/src/main/java/de/cwerl/complexityzoo` and the frontend code is stored in `/src/main/resources`.
