[Yunas Tutorial(Settings)](./index.md) | [Yunas Tutorial(DB)](./index_db.md)  
[日本語](./ja/index.md)
# Yunas Tutorial(DB Settings)

## See Sample Project
[Yunas Sample](https://github.com/cobayo/yunas-sample)

## DB Setting in application.properties

For Example(MySQL)  
```
yunas.db.driverClassName=com.mysql.jdbc.Driver
yunas.db.jdbcUrl=jdbc:mysql://your_db_server:3306/your_db?autoReconnect=true&useSSL=false
yunas.db.user=your_user
yunas.db.password=your_pw
yunas.db.maxPoolSize=16
yunas.db.connectionTimeout=6000000
```

### Execute SQL

You can use ``` yunas.util.DBManager ``` .  

For Example (Select)  
```
val row :ResultSetRow = DBManager(DBName.MASTER).use {
        it.select("select * from accounts where id = ?", listOf("1"))
    }

    println(row.getString("id")) // print 1
```

For Example (Transaction with Insert)  
```
DBManager(DBName.MASTER).use {
        it.beginTransaction()
        it.insert("INSERT INTO accounts (id,name) values (?,?)", listOf(2,"test"))
        it.commit()
    }
```

### Slave DB (Secondary DB) Settings

Yunas supports a setting of slave(secondary) db.  
```
yunas.db.secondary.driverClassName=com.mysql.jdbc.Driver
yunas.db.secondary.jdbcUrl=jdbc:mysql://your_db_server:3306/your_db?autoReconnect=true&useSSL=false
yunas.db.secondary.user=your_user
yunas.db.secondary.password=your_pw
yunas.db.secondary.maxPoolSize=16
yunas.db.secondary.connectionTimeout=6000000
```

You can use Slave DB with ```DBName.SECONDARY``` as below.  
```
val row :ResultSetRow = DBManager(DBName.SECONDARY).use {
        it.select("select * from accounts where id = ?", listOf("1"))
    }
```

## Get a DataSource
You can get ```javax.sql.DataSource```  as below.

```
Master:
Databases.getDataSource(DBName.MASTER.value)

Slave(Secondary):
Databases.getDataSource(DBName.SECONDARY.value)
```