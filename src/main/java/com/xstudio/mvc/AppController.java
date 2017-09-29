package com.xstudio.mvc;


import com.xstudio.common.AppUserDetails;
import com.xstudio.common.utils.ContextUtil;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AppController {

    @RequestMapping("")
    public String index() {
        return "index";
    }

    @RequestMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSessionRequestCache requestCache) {
        AppUserDetails currentUser = ContextUtil.getCurrentUser();
        if (null != currentUser) {
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            if (null != savedRequest) {
                return "redirect:" + savedRequest.getRedirectUrl();
            }

            return "redirect:/";
        }
        return "login";
    }
}
