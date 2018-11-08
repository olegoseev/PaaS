# Password as a Service

This is a simple web service that expose the users and groups information on UNIX-like systems. the service reading files /etc/passwd and /etc/group to obtain the information.

## Create an executable jar out of the project

The project was build using maven. In order to create a deployable jar, download the source files, navigate to the root folder of the project and execute maven comman

```
mvn clean package
```

Result of execution of the command above will be _**passwd-as-service-1.0.0.jar**_ file in _**target**_ direcotry

To execute unit tests only, use command

```
mvn test
```

## How to use it

The service is a simple spring-boot rest application can be launched from the command line. Optionally desired server port can be passed as an argument

```
java -jar passwd-as-service-1.0.0.jar [--server.port=8899]
```

By default the service is pooling the information from files /etc/passwd and /etc/group for user and group information respectively.
Alternatively files locations can be specified via passing command line arguments

```
java -jar passwd-as-service-1.0.0.jar --user.records=/etc/passwd --group.records=/etc/group
```

### Service API

The service provides a read-only access to the user and group information, so it accepts only HTTP GET requests.

The service supports following requests (let's assume the service is running locally):

#### Getting information of all users on the system

```
http://localhost:8080/users
```

#### Get a list of users matching to specified query fields

```
http://localhost:8080/users/query?[name=<nq>][&uid=<uq>][&gid=<gq>][&comment=<cq>][&...]
```

any combinations of following parameters can be specified in the query:

- name
- uid
- gid
- comment
- home
- shell

#### Return information for a single user

```
http://localhost:8080/users/<uid>
```

Return a single user information for the give uid

#### Get a list of groups where the user is member of

```
http://localhost:8080/users/<uid>/gropus
```
Return a list of groups for a single user with a given uid

#### Get a list of all groups on the system

```
http://localhost:8080/groups
```

#### Get a list of groups matching to a specified query fields

```
http://localhost:8080/groups/query?[name=<nq>][&gid=<gq>][&member=<mq1>][&member=<mq2>][&...]
```

any combinations of following parameters can be specified in the query:

- name
- gid
- member (repeated. if more than one member needs to search for)


#### Return information for a single group

```
http://localhost:8080/groups/<gid>
```

Return a group for a given gid

### Service respond data formant

On all type of requests, the service respond with a JSON representation.

Request executed successfully. Fields _**status**_ and _**success**_ show "OK" status. Contents of filed _**data**_ vary, depends on what was requested

```json
{
    "status": "OK",
    "success": "OK",
    "data": [
        {
            "name": "root",
            "uid": 0,
            "gid": 0,
            "comment": "root",
            "home": "/root",
            "shell": "/bin/bash"
        },
        {
            "name": "daemon",
            "uid": 1,
            "gid": 1,
            "comment": "daemon",
            "home": "/usr/sbin",
            "shell": "/usr/sbin/nologin"
        }
        .
        .
        .
    ]
}
```

There is an error occurred during processing the request. Fields will indicate what type of error occurred

```json
{
    "status": "error",
    "error": "NOT_FOUND",
    "message": "User not found"
}
```

### Conclusion

This is a simple spring-boot web service written as an exercise and provides no security or validation of who is using it.

### Licence

This project is licensed under the terms of the MIT license.