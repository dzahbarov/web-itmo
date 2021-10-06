package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    private final static String UTF_8 = StandardCharsets.UTF_8.name();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        request.setCharacterEncoding(UTF_8);
        response.setCharacterEncoding(UTF_8);
        for (String uri : URLDecoder.decode(request.getRequestURI(), UTF_8).split("\\+")) {
            uri = uri.contains("/") && uri.charAt(0) != '/' ? '/' + uri : uri;
            File file = new File(getServletContext().getRealPath("."), "../../src/main/webapp/static" + uri);

            if (!file.isFile()) {
                file = new File(getServletContext().getRealPath("/static"), uri);
            }
            if (file.isFile()) {

                if (response.getContentType() == null) {
                    response.setContentType(getContentTypeFromName(uri));
                }
                response.setStatus(HttpServletResponse.SC_OK);
                Files.copy(file.toPath(), outputStream);
                outputStream.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
