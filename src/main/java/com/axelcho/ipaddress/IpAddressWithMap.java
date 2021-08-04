package com.axelcho.ipaddress;

import java.util.HashMap;
import java.util.Map;

public class IpAddressWithMap<V> extends IpAddress<V>{
   Map<String,V> descriptionMap = new HashMap<>();

   @Override
   public void setDescription(V description) {
      descriptionMap.clear();
      descriptionMap.put("default", description);
      this.description = description;
   }

   @Override
   public V getDescription() {
      return descriptionMap.get("default");
   }

   public void addDescription(String key, V value) {
      descriptionMap.put(key, value);
   }

   public void removeDescription(String key) {
      descriptionMap.remove(key);
   }

   public Map<String, V> getDescriptionMap() {
      return descriptionMap;
   }

   public void setDescriptionMap(Map<String, V> descriptionMap) {
      this.descriptionMap = descriptionMap;
   }
}
