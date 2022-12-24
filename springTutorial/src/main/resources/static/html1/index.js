function get(url, args, callback) {
    // Представим, что эта функция делает запрос на сервер

    // url - интуитивно понятно, что это такое - адрес, по которому хотим сделать запрос.
    // args - аргументы запроса.
    // callback - функция, которая будет вызвана, когда придет ответ от сервера.
    // В неё передадим ответ сервера.
    console.log(url, args)
    if (url === "/load_reservations") {
        const res = [
            {
                cabinet: "208",
                teacher: "Кто-то ещё",
                purpose: "вообще не надо",
                startTime: "01.01.2023 12:00:00",
                endTime: "01.01.2023 13:00:00"
            }
        ]
        callback(res)
    }
}

function post(url, args, callback) {
    console.log(url, args)
    callback()
}


