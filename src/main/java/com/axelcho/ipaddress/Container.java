package com.axelcho.ipaddress;

import java.util.Map;

public interface Container<T> {
    boolean match(IpAddress<T> ip);

    void add(IpAddress<T> ip);

    void delete(IpAddress<T> ip);

    T getDescription(IpAddress<T> ip);
}
