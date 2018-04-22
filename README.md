# Spreadsheet Testdata
[![Build Status](https://travis-ci.org/flpa/swf.svg?branch=master)](https://travis-ci.org/flpa/swf)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=at.technikum.mse.swf%3ASpreadsheetTestdata&metric=alert_status)](https://sonarcloud.io/dashboard?id=at.technikum.mse.swf%3ASpreadsheetTestdata) [![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=at.technikum.mse.swf%3ASpreadsheetTestdata&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=at.technikum.mse.swf%3ASpreadsheetTestdata)  

## About this Project
Spreadsheet Testdata is a library that can generate template spreadsheet files for testdata classes and read the contents as a list of objects of a testdata class. This way programmers can specify a testdata class and generate a template, which can then be filled in by domain experts. The list that the library generates from the filled in spreadsheet file can then be used for testing purposes.

By default Spreadsheet Testdata supports Excel files by utilizing Apache POI.

## Initialize for Development
 
`gradle eclipse`

After running this command the project can be imported into Eclipse.

## Basic Usage
### Writing a Testdata-Class
By default a testdata class can include members of primitive types (except byte) or their corresponding object wrapper classes, as well as Strings. 
The class must contain a constructor with all members as parameters in the order they are defined as members.

#### Using Complex Types
The library also supports members of complex types that must follow the above constraints. In order to include such a member in the template file it must be annotated with the `@Flatten` annotation.

#### Defining Labels for the Template
By default the library uses the member names with a uppercased first letters as labels in the template file. Using the `@Label` annotation on a field level you can specifiy custom labels.

### Generating a Template
A template file can be generated using the following method:
```java
SpreadsheetTestdata spreadsheetTestdata = new SpreadsheetTestdata();
spreadsheetTestdata.createTemplate(new File("testdata.xlsx"), TestDataClass.class);
```

### Reading a Testdata-File
The contents of a testdata file can be read as a list of objects of a testdata class using the following method:
```java
SpreadsheetTestdata spreadsheetTestdata = new SpreadsheetTestdata();
List<TestdataClass> testData = spreadsheetTestdata.read(new File("testdata.xlsx"), TestdataClass.class);
```

### Using Custom Type-Mappers
If you want to use custom type mappers next to or instead of the built-in default type mappers, you can write a class that implements the `TypeMapper` interface and call the following method before reading or generating a file:
```java
spreadsheetTestdata.registerTypeMapper(new CustomTypeMapper(), CustomMappedClass.class);
```
If you also use a custom file mapper, type mappers must be registered after registering the respective file mapper.

### Using Custom File-Mappers
By default the library uses the built-in `PoiFileMapper` which utilizes Apache POI to write and read Excel files.
If you want to use a custom file mapper, you can write a class that implements the `FileMapper` interface and call the following method before reading or generating a file:
```java
spreadsheetTestdata.registerFileMapper(new CustomFileMapper());
```
All registered custom type mappers will be removed when registering a new file mapper. 

## Contributions
Contributions to this open source project are welcome. 
However, since this library is in an early stage there is no dedicated developer documentation beyond this readme and inline comments.

Our preferred workflow for contributions is forking the project and creating pull requests to offer changes via Github. For more information on this process, please refer to [the Github documentation](https://help.github.com/articles/fork-a-repo/).

## License

Copyright (c) 2018 Adelina Teofanescu, Florian Patzl, Ralf Rosskopf
published under the MIT license

This library uses the following software from other open source projects:
- Apache POI 3.17, Copyright (c) The Apache Software Foundation  
  license: The Apache Software License, Version 2.0
- SLF4J, Copyright (c) QOS.ch
  license: The MIT License
  sources: https://www.slf4j.org/download.html

Copies of all licenses can be found in this file's directory.
