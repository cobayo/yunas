1.[Yunas Tutorial(Settings)](/index.md)   
2.[Yunas Tutorial(DB)](/index_db.md)  
3.[Yunas Tutorial(Batch)](/index_batch.md)    
 
[日本語](/ja_index.md)

# Tutorial(Batch)

You can register a Batch Module in Yunas as belowe.  

```
fun main(args: Array<String>) {

   
    YunasBatch.add("test",{(args) ->
        println("HelloWorld")
    } )

    YunasBatch.run(args)
}
```

execute with a single executable jar.  s　
```
java -Dyunas.batch=test -jar xxxxx.jar
```

**Do not use Yunas Web and YunasBatch at same jar**