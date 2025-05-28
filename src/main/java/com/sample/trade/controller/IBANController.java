package com.sample.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.trade.config.ConfigReader;
import com.sample.trade.model.IBANAccount;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class IBANController {

    @Autowired
    private ConfigReader configReader;

    @GetMapping("/IBAN")
    public String getIBANInfo() {
        return "IBAN information" + configReader.name() + "::" + configReader.proxy().host();
    }

    @PostMapping("/IBAN")
    public String createIBANAccount(@RequestBody IBANAccount account) {
        return "IBAN account created: " + account.toString();
    }

    @PutMapping("/IBAN/{id}")
    public String updateIBANAccount(@PathVariable String id, @RequestBody IBANAccount account) {
        return "IBAN account updated: " + account.toString();
    }

    @DeleteMapping("/IBAN/{id}")
    public String deleteIBANAccount(@PathVariable String id) {
        return "IBAN account deleted with ID: " + id;
    }

}
