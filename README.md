![dn-ssh-client-ext](https://i.imgur.com/6wJT2wV.png)
# dn-ssh-client-ext
> For JPHP and DevelNext ([http://develnext.org](http://develnext.org)).

Пакет расширений для работы с SSH

### Возможности
- Подключение к SSH-серверу с помощью логина и пароля
- Исполнение консольных команд на сервере

### Пример

#### SSHClient
```php 
use php\ssh\client\SSHClient; 

$connection = SSHClient::connectWithPassword('192.168.0.102', 22, 'pi', 'raspberry');

// Инициализация подключения к серверу
$commander = $connection->execute();

// Исполняем команду
$commander->exec('tree', "UTF-8");

// Обязательно заканчиваем управление по SSH
$commander->end();

// Обязательно закрываем соединение по SSH
$connection->close();

// Получаем вывод с StdErr последней выполненной операции
$stdOut = $commander->getLastError();

// Получаем вывод с StdOut последней выполненной операции
$stdErr = $commander->getLastOutput();
```

### Сборка

1. Откройте консоль
2. Переместитесь в `dn-ssh-client-ext/3rd-party/ssh-client`
3. Используйте команду `"../../gradlew" install`
4. Переместитесь в `dn-ssh-client-ext`
5. Используйте команду `gradlew build`
6. В папке `dn-ssh-client-bundle/build` вы найдете файл бандла с расширением `.bundle`

