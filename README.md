Event Bridge
=======

### Overview
Event Bridge is an API that aims to facilitate log events collecting tasks. It provides a sort of resources in order to take a source log data and send it to a repository with different format. Among other features, it allows log monitoring by tailing one or more log files, schedule log files reading or downloading files to parse and output them to a data repository.

Despite you can use it as an application for collecting and sending log events, its first purpose is providing a framework which will let you control what is got from input source and what is sent to output source. That means you have matchers to take events from an input source, parsers, filters for business objects and listeners for all events in the process of reading and writing data.

For those that wants to use business objects to work with ElasticSearch there is an implementation that provides annotations for serializing and sending them to ElasticSearch. For more details take a look at [ElasticSearch OSEM](https://github.com/kzwang/elasticsearch-osem) (ElasticSearch object search engine mapping).

There is also support for scheduling execution. So you can configure it to execute tasks at specific time or periodically. Beyond, there's more features like report generation, directory cleaning and so forth.

### Documentation
Visit the Wiki page in order to get access to documentation. https://github.com/aureliano/evt-bridge/wiki

### Installation
Unfortunately this API is not available at Maven Central Repository yet. So at this point you have to install it manually.

Clone this repository with GIT `git clone https://github.com/aureliano/evt-bridge.git` or download source code from release `https://github.com/aureliano/evt-bridge/releases/tag/x.x.x`, or even getting the edge source from `https://github.com/aureliano/evt-bridge/archive/master.zip`. Extract files and go to project directory. Install locally with Maven by typing `mvn install`.

After installation from source code all you have to do is adding properly dependency tags.
```xml
<dependency>
  <groupId>com.github.aureliano</groupId>
  <artifactId>${agent}-${input/output}</artifactId>
  <version>x.x.x</version>
</dependency>
```
As an example you may consider an application that consume logs from standard input and sends it to standard output.
```xml
<dependency>
  <groupId>com.github.aureliano</groupId>
  <artifactId>standard-input</artifactId>
  <version>x.x.x</version>
</dependency>

<dependency>
  <groupId>com.github.aureliano</groupId>
  <artifactId>standard-output</artifactId>
  <version>x.x.x</version>
</dependency>
```

### Command line
In order to comply with those who want a basic usage - not to get involved with Java coding - or just testing there is a command line interface available on $PROJECT_HOME/target/application path after installation. Read more at [doc page](https://github.com/aureliano/evt-bridge/wiki/Command-line-application).

=======
License - [MIT](https://github.com/aureliano/evt-bridge/blob/master/LICENSE)
