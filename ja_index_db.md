1.[Yunas Tutorial(基本設定)](/ja_index.md)   
2.[Yunas Tutorial(DB)](/ja_index_db.md)  
3.[Yunas Tutorial(バッチ)](/ja_index_batch.md)  

[English](/)

# Tutorial(データベース)

## See Sample Project
[Yunas Sample](https://github.com/cobayo/yunas-sample)

以下のように ```application.propserties``` で設定するとHikariCPにより
コネクションプールが作られ、 ``` yunas.util.DBManager ``` でSQLをデータベースに投げることができます。

```
yunas.db.driverClassName=com.mysql.jdbc.Driver
yunas.db.jdbcUrl=jdbc:mysql://your_db_server:3306/your_db?autoReconnect=true&useSSL=false
yunas.db.user=your_user
yunas.db.password=your_pw
yunas.db.maxPoolSize=16
yunas.db.connectionTimeout=6000000
```

例:Select)
```
val row :ResultSetRow = DBManager(DBName.MASTER).use {
        it.select("select * from accounts where id = ?", listOf("1"))
    }

    println(row.getString("id")) // print 1
```

例:Transaction with Insert)
```
DBManager(DBName.MASTER).use {
        it.beginTransaction()
        it.insert("INSERT INTO accounts (id,name) values (?,?)", listOf(2,"test"))
        it.commit()
    }
```

以下のようにもう一台DB接続することができます。(Yunasは同時２台までDB接続可能)
```
yunas.db.secondary.driverClassName=com.mysql.jdbc.Driver
yunas.db.secondary.jdbcUrl=jdbc:mysql://your_db_server:3306/your_db?autoReconnect=true&useSSL=false
yunas.db.secondary.user=your_user
yunas.db.secondary.password=your_pw
yunas.db.secondary.maxPoolSize=16
yunas.db.secondary.connectionTimeout=6000000
```