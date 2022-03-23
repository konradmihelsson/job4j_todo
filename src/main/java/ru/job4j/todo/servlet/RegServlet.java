package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HbnStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = HbnStore.instOf().findUserByEmail(req.getParameter("email"));
        if (user != null) {
            req.setAttribute("error", "Такой email уже используется! Укажите другой email.");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else {
            user = new User(req.getParameter("email"), req.getParameter("password"));
            HbnStore.instOf().add(user);
            resp.sendRedirect(req.getContextPath() + "/auth.do");
        }
    }
}
