![dn-ssh-client-ext](https://i.imgur.com/6wJT2wV.png)
# dn-ssh-client-ext
> For JPHP and DevelNext ([http://develnext.org](http://develnext.org)).

Пакет расширений для работы с SSH

### Возможности
- Подключение к SSH-серверу с помощью логина и пароля
- Исполнение консольных команд на сервере

### Пример

```php 
use php\ssh\client\SSHClient; 

$connection = SSHClient::connectWithPassword('host', 22, 'user', 'password');

// инициализация подключения к серверу
$commander = $connection->execute();

// исполнение команды с указанием исходной кодировки
$commander->exec('tree', "UTF-8");

// исполнение команды с sudo правами
$commander->execWithSudo('command', 'password', "UTF-8"); // исходная кодировка

// заканчиваем управление по SSH
$commander->end();

// закрываем соединение по SSH
$connection->close();

// получаем вывод с StdErr последней выполненной операции
$stdErr = $commander->getLastError();

// получаем вывод с StdOut последней выполненной операции
$stdOut = $commander->getLastOutput();
```

### Сборка

1. `git clone https://github.com/artemirq/dn-ssh-client-ext`
2. `cd dn-ssh-client-ext`
3. `git submodule init`
4. `git submodule update`
5. `cd 3rd-party/ssh-client`
6. `"../../gradlew" install`
7. `cd ../..`
8. `gradlew bundle`

По пути `dn-ssh-client-bundle/build` вы найдете файл бандла
