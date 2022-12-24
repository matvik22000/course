function get(url, args, callback) {
    // Эта функция делает запрос на сервер

    // url - интуитивно понятно, что это такое - адрес, по которому хотим сделать запрос.
    // args - аргументы запроса.
    // callback - функция, которая будет вызвана, когда придет ответ от сервера.
    // В неё передадим ответ сервера.
    url += "?"
    const serialized_args = []
    for (const key in args) {
        serialized_args.push(key + "=" + args[key])
    }
    url += serialized_args.join("&")
    $.get(url, callback)
}

function post(url, args, callback) {
    $.post(url, args, callback)
}

