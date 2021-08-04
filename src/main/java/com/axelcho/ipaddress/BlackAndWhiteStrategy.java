package com.axelcho.ipaddress;

import java.util.Map;

public class BlackAndWhiteStrategy<T> implements Strategy<T>{

    RecursiveIpMapContainer<T> whitelist;
    RecursiveIpMapContainer<T> blacklist;
    String result;

    @Override
    public String filterResult(String ip) {
        Interpreter<T> interpreter = new Interpreter<>();
        IpAddressWithMap<T> ipAddress = interpreter.interpret(ip);

        Map<String, T> whitemap = whitelist.getDescriptionMap(ipAddress);

        if (whitemap != null) {
            result = "IP address " + ip + " is good ";

            whitemap.forEach((key, value) -> {
                result += System.lineSeparator() + "key: " + key + " value: " + value;
            });

            return result;
        }

        ipAddress = interpreter.interpret(ip);
        Map<String, T> blackmap = blacklist.getDescriptionMap(ipAddress);


        if (blackmap != null) {

            result = "IP address " + ip + " is bad ";
            blackmap.forEach((key, value) -> {
                result += System.lineSeparator() + "key: " + key + " value: " + value;
            });

            return result;
        }

        return "IP address " + ip + " is good";
    }

    @Override
    public void setWhiteList(RecursiveIpMapContainer<T> whitelist) {
        this.whitelist = whitelist;
    }

    @Override
    public void setBlackList(RecursiveIpMapContainer<T> blacklist) {
        this.blacklist = blacklist;
    }
}
