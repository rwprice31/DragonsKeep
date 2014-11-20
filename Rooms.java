package Model;

import java.util.ArrayList;

/**Class: Rooms
 *
@author Everton Gardiner Jr.	
 * @version 1.0
 * Course : ITEC 3150 Spring 2014
 * Written: Nov 18, 2014
 *
 *
 *
 * This class illustrates how to
 *
 * Purpose: 
 */

/**
 * @author egardiner
 *
 */
public class Rooms
{
	private String introduction;
	private String howToPlay;
    //private ArrayList<Integer> roomID;
    private ArrayList<String>  roomDescription;
	private ArrayList<Integer> isMonster;
	private ArrayList<Integer> isElixir;
	private ArrayList<Integer> isArmor;
	private ArrayList<Integer> isWeapon;
	private ArrayList<Integer> isPuzzle;
	private ArrayList<Integer> isEmpty; // 0 or 1 acts as sqlite boolean
	private ArrayList<String[]> choices;



    public Rooms()
    {
        //roomID = new ArrayList<Integer>();;
        roomDescription =  new ArrayList<String>();
        isMonster =  new ArrayList<Integer>();
        isElixir = new ArrayList<Integer>();
        isArmor =  new ArrayList<Integer>();
        isWeapon =  new ArrayList<Integer>();
        isPuzzle =  new ArrayList<Integer>();
        isEmpty =  new ArrayList<Integer>();
        choices =  new ArrayList<String[]>();
    }

    public void addChoices(String[] choices)
    {
        this.choices.add(choices);
    }

//    public void addRoomID(Integer roomID)
//    {
//        this.roomID.add(roomID);
//    }

    public void addRoomDescription(String roomDescription)
    {
        this.roomDescription.add(roomDescription);
    }

    public void addIsMonster(Integer isMonster)
    {
        this.isMonster.add(isMonster);
    }

    public void addIsElixir(Integer isElixer)
    {
        this.isElixir.add(isElixer);
    }

    public void addIsArmor(Integer isArmor)
    {
        this.isArmor.add(isArmor);
    }

    public void addIsWeapon(Integer isWeapon)
    {
        this.isWeapon.add(isWeapon);
    }

    public void addIsPuzzle(Integer isPuzzle)
    {
        this.isPuzzle.add(isPuzzle);
    }

    public void addIsEmpty(Integer isEmpty)
    {
        this.isEmpty.add(isEmpty);
    }

//    public ArrayList<Integer> getRoomID()
//    {
//        return roomID;
//    }

    public ArrayList<String> getRoomDescription()
    {
        return roomDescription;
    }

    public ArrayList<Integer> getIsMonster()
    {
        return isMonster;
    }

    public ArrayList<Integer> getIsElixir()
    {
        return isElixir;
    }

    public ArrayList<Integer> getIsArmor()
    {
        return isArmor;
    }

    public ArrayList<Integer> getIsWeapon()
    {
        return isWeapon;
    }

    public ArrayList<Integer> getIsPuzzle()
    {
        return isPuzzle;
    }

    public ArrayList<Integer> getIsEmpty()
    {
        return isEmpty;
    }

    public ArrayList<String[]> getChoices()
    {
        return choices;
    }
}
