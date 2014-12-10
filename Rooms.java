package Model;

import java.util.Arrays;

/**Class: Rooms
 * author Everton Gardiner Jr.
 * version 1.0
 * Course : ITEC 3150 Spring 2014
 * Written: Nov 18, 2014
 *
 * This class illustrates the structure of a room.
 *
 * Purpose: To allow the creation and querying of a room.
 */
public class Rooms
{
    //instance variables
    private String roomDescription;
	private int isMonster;
	private int isElixir;
	private int isArmor;
	private int isWeapon;
	private int isPuzzle;
	private int isEmpty; // 0 or 1 acts as sqlite boolean
	private String[] choices; //room options for exiting North south east west

    /**No argument constructor
     */
    public Rooms()
    {
        roomDescription = null;
        isMonster = 0;
        isElixir = 0;
        isArmor =  0;
        isWeapon =  0;
        isPuzzle =  0;
        isEmpty =  0;
        choices =  new String[4];
    }

    /**Sets the room exit choices
     * @param choices the exit rooms choices
     */
    public void setChoices(String[] choices)
    {
        this.choices = Arrays.copyOf(choices, choices.length);
    }

    /**Sets the room description
     * @param roomDescription The description for a room
     */
    public void setRoomDescription(String roomDescription)
    {
        this.roomDescription = roomDescription;
    }

    /**
     * @return roomDescription Gets the room description
     */
    public String getRoomDescription()
    {
        return roomDescription;
    }

    /**
     * @return isMonster Gets is there a monster in the room
     */
    public int getIsMonster()
    {
        return isMonster;
    }

    /**
     * @return isElixir Gets is there an elixir in the room
     */
    public int getIsElixir()
    {
        return isElixir;
    }

    /**
     * @return isArmor Gets is there an armor in the room
     */
    public int getIsArmor()
    {
        return isArmor;
    }

    /**
     * @return isWeapon Gets is there a weapon in the room
     */
    public int getIsWeapon()
    {
        return isWeapon;
    }

    /**
     * @return isPuzzle Gets is there a puzzle in the room
     */
    public int getIsPuzzle()
    {
        return isPuzzle;
    }

    /**
     * @return isEmpty Gets is the room empty
     */
    public int getIsEmpty()
    {
        return isEmpty;
    }

    /**
     * @return choices Gets the room exits
     */
    public String[] getChoices()
    {
        return choices;
    }

    /**Sets if there is a monster in the room
     * @param isMonster a 0 or 1 to indicate if there isn't or is a monster in the room
     */
    public void setIsMonster(int isMonster)
    {
        this.isMonster = isMonster;
    }

    /**Sets if there is an elixir in the room
     * @param isElixir a 0 or 1 to indicate if there isn't or is an elixir in the room
     */
    public void setIsElixir(int isElixir)
    {
        this.isElixir = isElixir;
    }

    /**Sets if there is an armor in the room
     * @param isArmor a 0 or 1 to indicate if there isn't or is an armor in the room
     */
    public void setIsArmor(int isArmor)
    {
        this.isArmor = isArmor;
    }

    /**Sets if there is a weapon in the room
     * @param isWeapon a 0 or 1 to indicate if there isn't or is a weapon in the room
     */
    public void setIsWeapon(int isWeapon)
    {
        this.isWeapon = isWeapon;
    }

    /**Sets if there is a puzzle in the room
     * @param isPuzzle a 0 or 1 to indicate if there isn't or is a puzzle in the room
     */
    public void setIsPuzzle(int isPuzzle)
    {
        this.isPuzzle = isPuzzle;
    }

    /**Sets the if the room is empty
     * @param isEmpty a 0 or 1 for empty or not empty
     */
    public void setIsEmpty(int isEmpty)
    {
        this.isEmpty = isEmpty;
    }
}