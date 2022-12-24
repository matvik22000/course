- HTTP вообще
- HTTP гоняет jsonы - значит это тоже важно


#### JSON 
JSON - (по сути, мапа или словарь, штука в которой хранятся значения по ключу) это формат хранения данных. Выглядит он так:
``` JSON
{
	"string": "value1",
	"int": 3,
	"bool": true,
	"array": [1, 2, 3],
	"json": {
		"key": "value"
	}
}
```
-  Значениями могут быть строки, числа, булевы переменные, массивы и другие json'ы


#### HTTP
_HTTP - HyperText Transfer Protocol._ 

Почему Гипер текст и что что такое?
Гипер текст - это текст со ссылками. HTML, о котором мы тоже поговорим - это HyperText Markdown Lanuage, то есть HTML документ - это и есть гипер текст. Однако сейчас HTTP используется для передачи чего угодно.

HTTP - протокол типа ответ-запрос, то есть клиент делает запрос, на который сервер отвечает. 

Рассмотрим самый простой пример запроса

#### Простой GET
Из чего он состоит?
```
GET /get HTTP/1.1  - строка стостояния (Метод, путь, версия)
Host: httpbin.org  - хедеры (в данном случае один)
```
- Хедеры - служебная информация, она, за некоторыми исключениями, которые мы рассмотрим подробнее, вам пока не понадобится
- Что такое метод рассмотрим позже
- Путь - это то, что идёт после адреса сайта http://httpbin.org/get 
- Версия - версии протокола, сейчас есть HTTP2 и HTTP3, но мы будем рассматривать 1.1, потому что 2 и 3 сложнее и всё еще далеко не везде поддерживаются

#### Ответ
```http
HTTP/1.1 200 OK - строка состояния, 200 - код ответа
Date: Sun, 27 Nov 2022 15:42:49 GMT 
Content-Type: application/json 
Content-Length: 728 
Connection: keep-alive 
Server: gunicorn/19.9.0 
Access-Control-Allow-Origin: * - вот это нам ещё понадобится 
ccess-Control-Allow-Credentials: true - вот это нам ещё понадобится 
```

- Код ответа говорит, правильно ли обработался запрос и что делать дальше. 200 - значит что всё хорошо, но бывает и [плохо](https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BA%D0%BE%D0%B4%D0%BE%D0%B2_%D1%81%D0%BE%D1%81%D1%82%D0%BE%D1%8F%D0%BD%D0%B8%D1%8F_HTTP)

#### GET с аргументами
- Если мы хотим передать какую-то информацию на сервер (например, имя интересующего нас пользователя) на сервер в GET запросе, мы можем дописать аргументы прямо в адресную строку в таком формате: http://httpbin.org/get?name=test
- В сыром виде это выглядит так:

```http
GET /get?name=test HTTP/1.1 
Host: httpbin.org 
```

#### POST
- Что если нам нужно передать на сервер что-нибудь большое, или бинарное? просто закодировать это в адресную строку не получится. Для этого существуют POST запросы. Их основное отличие от GET в том, что у них есть тело.

Выглядит он так:
```http
GET /get?name=test HTTP/1.1 
Host: httpbin.org 

"{'тут': 'то, что мы хотим передать'}"
```



### Тренируемся

Давайте возьмём функцию get из прошлых уроков и заставим её отправлять запрос по-настоящему:
```javascript
function get(url, args, callback) {  
    const serialized_args = []  
    for (const key in args) {  
        serialized_args.push(key + "=" + args[key])  
    }  
    url += serialized_args.join("&")  
    $.get(url, callback)  
}
```

Попробуем сделать с её помощью сделать запрос куда-нибудь

```javascript
get("http://httpbin.org/get", {test: 123}, function (data) {console.log(data)})
```

А теперь самое на наш сервер:

```javascript
get("http://localhost:8080/", {test: 123}, function (data) {console.log(data)})
```


Сделаем post:
```javascript
function post(url, args, callback) {  
    $.post(url, args, callback)  
}
```

```javascript
post("http://localhost:8080/", {test: 123}, function (data) {console.log(data)})
```

