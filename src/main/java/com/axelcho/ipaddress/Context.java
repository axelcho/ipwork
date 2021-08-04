package com.axelcho.ipaddress;

public class Context<T> {
    private final Strategy<T> strategy;

    public Context(Strategy<T> strategy) {
        this.strategy = strategy;
    }

    public void setWhiteList(RecursiveIpMapContainer<T> whitelist) {
        strategy.setWhiteList(whitelist);
    }

    public void setBlackList(RecursiveIpMapContainer<T> blacklist) {
        strategy.setBlackList(blacklist);
    }

    public String filterResult(String ip) {
        return strategy.filterResult(ip);
    }
}
