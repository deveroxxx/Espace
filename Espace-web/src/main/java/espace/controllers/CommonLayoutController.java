package espace.controllers;

import espace.enums.Role;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@ApplicationScoped
public class CommonLayoutController {

    private final List<Route> routes;

    public CommonLayoutController() {
        routes = new ArrayList<>();

        // Could be read from DB but it is not in the scope of this project.
        routes.add(new Route("/Product/listProducts", "Products"));

        routes.add(new Route("/Admin/usersList", "Manage users", Role.ADMIN));
        routes.add(new Route("/Admin/requestList", "Manage requests", Role.ADMIN));
        routes.add(new Route("/Admin/addCategory", "Categories", Role.ADMIN));

        routes.add(new Route("/Account/profile", "Profile", false, Role.ADMIN, Role.USER));

        routes.add(new Route("/Account/login", "Login", false, Role.GUEST));
        routes.add(new Route("/Account/register", "Register", false, Role.GUEST));
    }

    /**
     * Finds the first displayable route for the user and redirects to it.
     *
     * Used from the index.xhtml
     *
     * @throws IOException
     */
    public void redirectFromIndex() throws IOException {
        for (Route route : routes) {
            if (route.isRendered()) {
                getExternalContext().redirect("faces" + route.path + ".xhtml");
                break;
            }
        }
    }

    public List<Route> getRoutes() {
        return routes;
    }

    private boolean isUserInRole(Role role) {
        ExternalContext externalContext = getExternalContext();

        return externalContext.isUserInRole(role.name());
    }

    private ExternalContext getExternalContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        ExternalContext externalContext = facesContext.getExternalContext();

        return externalContext;
    }

    public class Route {

        private String path;

        private String name;

        private List<Role> roles;

        private boolean leftSide;

        public Route(String path, String name, Role... roles) {
            this(path, name, true, roles);
        }

        public Route(String path, String name, boolean leftSide, Role... roles) {
            this.path = path;
            this.name = name;
            this.leftSide = leftSide;
            this.roles = Arrays.asList(roles);
        }

        /**
         * The method tells if the given route can be rendered for the
         * requesting user.
         *
         * @param leftSide if the route should be displayed in the left menu
         * @return true if the route is renderable
         */
        public boolean isRendered(boolean leftSide) {
            if (leftSide != this.leftSide) {
                return false;
            }

            return isRendered();
        }

        /**
         * The method tells if the given route can be rendered for the
         * requesting user.
         *
         * @return true if the route is renderable
         */
        public boolean isRendered() {
            // For login and signup
            if (roles.isEmpty()) {
                return true;
            }

            if (roles.contains(Role.GUEST) && getExternalContext().getRemoteUser() == null) {
                return true;
            }

            for (Role role : roles) {
                if (isUserInRole(role)) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Helper method to render active css class for the active menu element.
         *
         * @return the css class - "active" as string if the equest's path
         *         matches
         *         the route's path otherwise an empty string
         */
        public String isActive() {
            HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            String requestPath = origRequest.getPathInfo();

            if (requestPath == null) {
                return "";
            }

            return path.equals(requestPath.replace(".xhtml", "")) ? "active" : "";
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public List<Role> getRoles() {
            return roles;
        }

        public void setRoles(List<Role> roles) {
            this.roles = roles;
        }

        public boolean isLeftSide() {
            return leftSide;
        }

    }

}
