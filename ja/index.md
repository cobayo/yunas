1.[Yunas Tutorial(基本設定)](/ja/index.md)   
2.[Yunas Tutorial(DB)](/ja/index_db.md)  
3.[Yunas Tutorial(バッチ)](/ja/index_batch.md)  

[English](/index.md)

# Tutorial(基本設定)

## See Sample Project
[Yunas Sample](https://github.com/cobayo/yunas-sample)

## プロジェクトへの追加
最新のyunasライブラリはMaven Centralから取得可能です。


build.gradle
```
group: 'io.github.cobayo', name:'yunas-framework',version: '1.0.3'
```


## application.properties
```src/main/resources/application.properties```  

実行環境 (local,dev,production) は YUNAS_PROFILES_ACTIVE 環境変数で設定できます。  

```
export YUNAS_PROFILES_ACTIVE=local
```
Yunasは自動的に application-<実行環境名>.properties を設定ファイルをとして選択します。  
もし設定されていない場合、あるいは  YUNAS_PROFILES_ACTIVEに production が設定されている場合は
自動的に  ```application.properties``` を選択します。  

### デフォルト port　の変更
デフォルトポートは 10421 になります。  
以下のように application.properties で変更可能です。 

```
yunas.port=8080
```

### 起動方法
#### ローカル環境などでは以下のようにgradleコマンドで起動できます。
```
gradle run
```

#### 本番配布用zipを gradle distZipで作成可能です。
```
gradle distZip  

unzip build/distribution/xxxxx.zip  

./xxxxx/bin/xxxxx  
```

#### 実行可能Jarに固める場合はfatJarの利用を勧めています。 

build.gradle
```
apply plugin: 'eu.appsatori.fatjar'
```

buildscript > dependencies  に追加  
```
classpath 'eu.appsatori:gradle-fatjar-plugin:0.3'
```

fatJar Config を build.gradleに追加  
```
fatJar {
    baseName = archivesBaseName
}
```

jarの作成  
```
gradle fatJar
```

Launch by Java Command
```
java -jar xxxx.jar
```


## Set Http Routing

以下のメソッドで RestAPI用のルーティングを設定できます。 
  
Yunas.Rest.get  
Yunas.Rest.post    
Yunas.Rest.put    
Yunas.Rest.delete    
  

ContentType は自動的に ```application/json```  となり、  
戻り値のオブジェクトも自動的JSON Stringへ変換されます。  
(Stringを返した場合はそのまま request bodyとして返却されます。
自前でJSON化したい時のためです。)

例)
```
import yunas.Context
import yunas.Yunas  


fun main(args: Array<String>) {
   Yunas.Rest.get("/top",{context: Context ->  "Hello World"})
}
```

GET 127.0.0.1:10421/top  アクセスすると、
```
Hello World
```

例2)
```
import yunas.Context
import yunas.Yunas  


fun main(args: Array<String>) {
   Yunas.Rest.get("/top",{context: Context ->  mapOf("message" to "Hello World")  })
}
```

GET 127.0.0.1:10421/top  アクセスすると

```

{"message":"Hello World"}
```
自動的にJSON化されレスポンスされています。

### Webページ表示はThymeleafを使います。

 以下のメソッドでルーティングを設定します。

Yunas.get    
Yunas.post   

ContentType は自動的に ```text/html``` となります。
 メソッドの戻り値は```ModelAndView```  ```Map```  ```String```
  のいずれかのである必要があります。
Thymeleafを使わずhtml文字列を表示したい場合はStringで返してください。  
ModelAndViewあるいはMapの場合は自動的にThymeleafのModelAndViewにセットされます。

For Example  

```
import yunas.Context
import yunas.Yunas  


fun main(args: Array<String>) {
   Yunas.get("/top",{context: Context ->  ModelAndView(mapOf("message" to "HelloWorld"),"index")})
}
```

```mapOf("message" to "HelloWorld") ```  "message" をThymeleaf上で変数として使えます。 

```index``` は Thymeleaf Template　です 
以下から選ばれます。  
 ```src/main/resources/templates/index.html```.

### 静的ファイルについて

静的ファイルは(css,js,img...etc) 以下のパスに配置してください。  
 ```src/main/resources/static/```

以下のように呼び出せます。  
```
src/main/resources/static/test.css

-> http[s]://yourdomain/text.css

```

