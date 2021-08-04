package com.axelcho.test;

import com.axelcho.ipaddress.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTest {

    /**
     * basic interpreter handling
     */
    @Test
    public void testInterpreter() {
        Interpreter<String> interpreter = new Interpreter<>();

        IpAddressWithMap<String> ipAddress = interpreter.interpret("1.2.3.4 region : generic, issue : unknown");

        assertEquals("generic", ipAddress.getDescriptionMap().get("region"));
        assertEquals("unknown", ipAddress.getDescriptionMap().get("issue"));

        assertEquals(1, ipAddress.removeFirst());
        assertEquals(2, ipAddress.removeFirst());
        assertEquals(3, ipAddress.removeFirst());
        assertEquals(4, ipAddress.removeFirst());


        //test parsing with wildcard
        IpAddressWithMap<String> ipAddress2 = interpreter.interpret("1.2.* region: wide area");
        assertEquals("wide area", ipAddress2.getDescriptionMap().get("region"));
    }


    /**
     * Test mapping and dictionary with interpreter
     */
    @Test
    public void matchTestWithInterpreter()  {
        RecursiveIpMapContainerFactory<String> factory = new RecursiveIpMapContainerFactory<>();
        RecursiveIpMapContainer<String> container = factory.create();

        Interpreter<String> interpreter = new Interpreter<>();

        IpAddressWithMap<String> ipEntry1 = interpreter.interpret("1.1.1.1 issue: DNS attack");
        container.add(ipEntry1);

        IpAddressWithMap<String> ipEntry2 = interpreter.interpret("2.2.* region: Blanket ban");
        container.add(ipEntry2);

        IpAddressWithMap<String> ipEntry3 = interpreter.interpret("2.2.2.2 issue: Specific ban");
        container.add(ipEntry3);

        IpAddress<String> check1 = interpreter.interpret("1.1.1.1");
        assertTrue(container.match(check1));

        IpAddress<String> check3 = interpreter.interpret("1.1.1.0");
        assertFalse(container.match(check3));

        IpAddress<String> check4 = interpreter.interpret("2.2.2.2");
        assertTrue(container.match(check4));

        IpAddressWithMap<String> delete = interpreter.interpret("2.2.* region: ban removed");
        container.delete(delete);

        IpAddress<String> check5 = interpreter.interpret("2.2.2.2");
        assertTrue(container.match(check5));

    }

    @Test
    public void testIpMapWithInterpreter() {
        RecursiveIpMapContainerFactory<String> factory = new RecursiveIpMapContainerFactory<>();
        RecursiveIpMapContainer<String> container = factory.create();

        Interpreter<String> interpreter = new Interpreter<>();

        IpAddressWithMap<String> ipEntry1 = interpreter.interpret("1.1.1.1 issue: DNS attack");
        container.add(ipEntry1);

        IpAddressWithMap<String> ipEntry2 = interpreter.interpret("2.2.* region: Blanket ban");
        container.add(ipEntry2);

        IpAddressWithMap<String> ipEntry3 = interpreter.interpret("2.2.2.2 issue: Specific ban");
        container.add(ipEntry3);

        IpAddress<String> check1 = interpreter.interpret("1.1.1.1");
        //map is returned with key "issue"
        assertEquals("DNS attack", container.getDescriptionMap(check1).get("issue"));

        IpAddress<String> check2 = interpreter.interpret("1.1.1.1");

        //map is returned, but it does not have key "region"
        assertNull(container.getDescriptionMap(check2).get("region"));

        IpAddress<String> check3 = interpreter.interpret("1.1.1.0");

        //map is missing
        assertNull(container.getDescriptionMap(check3));

        IpAddress<String> check4 = interpreter.interpret("2.2.2.2");

        assertEquals("Blanket ban", container.getDescriptionMap(check4).get("region"));

        //remove regional ban
        IpAddressWithMap<String> delete = interpreter.interpret("2.2.* region: ban removed");
        container.delete(delete);

        IpAddress<String> check5 = interpreter.interpret("2.2.2.2");
        //regional ban is not applied any more
        assertNull(container.getDescriptionMap(check5).get("region"));
        IpAddress<String> check6 = interpreter.interpret("2.2.2.2");

        //specific ban is still applied
        assertEquals("Specific ban", container.getDescriptionMap(check6).get("issue"));
    }
}
