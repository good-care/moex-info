package com.mokhovav.goodcare_moex_info;

import com.mokhovav.goodcare_moex_info.exceptions.GoodCareException;
import com.mokhovav.goodcare_moex_info.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestRequestsService {

    private final Logger logger;
    private final RestTemplate restTemplate;

    @Autowired
    public RestRequestsService(Logger logger, RestTemplateBuilder restTemplateBuilder) {
        this.logger = logger;
        this.restTemplate = restTemplateBuilder.build();
    }

    public Object getPostInJson(String url, Class c) throws GoodCareException {
        Object result = null;
        try {
            result = restTemplate.getForObject(url, c);
        } catch (RestClientException e) {
            throw new GoodCareException(this, "Bad response to request: url = " + url + " class = " + c.getName());
        }
        return result;
    }
}
