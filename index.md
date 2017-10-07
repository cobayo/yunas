[Yunas Tutorial(Settings)](./index.md) | [Yunas Tutorial(DB)](./index_db.md)  
[日本語](./ja/index.md)
# Yunas Tutorial(Settings)

## See Sample Project
[Yunas Sample](https://github.com/cobayo/yunas-sample)

## Add yunas-framework to your project
Latest Yunas Framework Library is in Maven Central  

build.gradle
```
group: 'io.github.cobayo', name:'yunas-framework',version: '1.0.3'
```
## Required kotlin plugin in build.gradle
```
apply plugin: 'kotlin'
```
Add buildscript > dependencies
```
classpath 'org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.1'
```

dependencies in build.gradle
```
compile "org.jetbrains.kotlin:kotlin-stdlib:1.1.1"
```

## Set MainClass in build.gradle
For Example, MainClass = sample.Main (fileName:Main.kt)
```
mainClassName="sample.MainKt"
```

## Set application.properties
Yunas can read ```src/main/resources/application.properties```  

You can set Runtime Environment (local,dev,production) to YUNAS_PROFILES_ACTIVE (environment variable).
```
export YUNAS_PROFILES_ACTIVE=local
```
Then, Yunas use ```application-local.properties``` instead of ```application.properites```  
If Unset YUNAS_PROFILES_ACTIVE or Set YUNAS_PROFILES_ACTIVE=production, 
Yunas always use  ```application.properties```

### Change default port
The default port used by Yunas is 10421.  
You can change default port by application.properties as below.  

```
yunas.port=8080
```

### Launch App
#### From Gradle (local)
```
gradle run
```

#### From Distribution by Gradle
```
gradle distZip  

unzip build/distribution/xxxxx.zip  

./xxxxx/bin/xxxxx  
```

#### From a single excutable jar with dependencies 

Use plugin: 'eu.appsatori.fatjar'
```
apply plugin: 'eu.appsatori.fatjar'
```

Add buildscript > dependencies
```
classpath 'eu.appsatori:gradle-fatjar-plugin:0.3'
```

Add fatJar Config
```
fatJar {
    baseName = archivesBaseName
}
```

Create a single executable jar with dependencies
```
gradle fatJar
```

Launch by Java Command
```
java -jar xxxx.jar
```


## Set Http Routing

If You use the following methods,  
  
Yunas.Rest.get  
Yunas.Rest.post    
Yunas.Rest.put    
Yunas.Rest.delete    
  

ContentType is automatically set to ```application/json``` And Return value (except String) is convert to JSON String.

For Example
```
import yunas.Context
import yunas.Yunas  


fun main(args: Array<String>) {
   Yunas.Rest.get("/top",{context: Context ->  "Hello World"})
}
```

GET 127.0.0.1:10421/top  
```
Hello World
```

For Example 2
```
import yunas.Context
import yunas.Yunas  


fun main(args: Array<String>) {
   Yunas.Rest.get("/top",{context: Context ->  mapOf("message" to "Hello World")  })
}
```

GET 127.0.0.1:10421/top  

```

{"message":"Hello World"}
```

### Routing with templates(Thymeleaf)
If You use the following methods,  

Yunas.get    
Yunas.post   

ContentType is automatically set to ```text/html``` And You have to return ```ModelAndView``` or ```Map``` or ```String```  
The default template engine by Yunas is Thymeleaf.

For Example  

```
import yunas.Context
import yunas.Yunas  


fun main(args: Array<String>) {
   Yunas.get("/top",{context: Context ->  ModelAndView(mapOf("message" to "HelloWorld"),"index")})
}
```

```mapOf("message" to "HelloWorld") ``` You cat Use "message" as variable on Thymeleaf.  

```index``` is Thymeleaf Template.   
In case, Yunas choose ```src/main/resources/templates/index.html```.

### Static files

You can set static files(css,js,img...etc) to ```src/main/resources/static/```.  

For example
```
src/main/resources/static/test.css

-> http[s]://yourdomain/text.css

```

