<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="scripts/index.js"></script>
    <script>
        $(document).ready(getItems('undone'));
    </script>
    <title>TODO list</title>
</head>
<body>
<div class="container">
    <div class="row">
        <ul class="nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/logout.do"> <c:out
                        value="${sessionScope.user.email}"/> | Выйти</a>
            </li>
        </ul>
    </div>
    <h1>TODO list</h1>
    <form>
        <div class="form-group row">
            <div class="col-md-6">
                <label for="inputName">New task: </label>
                <input type="text" class="form-control" title="Введите имя." id="inputName"
                       placeholder="Task description...">
                <button type="button" class="btn btn-primary" onclick="addItem()">Add
                </button>
            </div>
        </div>
    </form>
    <form>
        <div class="form-group row">
            <label>Show all tasks
                <input type="checkbox" onclick="unDoneOnlySwitch($(this));" id="checkbox">
            </label>
        </div>
    </form>
    <table class="table table-striped" id="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Description</th>
            <th scope="col">Created</th>
            <th scope="col">User</th>
            <th scope="col">Done</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
</body>
</html>
