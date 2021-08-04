package com.axelcho.ipaddress;

public class RecursiveIpContainerFactory<T> implements ContainerFactory<T>{
    @Override
    public Container<T> create() {
        return new RecursiveIpContainer<>();
    }
}
