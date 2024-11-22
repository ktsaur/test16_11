package ru.kpfu.servletes;

import org.json.JSONObject;
import ru.kpfu.client.HttpClient;
import ru.kpfu.client.HttpClientImpl;
import ru.kpfu.dao.UserDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {

    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String STRING = "bd5e378503939ddaee76f12ad7a97608";
    private final HttpClient httpClient = new HttpClientImpl();
    private UserDao userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = (UserDao) getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/views/mainPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cityName = req.getParameter("cityName");
        String login = (String) req.getSession().getAttribute("login");

        Map<String, String> params = new HashMap<>();
        params.put("q", cityName);
        params.put("appid", STRING);
        params.put("units", "metric");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        try {

            String weatherData = httpClient.get(API_URL, headers, params);

            JSONObject json = new JSONObject(weatherData);
            String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
            double temperature = json.getJSONObject("main").getDouble("temp");

            if (login != null && !login.isEmpty()) {
                userDao.addCities(login, cityName);
            } else {
                LOG.info("login is null");
            }

            req.setAttribute("cityName", cityName);
            req.setAttribute("description", description);
            req.setAttribute("temperature", temperature);

        } catch (Exception e) {
            req.setAttribute("weather", "Ошибка при получении данных о погоде. Пожалуйста, попробуйте еще раз.");
        }

        getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/views/mainPage.jsp").forward(req, resp);
    }
}
