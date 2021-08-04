package com.axelcho.ipaddress;

import java.util.Map;

public interface Strategy<T> {
    public String filterResult(String ip);

    public void setWhiteList(RecursiveIpMapContainer<T> whitelist);

    public void setBlackList(RecursiveIpMapContainer<T> blacklist);
}
