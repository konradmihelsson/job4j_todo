/**
 * Получение задач из БД в виде JSON м заполнение таблицы
 *
 * @param {string} param - параметр для получения всех или только невыполненных задач
 */
function getItems(param) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/items?param=' + param,
        dataType: 'json'
    }).done(function (data) {
        var item = '';
        $.each(data, function (key, value) {
            item += '<tr>';
            item += '<td>' +
                value.id + '</td>';
            item += '<td>' +
                value.description + '</td>';
            item += '<td>' +
                value.created + '</td>';
            if (value.done === true) {
                item += '<td><input type="checkbox" checked="checked" ' +
                    'onclick="editItem($(this));" id="checkbox-' + value.id + '"></td>';
            } else {
                item += '<td><input type="checkbox" ' +
                    'onclick="editItem($(this));" id="checkbox-' + value.id + '"></td>';
            }
            item += '</tr>';
        });
        $('#table').append(item);
    }).fail(function (err) {
        console.log(err);
    });
}

/**
 * Удаление строк таблицы
 */
function clearTable(){
    var table = document.getElementById("table");
    while(table.rows.length > 1) {
        table.deleteRow(1);
    }
}

/**
 * Показывать все/только невыполненные задачи
 *
 * @param t - таблица для перепостроения
 */
function unDoneOnlySwitch(t) {
    if (t.is(':checked')) {
        clearTable();
        getItems('all');
    } else {
        clearTable();
        getItems('undone');
    }
}

/**
 * Изменение флага выполнения задачи с сохранением в БД
 * с последующим обновлением отображения в таблице
 *
 * @param t - элемент checkbox, содержащий в своем id id задачи.
 */
function editItem(t) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/edit?id=' + t.attr('id'),
    }).done(function () {
        unDoneOnlySwitch($("#checkbox"));
    }).fail(function (err) {
        console.log(err);
    });
}

/**
 * Добавление задачи в БД с последующим добавлением ее в конец таблицы
 */
function addItem() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/items',
        data: JSON.stringify({
            description: $('#inputName').val()
        }),
        dataType: 'json'
    }).done(function (data) {
        var item = '';
        item += '<tr>';
        item += '<td>' +
            data.id + '</td>';
        item += '<td>' +
            data.description + '</td>';
        item += '<td>' +
            data.created + '</td>';
        item += '<td><input type="checkbox" ' +
            'onclick="editItem($(this));" id="checkbox-' + data.id + '"></td>';
        item += '</tr>';
        $('#table').append(item);
    }).fail(function (err) {
        console.log(err);
    });
}
