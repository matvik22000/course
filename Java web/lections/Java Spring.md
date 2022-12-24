
### ООП, объекты, JSON и как это всё связано

В JavaScript  мы уже научились "посылать запрос", но мы передаем в него параметры как JS объект - по сути, словарь, мапа. А на сервере мы хотим получить Java объект, что делать?

### Получение данных на сервере

Вспомним, как мы посылали запрос из JS:
```json
{
	classNumber: "108",
	teacherId: 1,
	reason: "важные занятия",
	startTime: '01.01.2023 10:00:00',
	endTime: '01.01.2023 12:00:00'
}
```


Напишем функцию, которая будет принимать в параметрах всю необходимую информацию, создавать бронирование и сохранять его
```java
void createReservation(  
        String classNumber,  
        int teacherId,  
        String reason,  
        Date startTime,  
        Date endTime  
) {  
    Reservation reservation = new Reservation(  
            classNumber, teacherId, reason, startTime, endTime  
    );  
    reservation.save();  
  
}
```
Что должен делать метод save? Записывать информацию куда-нибудь, в будущем - в базу данных. Пока мы не знаем, что это такое, поэтому выведем объект в консоль, чтобы убедиться, что всё работает

```java
public void save() {  
    System.out.println("Saved" + this);  
}  
  
@Override  
public String toString() {  
    return "reserved" + classNumber + "from " + startTime + " to " + endTime + " for " + reason;  
}
```

Отлично, но кто будет вызывать метод ``` createReservation()```? 

Он должен вызываться сам, когда клиент вызывает метод ```post()``` (помните у нас был такой?)

Для того, чтобы понять, как склеить 2 эти программы, нужно разобраться в [[HTTP]]

Фреймворк Spring позволяет легко превратить функцию в ```createReservation()``` в хендлер HHTP запроса
```java
@Controller  
public class HttpController {  
    @ResponseBody  // Нужно указать, что то, что возвращает метод нужно как есть передать по http
    @PostMapping(value = "/create_reservation", produces = "application/json")  
    // value - это относительный путь, при запросе на который будет вызываться метод
    // Например, если адрес сайта - 127.0.0.1:8080, то метод вызовется при запросе на 
    // 127.0.0.1:8080/create_reservation
    // produces отвечает за выставление хедера content-type в ответе, в целом, можно его 
    // не указывать
    String createReservation(
            String classNumber,  
            int teacherId,  
            String reason,  
            Date startTime,  
            Date endTime  
    ) {  
        Reservation reservation = new Reservation(  
                classNumber, teacherId, reason, startTime, endTime  
        );  
        return reservation.save();  
    }  
}

```

Теперь давайте научимся отдавать информацию о бронированиях

Посмотрим ещё раз на наш класс 
```Java
public class Reservation {
	private String classNumber; 
	private int teacherId;  
	private String reason;  
	private Date startTime;  
	private Date endTime;  
	
	...
}
```

Вспомним, что мы хотим получить
```json
{
	classNumber: "108",
	teacherId: 1,
	reason: "важные занятия",
	startTime: '01.01.2023 10:00:00',
	endTime: '01.01.2023 12:00:00'
}
```

Есть что-то общее, правда?

Можно, конечно, написать сериализатор руками, однако этим вы наверняка успеете заняться в институте, а пока предлагаю не изобретать велосипеды и воспользоваться готовой библиотекой  Jackson.


У нас есть список из множества резерваций, как понять, какая именно нам нужна? Для этого у каждой из них должен быть свой идентификатор. Следить за ними будет база данных: при добавлении новой записи она сама добавит ей поле id.

Добавим метод ```get()``` классу ```Reservation```, который по id будет возвращать объект из базы данных.
Важно, что этот метод должен быть статическим, так как самого объекта у нас на руках ещё нет.

```java
Reservation getReservation(@RequestParam  String id) {  
    return Reservation.get(id);  
}
```

Здесь мы по-хорошему должны вернуть строку вроде той, что посылаем из javascript в методе post, по почему-то возвращаем объект, как это работает?

Чтобы Jackson мог работать, у для всех полей классов должны быть прописаны геттеры и сеттеры

Spring увидит, что мы вернули не String и попытается привести этот объект к jsonу используя Jackson. Однако тот умеет приводить простые типы, а вот что делать с типом Date он не знает. Надо помочь:
```java
@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)  
private Date startTime;  
@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)  
private Date endTime;
```


## НО

До этого мы делали понятные вещи, но хорошего понемногу, теперь надо чуть-чуть поколдовать

В файл `application.properties` Нужно обязательно дописать
```
server.servlet.session.cookie.http-only=false  
server.servlet.session.cookie.secure=false
```

И над HttpController дописать 
```java
@CrossOrigin(allowCredentials = "true", originPatterns = "*")
```

Иначе будет такое:
![[Pasted image 20221220000425.png]]