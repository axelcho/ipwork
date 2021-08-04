package com.axelcho.test;

import com.axelcho.ipaddress.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecursiveContainerTest {

    @Test
    public void testRecursiveIpAddress() {

        /**
         * Test for version 1
         */
        ContainerFactory<String> factory = new RecursiveIpContainerFactory<>();
        Container<String> container = factory.create();

        IpAddress<String> ipEntry1 = new IpAddress<>();
        ipEntry1.add(1);
        ipEntry1.add(1);
        ipEntry1.add(1);
        ipEntry1.add(1);

        container.add(ipEntry1);

        IpAddress<String> ipEntry2 = new IpAddress<>();
        ipEntry2.add(2);
        ipEntry2.add(2);

        container.add(ipEntry2);

        IpAddress<String> ipEntry3 = new IpAddress<>();
        ipEntry3.add(2);
        ipEntry3.add(2);
        ipEntry3.add(2);
        ipEntry3.add(2);

        container.add(ipEntry3);


        IpAddress<String> check1 = new IpAddress<>();
        check1.add(1);
        check1.add(1);
        check1.add(1);
        check1.add(1);

        assertTrue(container.match(check1));

        IpAddress<String> check2 = new IpAddress<>();
        check2.add(1);
        check2.add(1);
        check2.add(1);
        check2.add(0);

        assertFalse(container.match(check2));


        IpAddress<String> check3 = new IpAddress<>();
        check3.add(2);
        check3.add(2);
        check3.add(2);
        check3.add(2);

        assertTrue(container.match(check3));

        IpAddress<String> delete = new IpAddress<>();
        delete.add(2);
        delete.add(2);

        container.delete(delete);


        IpAddress<String> check4 = new IpAddress<>();
        check4.add(2);
        check4.add(2);
        check4.add(2);
        check4.add(2);

        assertTrue(container.match(check4));
    }

    /**
     * Test for version 1
     */
    @Test
    public void testWithDescription() {
        RecursiveIpContainerFactory<String> factory = new RecursiveIpContainerFactory<>();
        Container<String> container = factory.create();



        IpAddress<String> ipEntry1 = new IpAddress<>();
        ipEntry1.add(1);
        ipEntry1.add(1);
        ipEntry1.add(1);
        ipEntry1.add(1);
        ipEntry1.setDescription("DNS attack");

        container.add(ipEntry1);

        IpAddress<String> ipEntry2 = new IpAddress<>();
        ipEntry2.add(2);
        ipEntry2.add(2);
        ipEntry2.setDescription("Blanket ban");

        container.add(ipEntry2);

        IpAddress<String> ipEntry3 = new IpAddress<>();
        ipEntry3.add(2);
        ipEntry3.add(2);
        ipEntry3.add(2);
        ipEntry3.add(2);
        ipEntry3.setDescription("Specific ban");

        container.add(ipEntry3);


        IpAddress<String> check1 = new IpAddress<>();
        check1.add(1);
        check1.add(1);
        check1.add(1);
        check1.add(1);

        assertEquals("DNS attack", container.getDescription(check1));

        IpAddress<String> check2 = new IpAddress<>();
        check2.add(1);
        check2.add(1);
        check2.add(1);
        check2.add(0);

        assertNull(container.getDescription(check2));

        IpAddress<String> check3 = new IpAddress<>();
        check3.add(2);
        check3.add(2);
        check3.add(2);
        check3.add(2);

        assertEquals("Blanket ban", container.getDescription(check3));

        IpAddress<String> delete = new IpAddress<>();
        delete.add(2);
        delete.add(2);

        container.delete(delete);


        IpAddress<String> check4 = new IpAddress<>();
        check4.add(2);
        check4.add(2);
        check4.add(2);
        check4.add(2);

        assertEquals("Specific ban", container.getDescription(check4));
    }

    /**
     * Added mapping to the structure
     */
    @Test
    public void testIpMapWithMap() {
        RecursiveIpMapContainerFactory<String> factory = new RecursiveIpMapContainerFactory<>();
        RecursiveIpMapContainer<String> container = factory.create();

        IpAddressWithMap<String> ipEntry1 = new IpAddressWithMap<>();
        ipEntry1.add(1);
        ipEntry1.add(1);
        ipEntry1.add(1);
        ipEntry1.add(1);
        ipEntry1.addDescription("issue", "DNS attack");

        container.add(ipEntry1);

        IpAddressWithMap<String> ipEntry2 = new IpAddressWithMap<>();
        ipEntry2.add(2);
        ipEntry2.add(2);
        ipEntry2.addDescription("region", "Blanket ban");

        container.add(ipEntry2);

        IpAddressWithMap<String> ipEntry3 = new IpAddressWithMap<>();
        ipEntry3.add(2);
        ipEntry3.add(2);
        ipEntry3.add(2);
        ipEntry3.add(2);
        ipEntry3.addDescription("issue", "Specific ban");

        container.add(ipEntry3);

        IpAddress<String> check1 = new IpAddress<>();
        check1.add(1);
        check1.add(1);
        check1.add(1);
        check1.add(1);

        //map is returned with key "issue"
        assertEquals("DNS attack", container.getDescriptionMap(check1).get("issue"));

        IpAddress<String> check2 = new IpAddress<>();
        check2.add(1);
        check2.add(1);
        check2.add(1);
        check2.add(1);

        //map is returned, but it does not have key "region"
        assertNull(container.getDescriptionMap(check2).get("region"));

        IpAddress<String> check3 = new IpAddress<>();
        check3.add(1);
        check3.add(1);
        check3.add(1);
        check3.add(0);

        //map is missing
        assertNull(container.getDescriptionMap(check3));

        IpAddress<String> check4 = new IpAddress<>();
        check4.add(2);
        check4.add(2);
        check4.add(2);
        check4.add(2);

        assertEquals("Blanket ban", container.getDescriptionMap(check4).get("region"));

        //remove regional ban
        IpAddressWithMap<String> delete = new IpAddressWithMap<>();
        delete.add(2);
        delete.add(2);
        delete.addDescription("region", "Blanket ban removed");

        container.delete(delete);

        IpAddress<String> check5 = new IpAddress<>();
        check5.add(2);
        check5.add(2);
        check5.add(2);
        check5.add(2);

        assertNull(container.getDescriptionMap(check5).get("region"));


        IpAddress<String> check6 = new IpAddress<>();
        check6.add(2);
        check6.add(2);
        check6.add(2);
        check6.add(2);

        assertEquals("Specific ban", container.getDescriptionMap(check6).get("issue"));
    }
}
