## Running the application

To run this application, you'll need to clone it from GitHub [Cloning a repository](https://docs.github.com/en/github/creating-cloning-and-archiving-repositories/cloning-a-repository-from-github/cloning-a-repository)
or download the zip and extract it with your preferred archive tool.


To run the program for testing, use the included `gradlew.bat` (Windows) or `gradlew` (Linux) file.

Using your system's terminal, call gradlew with the bootRun task.

Example (Windows `cmd`):
```
cd C:\Users\USERNAME\IdeaProjects\points\points
gradlew.bat bootRun
```

Example (Linux `bash`):
```bash
cd /home/IdeaProjects/points/
./gradlew bootRun
```

The terminal will display timestamped logs showing the startup. The last line will indicate the program is ready for use.
```
fetch.rewards.points.PointsApplication   : Started PointsApplication in 3.632 seconds (JVM running for 4.04)
```

Note: A progress bar stopped at 80% will be displayed. This is expected behavior while the gradle virtual machine is running the program. Do not close the terminal.

## Using the application

The default host address is localhost:8080.
API Endpoints:
Get - `/points`,
Post - `/transaction` `/spend`

For testing the points endpoint, it can be called directly from a web browser `localhost:8080/points`

For testing the Post API calls, [Postman](https://www.postman.com/) collections are included under src/test/resources. 
These collections mirror the API calls performed in the prompt example sections. These can be used as a template for acceptable RequestBody input.

For help running the collections in Postman, read [Using the Collection Runner](https://learning.postman.com/docs/running-collections/intro-to-collection-runs/).

## Viewing the H2 Database contents
In addition to the API endpoints, the application runs an H2 database. The console is accessible at `localhost:8080/h2-console`.
A list of supported SQL commands is available on the [h2database website.](https://h2database.com/html/commands.html)

![h2-settings](https://user-images.githubusercontent.com/42303117/126254253-4318afa6-906a-4f7f-b91e-dd70ff7f702a.png)

## Troubleshooting
Common problems include:
- `Port 8080 is already in use` - A program is running and already using the port. Close the program using the port and rerun.
- `Whitelabel Error Page` - Attempted to access the default host, or an endpoint that doesn't exist. Consult the list of API endpoints.
