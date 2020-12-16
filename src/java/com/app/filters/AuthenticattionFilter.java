/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.filters;

import com.app.constants.Role;
import com.app.daos.UserDAO;
import com.app.dtos.UserDTO;
import com.app.routes.AppRouting;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author DuyNK
 */
public class AuthenticattionFilter implements Filter {

//    private final String[] ADMIN_ROUTES = {
//        "admin", "admin.jsp", "addProduct", "add_product.jsp",
//        "manageProduct", "manage_product.jsp", "editProduct", "edit_product.jsp",
//        "AddProductServlet", "DeleteProductServlet", "UpdateProductServlet",
//        "order", "manage_order.jsp", "ManageProductServlet", "ManageOrderServlet",
//        "OrderDetailsServlet", "order_details.jsp",
//        "LogoutServlet", "", "manage_product_search.jsp",
//        "AdminSearchServlet", "adminSearch"
//    };
//
//    private final String[] USER_ROUTES = {
//        "profile", "user_profile.jsp", "", "LogoutServlet"
//    };
//    
//    private final String[] GUEST_ROUTES = {
//        "cart", "cart.jsp", "checkout", "checkout.jsp",
//        "CheckoutServlet", "UpdateCartServlet", "DeleteCartItemServlet",
//        "SearchProductServlet", "search", "SearchProductServlet", "product_search_result.jsp",
//        "index.jsp"
//    };
    
    private final List<String> ADMIN_ROUTES;

    private final List<String> USER_ROUTES;
    
    private final List<String> GUEST_ROUTES;
    
   
    private final String LOGIN_PAGE = "login.jsp";
    private final String ADMIN_PAGE = "admin.jsp";
    private final String USER_PAGE = "index.jsp";
    private final String FOBIDDEN_PAGE = "forbidden.html";

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    private static final Logger LOGGER = Logger.getLogger(AuthenticattionFilter.class);

    private final String NOT_FOUND = "not_found.html";

    public AuthenticattionFilter() {
        // init all routes for the app
        AppRouting.initRoutes();
        ADMIN_ROUTES = AppRouting.adminRoutes;
        USER_ROUTES = AppRouting.userRoutes;
        GUEST_ROUTES = AppRouting.guestRoutes;
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticattionFilter:DoBeforeProcessing");
        }

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticattionFilter:DoAfterProcessing");
        }

    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        HttpSession session = req.getSession(false);

        String resource = uri.substring(uri.lastIndexOf("/") + 1);
       
        // check user is authenticated or not
        boolean isAuthenticated = session != null && session.getAttribute("user") != null;
        boolean isAdminResource = ADMIN_ROUTES.contains(resource);
        boolean isUserResource = USER_ROUTES.contains(resource);
        boolean isGuestResource = GUEST_ROUTES.contains(resource);
        boolean isLoginRequest = resource.equals("login") || resource.equals("LoginServlet") || resource.equals("login.jsp");
        boolean isRegisterRequest = resource.equals("register") || resource.equals("RegisterServlet") || resource.equals("register.jsp");
        boolean isHomePage = resource.isEmpty();
        boolean isStaticRequestFile = resource.endsWith(".css") || resource.endsWith(".js")
                || resource.endsWith(".png") || resource.endsWith(".jpg") || resource.endsWith(".svg");

        if (isStaticRequestFile) {
            chain.doFilter(request, response);
        } else {
            if (isAuthenticated) {
                try {
                    // check role of user
                    UserDTO loggedInUser = (UserDTO) session.getAttribute("user");

                    UserDAO dao = new UserDAO();
                    String roleID = loggedInUser.getRole().getRoleID();
                    String roleName = dao.getUserRoleName(roleID);

                    if (isHomePage || isLoginRequest || isRegisterRequest) {
                        if (roleName.equals(Role.ADMIN)) {
                            request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
                        } else if (roleName.equals(Role.USER)) {
                            request.getRequestDispatcher(USER_PAGE).forward(request, response);
                        }
                    }                   
                    else {
                        if (roleName.equals(Role.ADMIN) && isAdminResource) {
                            chain.doFilter(request, response);
                        } 
                        if (roleName.equals(Role.USER) && (isUserResource || isGuestResource)) {
                            chain.doFilter(request, response);
                        }
                        if((roleName.equals(Role.ADMIN) && !isAdminResource) ||
                                roleName.equals(Role.USER) && !isUserResource){
                            request.getRequestDispatcher(FOBIDDEN_PAGE).forward(request, response);
                        }
                        
                    }

                } catch (Exception ex) {
                    LOGGER.error("Error at when get usser role name", ex);
                }
            } else {
                if (isAdminResource || isUserResource) {
                    if (isHomePage) {
                        request.getRequestDispatcher(USER_PAGE).forward(request, response);
                    } else {
                        res.sendRedirect(LOGIN_PAGE);
                    }

                } else {
                    chain.doFilter(request, response);
                }
            }
        }

    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthenticattionFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticattionFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenticattionFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
