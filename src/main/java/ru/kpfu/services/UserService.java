package ru.kpfu.services;

import ru.kpfu.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserService {

    public User getUser(HttpServletRequest req, HttpServletResponse res) {
        return (User) req.getSession().getAttribute("user");
    }

    public void authUser(User user, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getSession().setAttribute("user", user); //Установка атрибута user в сессии
    }

    public void deleteUser(User user, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getSession().removeAttribute("user");
        //return userDao.deleteUser(user.getId());
    }

}
