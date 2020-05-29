# Metanome

[![Build Status](https://travis-ci.org/HPI-Information-Systems/Metanome.png?branch=master)](https://travis-ci.org/HPI-Information-Systems/Metanome)
[![Coverage Status](https://coveralls.io/repos/HPI-Information-Systems/Metanome/badge.png)](https://coveralls.io/r/HPI-Information-Systems/Metanome)

The [Metanome project](https://hpi.de/naumann/projects/data-profiling-and-analytics/metanome-data-profiling.html) is a joint project between the Hasso-Plattner-Institut ([HPI](http://www.hpi.de)) and the Qatar Computing Research Institute ([QCRI](http://www.qcri.org)). Metanome provides a fresh view on data profiling by developing and integrating efficient algorithms into a common tool, expanding on the functionality of data profiling, and addressing performance and scalability issues for Big Data. A vision of the project appears in SIGMOD Record: "[Data Profiling Revisited](http://hpi.de/naumann/publications/publications-by-type/year/2013/102276/Nau13.html)". Please find the the BibTex, EndNote, and ACM Ref citations for referencing the Metanome project in scientific works on the [Metanome project](https://hpi.de/naumann/projects/data-profiling-and-analytics/metanome-data-profiling.html) website.

The Metanome tool is supplied under Apache License. You can use and extend the tool to develop your own profiling algorithms. The profiling algorithms, which we provide on our [Algorithm releases page](https://hpi.de/naumann/projects/data-profiling-and-analytics/metanome-data-profiling/algorithms.html) and the [metanome-algorithms](https://github.com/HPI-Information-Systems/metanome-algorithms) repository, have Apache copyright as well.

#### Building Metanome Locally

Metanome is a java maven project. So in order to build the sources, the following development tools are needed:

1. Java JDK 1.8 or later
2. Maven 3.1.0
2. Git

Make sure that all three are on your system's PATH variable when running the build.

##### Build Metanome

Metanome can be build by executing:

```
mvn clean install
```

Note that if Metanome has not been installed before creating the package (via mvn clean install), dependencies will be retrieved online, which can result in a deprecated package!

#### Downloads
All Metanome releases can be found on the [Metanome releases page](https://github.com/HPI-Information-Systems/Metanome/releases).

Current profiling algorithms are available at the [Algorithm releases page](https://hpi.de/naumann/projects/data-profiling-and-analytics/metanome-data-profiling/algorithms.html). The sources of all these algorithms are available on GitHub in the [metanome-algorithms](https://github.com/HPI-Information-Systems/metanome-algorithms) repository.

#### Developing a profiling algorithm for Metanome
If you want to build your own profiling algorithm for the Metanome tool, the best way to get started is our [Skeleton Project](https://hpi.de/fileadmin/user_upload/fachgebiete/naumann/projekte/repeatability/DataProfiling/Metanome/MetanomeAlgorithmSkeleton.zip). It contains an algorithm frame and a test runner project, with which you can run and test your code (without a running Metanome tool instance). For more details, check out the contained README.txt file.

Since many profiling algorithms use similar techniques for the discovery of dependencies, its worth checking out the following resources as well:

* [metanome-algorithms](https://github.com/HPI-Information-Systems/metanome-algorithms) including many implementations of novel and popular profiling algorithms for various types of metadata.
* [Metanome-Data-Structures](https://github.com/jakob-zwiener/Metanome-Data-Structures) including, for instance, position list indexes (PLIs), which many algorithms use for candidate validation; see also [pli-benchmarks](https://github.com/jakob-zwiener/pli-benchmarks)

#### Documentation
The Metanome tool, information for algorithm developers and contributors to the project can be found in the [GitHub wiki](https://github.com/HPI-Information-Systems/Metanome/wiki).

#### Deploy Metanome Remote
It is possible to deploy Metanome using PaaS providers like (Amazon Beanstalk, Heroku or Google App Engine).
We provide additional configs and documentation how to deploy Metanome on these in the [GitHub wiki](https://github.com/HPI-Information-Systems/Metanome/wiki).

#### Development
The Metanome modules are continuously deployed to Sonatype and can be used by adding the repository:
```xml
<repositories>
    <repository>
        <id>snapshots-repo</id>
        <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
</repositories>
```

#### Git Commit Hooks
The project is using [license-maintainer](https://github.com/NitorCreations/license-maintainer) as Pre-Commit Git Hook to keep the license information in all Java, XML and Python files up to date. To use it you have to execute the ```./add_hooks.sh``` shell script which is creating an pre-commit hook symlink to the license-maintainer script.

##### Coding style
The project follows the google-styleguide please make sure that all contributions adhere to the correct format. Formatting settings for common ides can be found at: http://code.google.com/p/google-styleguide/
All files should contain the apache copyright header. The header can be found in the ```COPYRIGHT_HEADER``` file.
