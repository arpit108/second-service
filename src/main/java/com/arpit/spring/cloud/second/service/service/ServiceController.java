package com.arpit.spring.cloud.second.service.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class ServiceController {
    @Autowired
   EurekaClient client;

    @GetMapping("/callFirstService")
    public String callFirstService()
    {
        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo nextServerFromEureka = client.getNextServerFromEureka("first-service", false);
        String basURL=nextServerFromEureka.getHomePageUrl();
        ResponseEntity <String> response=  restTemplate.exchange(basURL,HttpMethod.GET,null,String.class);
       // System.out.print(response.getBody());
       return response.getBody();
    }

    @GetMapping("/")
    public String callService()
    {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity("http://first-service/",String.class).getBody();
    }


}
