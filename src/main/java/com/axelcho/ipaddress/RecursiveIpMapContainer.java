package com.axelcho.ipaddress;


import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RecursiveIpMapContainer<V> extends RecursiveIpContainer<V> {
    private final TreeMap<Integer, RecursiveIpMapContainer<V>> ipListWithMap = new TreeMap<>();
    private Map<String, V> descriptionMap = new HashMap<>();

    @Override
    public boolean match(IpAddress<V> ip) {
        int first = ip.removeFirst();
        RecursiveIpMapContainer<V> childList = ipListWithMap.get(first);

        if (childList == null) {
            return false;
        }

        if (isTerminal()) {
            return true;
        }

        return childList.match(ip);
    }

    @Override
    public void add(IpAddress<V> ip) {
        int first = ip.removeFirst();

        RecursiveIpMapContainer<V> childList = ipListWithMap.get(first);
        if (childList == null) {
            childList = new RecursiveIpMapContainer<>();
        }

        if (ip.size() == 0) {
            Map<String, V> ipDescriptionMap = ip.getDescriptionMap();
            this.setTerminal(true);
            ipDescriptionMap.forEach((key, value) -> descriptionMap.put(key, value));
        }

        if (ip.size() > 0) {
            childList.add(ip);
        }

        ipListWithMap.put(first, childList);
    }

    public void delete(IpAddressWithMap<V> ip) {
        int first = ip.removeFirst();

        RecursiveIpMapContainer<V> childList = ipListWithMap.get(first);
        if (childList == null) {
            throw new NullPointerException();
        }

        if (ip.size() == 0 && isTerminal()) {
            Map<String, V> ipDescriptionMap = ip.getDescriptionMap();

            ipDescriptionMap.forEach((key, value) -> descriptionMap.remove(key));

            if (descriptionMap.isEmpty()) {
                setTerminal(false);
                description = null;
                descriptionMap = null;
            }
        }

        if (ip.size() > 0) {
            childList.delete(ip);
        }

        if (childList.isEmpty()) {
            ipListWithMap.remove(first);
        }
    }

    public Map<String, V> getDescriptionMap(IpAddress<V> ip) {
        int first = ip.removeFirst();
        RecursiveIpMapContainer<V> childList = ipListWithMap.get(first);

        if (childList == null) {
            return null;
        }

        if (isTerminal()) {
            return this.descriptionMap;
        }
        return childList.getDescriptionMap(ip);
    }

    @Override
    protected boolean isEmpty() {
        return ipListWithMap.isEmpty();
    }
}
