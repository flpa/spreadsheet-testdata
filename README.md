# Excel Spreadsheet Testdata
[![Build Status](https://travis-ci.org/flpa/swf.svg?branch=master)](https://travis-ci.org/flpa/swf)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=at.technikum.mse.swf%3AExcelSpreadsheetTestdata&metric=alert_status)](https://sonarcloud.io/dashboard?id=at.technikum.mse.swf%3AExcelSpreadsheetTestdata) [![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=at.technikum.mse.swf%3AExcelSpreadsheetTestdata&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=at.technikum.mse.swf%3AExcelSpreadsheetTestdata)  

## Initialize for Development
 
`gradle eclipse`

After running this command the project can be imported into Eclipse.

## Basic Usage
### Writing a Testdata-Class
By default a testdata class can only include members of primitive types (except byte) or their corresponding object wrapper classes, as well as Strings. 
The class must contain a constructor with all members as parameters in the order they are defined as members.

### Generating a Template
A template file can be generated using the following method:
```java
LibraryApi libraryApi = new LibraryApi();
libraryApi.createTemplate(new File("testdata.xlsx"), TestDataClass.class);
```

### Reading a Testdata-File
The contents of a testdata file can be read as a list of testdata objects using the following method:
```java
LibraryApi libraryApi = new LibraryApi();
List<TestDataClass> testData = libraryApi.read(new File("testdata.xlsx"), TestDataClass.class);
```

### Using Custom Type-Mappers
If you want to use custom type mappers next to or instead of the built-in default type mappers, you can write a class that implements the `TypeMapper` interface and call the following method before reading or generating a file:
```java
libraryApi.registerTypeMapper(new CustomTypeMapper(), CustomMappedClass.class);
```
If you use also use a custom file mapper type mappers must be registered after registering the respective file mapper.

### Using Custom File-Mappers
By default the library uses the built-in `PoiFileMapper` which utilizes Apache Poi to write and read Excel files.
If you want to use a custom file mapper, you can write a class that implements the interface `FileMapper` and call the following method before reading or generating a file:
```java
libraryApi.registerFileMapper(new CustomFileMapper());
```
All registered custom type mappers will be removed when registering a new file mapper. 

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
