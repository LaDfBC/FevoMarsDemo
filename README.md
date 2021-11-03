# FevoMarsDemo
Programming Challenge - Mars Images API - for FEVO

Compiled and tested on Pop OS! with SDK Amazon Coretto 11.0.11.  Due to the short time constraint
on this project, I made no attempt to run in any other environment.  This would be one of the
first things to test if given more time.

## Running
You're going to want maven.  Yes, you can make this work with `javac` and `java` commands but that will drive you crazy.

### Pulling in Maven
These are basically different forms of using a package manager to install maven.  
You can also download it manually here: https://maven.apache.org/download.cgi

**Ubuntu-like Systems**: ```sudo apt install maven``` <br><br>
**Debian-like Systems**: ```sudo yum install maven``` <br><br>
**Mac**: ```brew install maven``` If you don't have Homebrew, you will probably need to install Maven manually.<br><br>
**Windows**: I think you need to install maven manually using the above link

### Execute the Main Method
Get an API Key from NASA, here: https://api.nasa.gov/ and then copy it.

Now it's easy!  On all systems: ```mvn package``` <br><br>
then <br><br>
```java -cp target/FevoMarsDemo-1.0-SNAPSHOT-jar-with-dependencies.jar com.georgemausshardt.MarsImagesApplication YOURAPI_KEY_HERE```

**DON'T FORGET TO REPLACE WITH YOUR OWN API KEY** <br>
(You can also use "DEMO_KEY" for testing or one-off runs!)

If you run into issues, make sure your system didn't name the jar differently.  There's no easy way to force it on all
Operating Systems but the above will **probably** work.  If it doesn't, do an `ls target` and check what the jar is named.
You're looking for the one that ends with `jar-with-dependences.jar`.


## Testing
```mvn test```

Yep, that easy.  If you just want to do this part, see "Pulling in Maven" in the section above.


## Considerations
-- This is in Java.  Not Python, Ruby, etc.  Python makes HTTP requests a breeze and would probably have been quicker to get through
the main REST client phase. However, I've found that weakly typed languages tend to be harder to maintain and read at the
enterprise level, so I went with a language that enforces types and contains a solid inheritance model.

-- Do I think that choice was the best now that I'm done?  Maybe - I'm not sure.  Python would have made the HTTP logic
smoother and cleaner but the class structure would have been a little more confusing.  I do know that I considered doing it
in Common Lisp as I was talking to my first interviewer at FEVO about it.  Very glad I did **NOT** do that!

-- The correct way to store an API key is to put it in a Secret Manager like Vault or one of the offerings from AWS/GCP, etc.
For the purposes of time, it's just read in from the Command Line here but this is one of the first improvements I'd make.

-- **I think this should be a REST API itself** as mentioned several times below.  Adding features would be easiest that way.
It pains me a little to leave it as a command line project with few features but the time constraint demands it.

-- This project uses both Maven and JUnit.  While I acknowledge that both of these could be 
considered frameworks and are thus not to be used as per the assignment instructions, JUnit
is explicitly allowed as a testing framework and Maven provides a simple way to pull down
the libraries used in the project. If you wish to see this project run without 
Maven, manually download the libraries and add them to your classpath, then run the tests as described above.

-- I tried to be as light as possible in my usage of external libraries.  JUnit and Jackson ended up being unavoidable,
the first as mentioned above as a testing library and the second because there is no way I'm getting a JSON encoder and 
decoder to work in under 4 hours and if I was doing this for real, I'd use Jackson (or maybe GSON) anyway!

-- As mentioned a little below, the relatively strict time constraint forced choices as to what portions of this
project to apply "polish" to.  I decided that documentation and testing were more important than including more features
as I could simply explain here what the next features added would be.

## Next Steps
-- A real caching solution.  In-Memory with a Map is fine for demo purposes but will not hold up long term nor under pressure.

-- A real HTTP Request library.  I tried to stay away from 3rd party libraries as the wording of the project requirements
suggested that as much as possible should be done from scratch, by me.  I'd probably use the 
[Apache HTTP Client](https://hc.apache.org/httpcomponents-client-5.1.x/) or Springboot dependencies. 

-- Command line args to control how many days back to get, how many photos to get per day, time ranges (start and end day),
different rovers on command, different cameras on command, etc.  OR see the comment about making this a REST API which is 
the real best answer but takes more time to set up than the 4 hours I had.

-- Injection. Define the cache, API, and some constants and inject them, rather than making it a private member variable.

-- Pull API Key from a Secrets Manager rather than asking for it on the Command Line

-- NotNull and Nullable annotations as per the [Javax library](https://docs.oracle.com/javaee/7/api/javax/validation/constraints/NotNull.html).
I've found these "Documentation Annotations" to be extremely useful in both hunting down issues and in teaching younger
developers how to watch out for problematic function calls and parameters.

-- Along with that, Preconditions checks would be a great thing to add.  Null checks especially in case the API starts
acting funny or giving weird responses to catch those issues quickly.

-- Springboot!  This could be a REST API that encapsulates the NASA API quite easily.  Springboot is **definitely** a framework
though, and as such was banned from this assignment.  GET requests with error codes would go a long way to improving this
project.

-- More testing.  I held myself strictly to the four-hour time limit, favoring documentation first and
tons of unit tests second.  In general though, I would have never let this get through a Pull Request for lack of unit
and integration tests.  Tests could wrap the API itself and each of the individual functions in it + some tests around the
JSON classes to make sure they work properly in the case of nulls etc.

