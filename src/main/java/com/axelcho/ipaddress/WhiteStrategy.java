package com.axelcho.ipaddress;

import java.util.Map;

public class WhiteStrategy<T> implements Strategy<T>{

    RecursiveIpMapContainer<T> whitelist;
    String result;


    @Override
    public String filterResult(String ip) {
        Interpreter<T> interpreter = new Interpreter<>();
        IpAddressWithMap<T> ipAddress = interpreter.interpret(ip);

        Map<String, T> descriptionMap = whitelist.getDescriptionMap(ipAddress);

        if (descriptionMap == null) {
            return "IP address " + ip + " is bad";
        }

        result = "IP address " + ip + " is good ";

        descriptionMap.forEach((key, value) -> {
            result += System.lineSeparator() + "key: " + key + " value: " + value;
        });

        return result;
    }

    @Override
    public void setWhiteList(RecursiveIpMapContainer<T> whitelist) {
        this.whitelist = whitelist;
    }

    @Override
    public void setBlackList(RecursiveIpMapContainer<T> blacklist) {

    }
}
