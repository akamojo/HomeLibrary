package com.vaadin;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletConfiguration;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The main servlet for the application.
 * <p>
 * It is not mandatory to have the Servlet, since Flow will automatically register a Servlet to any app with at least one {@code @Route} to server root context.
 */

@WebServlet(urlPatterns = "/*", name = "UIServlet", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false)
public class Servlet extends VaadinServlet {
}
