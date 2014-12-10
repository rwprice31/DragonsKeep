package Controller;

import org.junit.*;

import static org.junit.Assert.*;

public class ControllerLoadTest
{
    private static Controller tdb = new Controller();
    private static int playerID;

    @BeforeClass
    public static void  setUp() throws Exception
    {
        playerID = Controller.createAccount("JUnitTestName");
        ControllerUpdate.saveHeroData(playerID + "|JUnitTestName|1|77|61");

        String rooms = playerID + "|" + 14;
        for (int i = 1; i <= 50;  i++)
        {
            rooms += "|" + (i%2);
        }
        ControllerUpdate.saveRoomState(rooms);

        String[][] tempInventory = new String[3][2];
        tempInventory[0][0] = "dagger";
        tempInventory[0][1] = "w";
        tempInventory[1][0] = "Standard Armor";
        tempInventory[1][1] = "a";
        tempInventory[2][0] = "Red Elixir";
        tempInventory[2][1] = "e";

        ControllerUpdate.saveHeroInventory(playerID, tempInventory);
    }

    @AfterClass
    public static void tearDown() throws Exception
    {
        tdb.modData("DELETE FROM playerFile WHERE name = \'JUnitTestName\'");
        tdb.modData("DELETE FROM playerRooms WHERE playerID = " + playerID);
        tdb.modData("DELETE FROM savedInventory WHERE playerID = " + playerID);
    }

    @Test
    public void testLoadSavedRooms() throws Exception
    {
        String rooms = playerID + "|" + 14;
        for (int i = 1; i <= 50;  i++)
        {
            rooms += "|" + (i%2);
        }
        assertEquals(rooms, ControllerLoad.loadSavedRooms(playerID));
    }

    @Test
    public void testLoadHero() throws Exception
    {
        String expected = playerID + "|JUnitTestName|1|77|61|";

        assertEquals(expected, ControllerLoad.loadHero("JUnitTestName"));
    }

    @Test
    public void testLoadHeroInventory() throws Exception
    {
        String expected = "1|0|0|0|1|0|0|0|3|";

        assertEquals(expected, ControllerLoad.loadHeroInventory(playerID));
    }

    @Test
    public void testRetrieveAllRooms() throws Exception
    {
        String[] actual = ControllerLoad.retrieveAllRooms().split("[|]]");

        for(int i = 0; i < actual.length-11; i = i+12)
        {
            assertTrue(Integer.parseInt(actual[i]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 1]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 2]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 3]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 4]) >= 0);

            assertTrue(actual[i + 5] != null);

            assertTrue(Integer.parseInt(actual[i + 6]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 7]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 8]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 9]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 10]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 11]) >= 0);
        }
    }

    @Test
    public void testRetrieveMonster() throws Exception
    {
        String expected = "Blue Dragon|31|51";

        assertEquals(expected, ControllerLoad.retrieveMonster(5));
    }

    @Test
    public void testRetrieveArmor() throws Exception
    {
        String expected = "Dragon Scales|48";

        assertEquals(expected, ControllerLoad.retrieveArmor(3));
    }

    @Test
    public void testRetrieveElixir() throws Exception
    {
        String expected = "Blue Elixir|10";

        assertEquals(expected, ControllerLoad.retrieveElixir(1));
    }

    @Test
    public void testRetrieveWeapon() throws Exception
    {
        String expected = "Axe|8";

        assertEquals(expected, ControllerLoad.retrieveWeapon(6));
    }

    @Test
    public void testRetrievePuzzle() throws Exception
    {
        StringBuilder expected = new StringBuilder("its completely dark. Along the wall is a flashing red light. It " +
                "seems\nto indicate something used to be in the empty candle holder");
        expected.append("|use roman candle sticks");
        expected.append("|The room is now lit and you can see, shining across the room some armor");
        expected.append("|nothing happened");
        expected.append("|0");

        assertEquals(expected.toString(), ControllerLoad.retrievePuzzle(2));
    }
}