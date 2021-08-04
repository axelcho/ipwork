package com.axelcho.ipaddress;

public class RecursiveIpMapContainerFactory<T> implements ContainerFactory<T> {
    @Override
    public RecursiveIpMapContainer<T> create() {
        return new RecursiveIpMapContainer<>();
    }
}
