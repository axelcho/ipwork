package com.axelcho.ipaddress;

import java.util.LinkedList;
import java.util.Map;

public class IpAddress<T> {
    protected final LinkedList<Integer> ipList = new LinkedList<>();
    protected T description;

    public LinkedList<Integer> getIpList() {
        return ipList;
    }

    public void add(int ip) {
        if (ipList.size() < 4 && ip > -1 && ip < 256) {
            ipList.add(ip);
        }
    }

    public int removeFirst() {
        return ipList.removeFirst();
    }

    public int size() {
        return ipList.size();
    }

    public void setDescription(T description) {
        this.description = description;
    }

    public T getDescription() {
        return description;
    }

    public Map<String, T> getDescriptionMap() {
        return null;
    }
}
