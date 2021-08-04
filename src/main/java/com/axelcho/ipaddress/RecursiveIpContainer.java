package com.axelcho.ipaddress;

import java.util.Map;
import java.util.TreeMap;

public class RecursiveIpContainer<T> implements Container<T>{
    protected final TreeMap<Integer, RecursiveIpContainer<T>> ipList = new TreeMap<>();
    protected boolean terminal = false;
    protected T description;


    @Override
    public boolean match(IpAddress<T> ip) {
        int first = ip.removeFirst();
        RecursiveIpContainer<T> childList = ipList.get(first);

        if (childList == null) {
            return false;
        }

        if (isTerminal()) {
            return true;
        }

        return childList.match(ip);
    }

    @Override
    public void add(IpAddress<T> ip) {

        T ipDescription = ip.getDescription();

        int first = ip.removeFirst();

        RecursiveIpContainer<T> childList = ipList.get(first);
        if (childList == null) {
            childList = new RecursiveIpContainer<>();
        }

        if(ip.size() == 0) {
            this.setTerminal(true);
            this.setDescription(ipDescription);
        }

        if (ip.size() > 0) {
            childList.add(ip);
        }

        ipList.put(first, childList);
    }

    @Override
    public void delete(IpAddress<T> ip) {
        int first = ip.removeFirst();

        RecursiveIpContainer<T> childList = ipList.get(first);
        if (childList == null) {
            throw new NullPointerException();
        }

        if (ip.size() == 0 && isTerminal()) {
            setTerminal(false);
            this.description = null;
        }

        if (ip.size() > 0) {
            childList.delete(ip);
        }

        if (childList.isEmpty()) {
            ipList.remove(first);
        }
    }


    @Override
    public T getDescription(IpAddress<T> ip) {
        int first = ip.removeFirst();
        RecursiveIpContainer<T> childList = ipList.get(first);

        if (childList == null) {
            return null;
        }

        if (isTerminal()) {
            return this.description;
        }

        return childList.getDescription(ip);
    }

    protected  void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    protected boolean isEmpty() {
        return ipList.isEmpty();
    }

    protected boolean isTerminal() {
        return terminal;
    }

    protected void setDescription(T description) {
        this.description = description;
    }
}
