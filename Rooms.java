package Model;

import java.util.ArrayList;
import java.util.Arrays;

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
    private String roomDescription;
	private int isMonster;
	private int isElixir;
	private int isArmor;
	private int isWeapon;
	private int isPuzzle;
	private int isEmpty; // 0 or 1 acts as sqlite boolean
	private String[] choices;



    public Rooms()
    {
        //roomID = new ArrayList<Integer>();;
        roomDescription = null;
        isMonster = 0;
        isElixir = 0;
        isArmor =  0;
        isWeapon =  0;
        isPuzzle =  0;
        isEmpty =  0;
        choices =  new String[4];
    }

    public void setChoices(String[] choices)
    {
        this.choices = Arrays.copyOf(choices, choices.length);
    }

//    public void addRoomID(Integer roomID)
//    {
//        this.roomID.add(roomID);
//    }

    public void setRoomDescription(String roomDescription)
    {
        this.roomDescription = roomDescription;
    }
//
//    public void addIsMonster(Integer isMonster)
//    {
//        this.isMonster = isMonster;
//    }
//
//    public void addIsElixir(Integer isElixer)
//    {
//        this.isElixir = isElixer;
//    }
//
//    public void addIsArmor(Integer isArmor)
//    {
//        this.isArmor = isArmor;
//    }
//
//    public void addIsWeapon(Integer isWeapon)
//    {
//        this.isWeapon = isWeapon;
//    }
//
//    public void addIsPuzzle(Integer isPuzzle)
//    {
//        this.isPuzzle = isPuzzle;
//    }
//
//    public void addIsEmpty(Integer isEmpty)
//    {
//        this.isEmpty = isEmpty;
//    }

//    public ArrayList<Integer> getRoomID()
//    {
//        return roomID;
//    }

    public String getRoomDescription()
    {
        return roomDescription;
    }

    public int getIsMonster()
    {
        return isMonster;
    }

    public int getIsElixir()
    {
        return isElixir;
    }

    public int getIsArmor()
    {
        return isArmor;
    }

    public int getIsWeapon()
    {
        return isWeapon;
    }

    public int getIsPuzzle()
    {
        return isPuzzle;
    }

    public int getIsEmpty()
    {
        return isEmpty;
    }

    public String[] getChoices()
    {
        return choices;
    }

    public void setIsMonster(int isMonster)
    {
        this.isMonster = isMonster;
    }

    public void setIsElixir(int isElixir)
    {
        this.isElixir = isElixir;
    }

    public void setIsArmor(int isArmor)
    {
        this.isArmor = isArmor;
    }

    public void setIsWeapon(int isWeapon)
    {
        this.isWeapon = isWeapon;
    }

    public void setIsPuzzle(int isPuzzle)
    {
        this.isPuzzle = isPuzzle;
    }

    public void setIsEmpty(int isEmpty)
    {
        this.isEmpty = isEmpty;
    }
}
