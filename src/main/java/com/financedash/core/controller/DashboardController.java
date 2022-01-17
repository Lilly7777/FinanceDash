package com.financedash.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class DashboardController {

    @Autowired
    OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping("/")
    public String index(Model model, OAuth2AuthenticationToken authentication, HttpServletResponse response) {

        OAuth2AuthorizedClient client = oAuth2AuthorizedClientService
                .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        response.addCookie(new Cookie("ACCESS_TOKEN", client.getAccessToken().getTokenValue()));

        model.addAttribute("earnings", 0);
        model.addAttribute("expenses", 0);
        model.addAttribute("tasks_percentage", 0);
        model.addAttribute("tasks_count", 0);
        model.addAttribute("given_name", authentication.getPrincipal().getAttributes().get("given_name"));
        model.addAttribute("family_name", authentication.getPrincipal().getAttributes().get("family_name"));


        return "index";
    }

    @GetMapping("/transactions")
    public String table(Model model, OAuth2AuthenticationToken authentication) {
        model.addAttribute("earnings", 0);
        model.addAttribute("given_name", authentication.getPrincipal().getAttributes().get("given_name"));
        model.addAttribute("family_name", authentication.getPrincipal().getAttributes().get("family_name"));
        return "table";
    }

    @GetMapping("/profile")
    public String profile(Model model, OAuth2AuthenticationToken authentication) {
        model.addAttribute("given_name", authentication.getPrincipal().getAttributes().get("given_name"));
        model.addAttribute("family_name", authentication.getPrincipal().getAttributes().get("family_name"));
        //System.out.println(((com.nimbusds.jose.shaded.json.JSONArray) authentication.getPrincipal().getAttributes().get("emails")).toJSONString());
        //Matcher matcher = Pattern.compile("\\[\"(.*)\"\\]").matcher(((com.nimbusds.jose.shaded.json.JSONArray) authentication.getPrincipal().getAttributes().get("emails")).toJSONString());
       model.addAttribute("email", (((com.nimbusds.jose.shaded.json.JSONArray) authentication.getPrincipal().getAttributes().get("emails")).toJSONString()));
        return "profile";
    }
}
