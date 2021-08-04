package com.axelcho.test;


import com.axelcho.ipaddress.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test blacklist and whitelist strategies with context.
 */
public class ContextTest {

    @Test
    public void testBlack() {
        RecursiveIpMapContainerFactory<String> factory = new RecursiveIpMapContainerFactory<>();
        RecursiveIpMapContainer<String> blacklist = factory.create();
        Interpreter<String> interpreter = new Interpreter<>();


        IpAddressWithMap<String> blackEntry1 = interpreter.interpret("1.1.1.1 issue:DNS attack");
        blacklist.add(blackEntry1);

        IpAddressWithMap<String> blackEntry2 = interpreter.interpret("2.2.2.2 issue: SQL Injection");
        blacklist.add(blackEntry2);

        IpAddressWithMap<String> blackEntry3 = interpreter.interpret("3.3.3.3 issue: proxy usage");
        blacklist.add(blackEntry3);

        IpAddressWithMap<String> blackEntry4 = interpreter.interpret("4.4.* issue: country ban");
        blacklist.add(blackEntry4);

        IpAddressWithMap<String> blackEntry5 = interpreter.interpret("4.4.4.4 issue: organization ban");
        blacklist.add(blackEntry5);

        Strategy<String> strategy = new BlackStrategy<>();
        Context<String> context = new Context<>(strategy);

        context.setBlackList(blacklist);

        assertTrue(context.filterResult("1.1.1.1").contains("bad"));
        assertTrue(context.filterResult("1.1.1.2").contains("good"));
        assertTrue(context.filterResult("4.4.4.4").contains("country"));
        assertFalse(context.filterResult("4.4.4.4").contains("organization"));
    }

    @Test
    public void testWhite() {
        RecursiveIpMapContainerFactory<String> factory = new RecursiveIpMapContainerFactory<>();
        RecursiveIpMapContainer<String> whitelist = factory.create();
        Interpreter<String> interpreter = new Interpreter<>();


        IpAddressWithMap<String> whiteEntry1 = interpreter.interpret("1.1.1.1 note:My friend");
        whitelist.add(whiteEntry1);

        IpAddressWithMap<String> whiteEntry2 = interpreter.interpret("2.2.2.2 note: My test account");
        whitelist.add(whiteEntry2);

        IpAddressWithMap<String> whiteEntry3 = interpreter.interpret("3.3.3.3 note: allowed proxy usage");
        whitelist.add(whiteEntry3);

        IpAddressWithMap<String> whiteEntry4 = interpreter.interpret("4.4.* note: country accepted");
        whitelist.add(whiteEntry4);

        IpAddressWithMap<String> whiteEntry5 = interpreter.interpret("4.4.4.4 note: organization accepted");
        whitelist.add(whiteEntry5);

        Strategy<String> strategy = new WhiteStrategy<>();
        Context<String> context = new Context<>(strategy);

        context.setWhiteList(whitelist);

        assertTrue(context.filterResult("1.1.1.1").contains("good"));
        assertTrue(context.filterResult("1.1.1.2").contains("bad"));
        assertTrue(context.filterResult("4.4.4.4").contains("good"));
        assertTrue(context.filterResult("4.4.4.4").contains("country"));
        assertFalse(context.filterResult("4.4.4.4").contains("organization"));
    }

    @Test
    public void testBlackAndWhite() {
        RecursiveIpMapContainerFactory<String> factory = new RecursiveIpMapContainerFactory<>();
        RecursiveIpMapContainer<String> whitelist = factory.create();
        RecursiveIpMapContainer<String> blacklist = factory.create();
        Interpreter<String> interpreter = new Interpreter<>();

        IpAddressWithMap<String> blackEntry1 = interpreter.interpret("1.1.1.1 issue:DNS attack");
        blacklist.add(blackEntry1);

        IpAddressWithMap<String> blackEntry2 = interpreter.interpret("2.2.2.2 issue: SQL Injection");
        blacklist.add(blackEntry2);

        IpAddressWithMap<String> blackEntry3 = interpreter.interpret("3.3.3.3 issue: proxy usage");
        blacklist.add(blackEntry3);

        IpAddressWithMap<String> blackEntry4 = interpreter.interpret("4.4.* issue: country ban");
        blacklist.add(blackEntry4);

        IpAddressWithMap<String> blackEntry5 = interpreter.interpret("4.4.4.4 issue: organization ban");
        blacklist.add(blackEntry5);

        IpAddressWithMap<String> whiteEntry1 = interpreter.interpret("1.1.1.0 note: my own test");
        whitelist.add(whiteEntry1);

        IpAddressWithMap<String> whiteEntry2 = interpreter.interpret("1.1.1.1 note:DNS Server");
        whitelist.add(whiteEntry2);

        Strategy<String> strategy = new BlackAndWhiteStrategy<>();
        Context<String> context = new Context<>(strategy);

        context.setWhiteList(whitelist);
        context.setBlackList(blacklist);



        //1.1.1.1 is on both list, and it should pass as "good" one.
        assertTrue(context.filterResult("1.1.1.1").contains("good"));

        //2.2.2.2 is only on black list
        assertTrue(context.filterResult("2.2.2.2").contains("bad"));

        //1.1.1.0 is only on white list
        assertTrue(context.filterResult("1.1.1.0").contains("good"));

        //5.5.5.5 is in neither list, and so it should pass
        assertTrue(context.filterResult("5.5.5.5").contains("good"));
    }
}
