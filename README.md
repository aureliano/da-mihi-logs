Da mihi logs
=======

### Overview
Da mihi logs is an API that aims to facilitate log events collecting tasks. It provides a sort of resources in order to take a source log data and send it to a repository with different format. Among other features, it allows log monitoration by tailing one or more log files, schedule log files reading or downloading files to parse and output them to a data repository.

Despite you can use it as an application for collecting and sending log events, its first purpose is providing a framework which will let you control what is got from input source and what is sent to output source. That means you have matchers to take events from an input source, parsers, filters for business objects and listeners for all events in the process of reading and writing data.

For those that wants to use business objects to work with ElasticSearch there is an implementation that provides annotations for serializing and sending them to ElasticSearch. For more details take a look at [ElasticSearch OSEM](https://github.com/kzwang/elasticsearch-osem) (ElasticSearch object search engine mapping).

There is also support for scheduling execution. So you can configure it to execute tasks like a cron application. Beyond, there's more features like report generation, directory cleaning and so forth.

### Documentation
Visit the wiki page in order to get access to documentation. https://github.com/aureliano/da-mihi-logs/wiki

### Installation
Unfortunatelly this API is not available at Maven Central Repository yet. So at this point you have to install it manually.

Clone this repository with GIT `git clone https://github.com/aureliano/da-mihi-logs.git` or download source code from release `https://github.com/aureliano/da-mihi-logs/releases/tag/x.x.x`, or even getting the edge source from `https://github.com/aureliano/da-mihi-logs/archive/master.zip`. Extract files and go to project directory. Install locally with Maven by typing `mvn install`.

After installation from source code all you have to do is add this dependency tag.
```xml
<dependency>
  <groupId>com.github.aureliano</groupId>
  <artifactId>da-mihi-logs</artifactId>
  <version>x.x.x</version>
</dependency>
```

=======
License - [MIT](https://github.com/aureliano/da-mihi-logs/blob/master/LICENSE)
