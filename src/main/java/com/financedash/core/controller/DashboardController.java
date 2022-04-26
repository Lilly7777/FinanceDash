package com.financedash.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.financedash.core.model.Category;
import com.financedash.core.model.ExtendedTransaction;
import com.financedash.core.model.Transaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@CrossOrigin(origins = "*")
@Controller
public class DashboardController {

    @Value("${financedash.resource.server.uri}")
    private String resourceServerUri;

    @Autowired
    OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping("/")
    public String index(Model model, OAuth2AuthenticationToken authentication, HttpServletResponse response) throws JsonProcessingException {

        OAuth2AuthorizedClient client = oAuth2AuthorizedClientService
                .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        response.addCookie(new Cookie("ACCESS_TOKEN", client.getAccessToken().getTokenValue()));

        model.addAttribute("earnings", 0);
        model.addAttribute("tasks_percentage", 0);
        model.addAttribute("tasks_count", 0);
        model.addAttribute("given_name", authentication.getPrincipal().getAttributes().get("given_name"));
        model.addAttribute("family_name", authentication.getPrincipal().getAttributes().get("family_name"));
        String email = StringUtils.replace((((com.nimbusds.jose.shaded.json.JSONArray) authentication.getPrincipal().getAttributes().get("emails")).toJSONString()), "[\"", "");
        email = StringUtils.replace(email, "\"]", "");
        model.addAttribute("email", email);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(client.getAccessToken().getTokenValue());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<String>> categoryHttpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<List> categoryResponse = restTemplate.exchange(resourceServerUri + "/api/v1/categories", HttpMethod.GET, categoryHttpEntity, List.class);
        List<String> categories = categoryResponse.getBody();

        model.addAttribute("categories", categories);

        List<ExtendedTransaction> transactions = getAllUserTransactions(email, client.getAccessToken().getTokenValue());
        AtomicReference<Double> totalExpenses = new AtomicReference<>(0d);
        for(ExtendedTransaction transaction : transactions) {
            totalExpenses.set(totalExpenses.get() + (transaction.getSum()));
        }
        model.addAttribute("expenses", totalExpenses.get());
        return "index";
    }

    @GetMapping("/transactions")
    @SuppressWarnings("unchecked")
    public String table(Model model, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = oAuth2AuthorizedClientService
                .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        String email = StringUtils.replace((((com.nimbusds.jose.shaded.json.JSONArray) authentication.getPrincipal().getAttributes().get("emails")).toJSONString()), "[\"", "");
        email = StringUtils.replace(email, "\"]", "");
        model.addAttribute("email", email);

        List<ExtendedTransaction> transactions = getAllUserTransactions(email, client.getAccessToken().getTokenValue());

        model.addAttribute("earnings", 0);
        model.addAttribute("given_name", authentication.getPrincipal().getAttributes().get("given_name"));
        model.addAttribute("family_name", authentication.getPrincipal().getAttributes().get("family_name"));
        model.addAttribute("transactions", transactions);
        return "table";
    }

    @GetMapping("/profile")
    public String profile(Model model, OAuth2AuthenticationToken authentication) {
        model.addAttribute("given_name", authentication.getPrincipal().getAttributes().get("given_name"));
        model.addAttribute("family_name", authentication.getPrincipal().getAttributes().get("family_name"));
        //System.out.println(((com.nimbusds.jose.shaded.json.JSONArray) authentication.getPrincipal().getAttributes().get("emails")).toJSONString());
        //Matcher matcher = Pattern.compile("\\[\"(.*)\"\\]").matcher(((com.nimbusds.jose.shaded.json.JSONArray) authentication.getPrincipal().getAttributes().get("emails")).toJSONString());
        String email = StringUtils.replace((((com.nimbusds.jose.shaded.json.JSONArray) authentication.getPrincipal().getAttributes().get("emails")).toJSONString()), "[\"", "");
        email = StringUtils.replace(email, "\"]", "");
        model.addAttribute("email", email);
        return "profile";
    }


    private List<ExtendedTransaction> getAllUserTransactions(String email, String accessToken) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<List<ExtendedTransaction>> httpEntity = new HttpEntity<>(null, headers);

            ResponseEntity<List> response = restTemplate.exchange(resourceServerUri + "/api/v1/transaction?userId={name}&output=extended", HttpMethod.GET, httpEntity, List.class, email);
            return objectMapper.readValue(objectMapper.writeValueAsString(response.getBody()), new TypeReference<List<ExtendedTransaction>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
