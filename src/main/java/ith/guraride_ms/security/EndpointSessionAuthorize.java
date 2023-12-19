package ith.guraride_ms.security;

import jakarta.servlet.http.HttpSession;

public class EndpointSessionAuthorize {

    public boolean isAdmin(HttpSession session) {
        if (session.getAttribute("status") != null && session.getAttribute("status").equals("admin")) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isWorker(HttpSession session) {
        if (session.getAttribute("status") != null && session.getAttribute("status").equals("worker")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRenter(HttpSession session) {
        if (session.getAttribute("status") != null && session.getAttribute("status").equals("renter")) {
            return true;
        } else {
            return false;
        }
    }
}
