package com.axelcho.ipaddress;

import java.util.Map;

public class BlackStrategy<T> implements Strategy<T>{

    RecursiveIpMapContainer<T> blacklist;
    String result;

    @Override
    public String filterResult(String ip) {

        Interpreter<T> interpreter = new Interpreter<>();
        IpAddressWithMap<T> ipAddress = interpreter.interpret(ip);

        Map<String, T> descriptionMap = blacklist.getDescriptionMap(ipAddress);

        if (descriptionMap == null) {
            return "IP address " + ip + " is good";
        }

        result = "IP address " + ip + " is bad ";

        descriptionMap.forEach((key, value) -> {
            result += System.lineSeparator() + "key: " + key + " value: " + value;
        });

        return result;

    }

    @Override
    public void setWhiteList(RecursiveIpMapContainer<T> whitelist) {

    }

    @Override
    public void setBlackList(RecursiveIpMapContainer<T> blacklist) {
        this.blacklist = blacklist;
    }
}
