package ru.kpfu.servletes;

import ru.kpfu.dao.UserDao;
import ru.kpfu.entity.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/signin")
public class SigninServlet extends HttpServlet {

    private UserDao userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = (UserDao) getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            User user = userDao.getUser(login, password);

            if (user != null) {
                userDao.logLoginAttempt(login, true);

                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(60 * 60);

                Cookie cookie = new Cookie("user", login);
                cookie.setMaxAge(24 * 60 * 60);
                resp.addCookie(cookie);

                resp.sendRedirect("mainPage.jsp");
            } else {
                userDao.logLoginAttempt(login, false);

                req.setAttribute("error", "Invalid login or password");
                getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException("Error during login process", e);
        }
    }
}
