package JunitTestCases;

import Model.Inventory;
import Model.Weapon;
import Model.Armor;
import Model.Elixir;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3860 Fall 2014
 * Written: 12/5/2014
 *
 * This class represents an Inventory JUnit Test.
 *
 * Purpose: Tests all the methods in the Inventory class.
 */
public class InventoryTest
{
    Inventory inventory;
    Weapon weapon;
    Armor armor;
    Elixir elixir;

    @Before
    public void setUp() throws Exception
    {
        inventory = new Inventory();

        weapon = new Weapon("testWeapon", 27);
        armor = new Armor("testArmor", 14);
        elixir = new Elixir("testElixir", 9);

        inventory.add(weapon);
        inventory.add(armor);
        inventory.add(elixir);
    }

    @Test
    public void testAddWeapon() throws Exception
    {
        assertTrue(inventory.add(weapon));
    }

    @Test
    public void testAddArmor() throws Exception
    {
        assertTrue(inventory.add(armor));
    }

    @Test
    public void testAddElixir() throws Exception
    {
        assertTrue(inventory.add(elixir));
    }

    @Test
    public void testGetWeapon() throws Exception
    {
        assertEquals(weapon, inventory.getWeapon("testWeapon"));

        assertNotEquals(weapon, inventory.getWeapon(null));
        assertNotEquals(weapon, inventory.getWeapon(""));
        assertNotEquals(weapon, inventory.getWeapon(" "));
    }

    @Test
    public void testGetElixir() throws Exception
    {
        assertEquals(elixir, inventory.getElixir("testElixir"));

        assertNotEquals(elixir, inventory.getWeapon(null));
        assertNotEquals(elixir, inventory.getWeapon(""));
        assertNotEquals(elixir, inventory.getWeapon(" "));
    }

    @Test
    public void testGetArmor() throws Exception
    {
        assertEquals(armor, inventory.getArmor("testArmor"));

        assertNotEquals(armor, inventory.getArmor(null));
        assertNotEquals(armor, inventory.getArmor(""));
        assertNotEquals(armor, inventory.getArmor(" "));
    }

    @Test
    public void testConfirmItem() throws Exception
    {
        assertTrue(inventory.confirmItem("testWeapon"));
        assertTrue(inventory.confirmItem("testElixir"));
        assertTrue(inventory.confirmItem("testArmor"));

        assertFalse(inventory.confirmItem("weapon"));
        assertFalse(inventory.confirmItem("elixir"));
        assertFalse(inventory.confirmItem("armor"));

        assertFalse(inventory.confirmItem(null));
        assertFalse(inventory.confirmItem(""));
        assertFalse(inventory.confirmItem(" "));
    }

    @Test
    public void testGetItemType() throws Exception
    {
        inventory.view();

        assertEquals("w", inventory.getItemType("testWeapon"));
        assertEquals("e", inventory.getItemType("testElixir"));
        assertEquals("a", inventory.getItemType("testArmor"));

        assertNotEquals("a", inventory.getItemType("testWeapon"));
        assertNotEquals("w", inventory.getItemType("testElixir"));
        assertNotEquals("e", inventory.getItemType("testArmor"));

        assertNotEquals("a", inventory.getItemType(null));
        assertNotEquals("w", inventory.getItemType(""));
        assertNotEquals("e", inventory.getItemType(" "));
    }

    @Test
    public void testRemove() throws Exception
    {
        assertTrue(inventory.remove("testWeapon"));
        assertTrue(inventory.remove("testElixir"));
        assertTrue(inventory.remove("testArmor"));

        assertFalse(inventory.remove("armorIV"));
        assertFalse(inventory.remove(null));
        assertFalse(inventory.remove(""));
        assertFalse(inventory.remove(" "));
    }

    @Test
    public void testRemoveQuery() throws Exception
    {
        assertTrue(inventory.remove("testElixir"));

        assertNotEquals("e", inventory.getItemType("testElixir"));
        assertFalse(inventory.confirmItem("testElixir"));

        assertTrue(inventory.confirmItem("testWeapon"));
        assertTrue(inventory.confirmItem("testArmor"));

        assertEquals("w", inventory.getItemType("testWeapon"));
        assertEquals("a", inventory.getItemType("testArmor"));
    }
}