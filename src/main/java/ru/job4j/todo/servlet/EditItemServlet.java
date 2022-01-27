package ru.job4j.todo.servlet;

import ru.job4j.todo.store.HbnStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String param = req.getParameter("id");
        int id = Integer.parseInt(param.split("-")[1]);
        HbnStore.instOf().changeIsDoneFlag(id);
    }
}
