package com.axelcho.ipaddress;

public interface ContainerFactory<T> {
    public Container<T> create();
}
