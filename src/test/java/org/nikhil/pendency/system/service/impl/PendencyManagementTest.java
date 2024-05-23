package org.nikhil.pendency.system.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nikhil.pendency.system.repository.TagRepository;
import org.nikhil.pendency.system.repository.TransactionRepository;
import org.nikhil.pendency.system.repository.impl.InMemoryTagRepository;
import org.nikhil.pendency.system.repository.impl.InMemoryTransactionRepository;
import org.nikhil.pendency.system.service.PendencyManagementService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PendencyManagementTest {

    private static PendencyManagementService pendencyManagementService;

    @BeforeAll
    public static void setup(){
        TransactionRepository transactionRepository = InMemoryTransactionRepository.getInstance();
        TagRepository tagRepository = InMemoryTagRepository.getInstance();
        pendencyManagementService = new PendencyManagementServiceImpl(transactionRepository, tagRepository);
    }

    @Test
    public void endToEndIntegrationTest(){
        pendencyManagementService.startTracking(1112, Arrays.asList("UPI", "Karnataka", "Bangalore"));
        pendencyManagementService.startTracking(2451, Arrays.asList("UPI", "Karnataka", "Mysore"));
        pendencyManagementService.startTracking(3421, Arrays.asList("UPI", "Rajasthan", "Jaipur"));
        pendencyManagementService.startTracking(1221, Arrays.asList("Wallet", "Karnataka", "Bangalore"));
        Integer result1 = pendencyManagementService.getCounts(Arrays.asList("UPI"));
        assertEquals(3, result1);
        Integer result2 = pendencyManagementService.getCounts(Arrays.asList("UPI", "Karnataka"));
        assertEquals(2, result2);
        Integer result3 = pendencyManagementService.getCounts(Arrays.asList("UPI", "Karnataka", "Bangalore"));
        assertEquals(1, result3);
        Integer result4 = pendencyManagementService.getCounts(Arrays.asList("Wallet"));
        assertEquals(1, result4);
        Integer result5 = pendencyManagementService.getCounts(Arrays.asList("Bangalore"));
        assertEquals(0, result5);
        pendencyManagementService.startTracking(4221, Arrays.asList("Wallet", "Karnataka", "Bangalore"));
        pendencyManagementService.stopTracking(1112);
        pendencyManagementService.stopTracking(2451);
        Integer result6 = pendencyManagementService.getCounts(Arrays.asList("UPI"));
        assertEquals(1, result6);
        Integer result7 = pendencyManagementService.getCounts(Arrays.asList("Wallet"));
        assertEquals(2, result7);
        Integer result8 = pendencyManagementService.getCounts(Arrays.asList("UPI", "Karnataka", "Bangalore"));
        assertEquals(0, result8);
    }

    @Test
    public void endToEndIntegrationTest2(){
        pendencyManagementService.startTracking(1, Arrays.asList("Mumbai","KFC","Fast Food","Burger"));
        pendencyManagementService.startTracking(2, Arrays.asList("Mumbai","KFC","Fast Food","Pizza"));
        pendencyManagementService.startTracking(3, Arrays.asList("Mumbai","McD","Quick food", "Burger"));
        pendencyManagementService.startTracking(4, Arrays.asList("Gurgaon", "KFC", "Fast Food", "Burger"));
        assertEquals(3,pendencyManagementService.getCounts(Arrays.asList("Mumbai")));
        assertEquals(2,pendencyManagementService.getCounts(Arrays.asList("Mumbai", "KFC")));
        assertEquals(1,pendencyManagementService.getCounts(Arrays.asList("Mumbai", "McD", "Quick food", "Burger")));
        pendencyManagementService.startTracking(5, Arrays.asList("Mumbai","McD","Quick food","Burger"));
        assertEquals(2,pendencyManagementService.getCounts(Arrays.asList("Mumbai", "McD", "Quick food", "Burger")));
        assertEquals(0,pendencyManagementService.getCounts(Arrays.asList("Chennai")));
        assertEquals(0,pendencyManagementService.getCounts(Arrays.asList("KFC")));
        assertEquals(0,pendencyManagementService.getCounts(Arrays.asList("Fast food")));
        pendencyManagementService.startTracking(6, Arrays.asList("Chennai", "Pizza Hut", "Italian", "Pizza"));
        pendencyManagementService.stopTracking(5);
        pendencyManagementService.stopTracking(4);
        assertEquals(1,pendencyManagementService.getCounts(Arrays.asList("Chennai")));
        assertEquals(1,pendencyManagementService.getCounts(Arrays.asList("Chennai", "Pizza Hut")));
        assertEquals(1,pendencyManagementService.getCounts(Arrays.asList("Chennai", "Pizza Hut", "Italian")));
        assertEquals(2,pendencyManagementService.getCounts(Arrays.asList("Mumbai","KFC")));
        pendencyManagementService.stopTracking(1);
        assertEquals(1,pendencyManagementService.getCounts(Arrays.asList("Mumbai","KFC")));
        assertEquals(0,pendencyManagementService.getCounts(Arrays.asList("Gurgaon","KFC")));
    }

}