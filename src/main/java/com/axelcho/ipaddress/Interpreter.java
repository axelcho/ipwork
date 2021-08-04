package com.axelcho.ipaddress;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Interpreter<T> {

    protected IpAddressWithMap<T> ipAddress = new IpAddressWithMap<>();

    protected String ipText;

    protected String ipNumericText;

    protected String ipMapText;

    public IpAddressWithMap<T> interpret(String ipText) {
        ipAddress =new IpAddressWithMap<>();
        ipNumericText = null;
        ipMapText = null;

        this.ipText = ipText;
        splitNumericAndMap();
        parseIpNumeric();
        parseIpMap();
        return ipAddress;
    }

    protected void splitNumericAndMap() {
        try {
            String[] parts = ipText.split(" ", 2);
            ipNumericText = parts[0];
            ipMapText = parts[1];

        } catch (Exception e) {
            ipNumericText = ipText;
            ipMapText = null;
        }
    }

    protected void parseIpNumeric() {
        String[] numerics = ipNumericText.split("\\.");

        Stream.of(numerics)
                .filter(e -> e.matches("\\d+"))
                .map(Integer::parseInt)
                .forEach(ipAddress::add);
    }

    //build map
    protected void parseIpMap() {
        if (ipMapText != null) {
            String[] mapParts = ipMapText.split(",");
            Map<String, T> descriptionMap = new HashMap<>();

            Stream.of(mapParts)
                    .filter(e -> e.contains(":"))
                    .forEach(e -> {
                        String[] parts = e.split(":", 2);

                        String key = parts[0].trim();
                        T value = (T) parts[1].trim();

                        descriptionMap.put(key, value);
                    });

            ipAddress.setDescriptionMap(descriptionMap);
        }
    }
}
