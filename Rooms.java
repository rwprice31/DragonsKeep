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
	private String [] roomDescription =  new String [50];
	private boolean [] isMonster =  new boolean [50];
	private boolean[] isElixer = new boolean[50];
	private boolean [] isArmor =  new boolean [50];
	private boolean [] isWeapon =  new boolean [50];
	private boolean [] isPuzzle =  new boolean [50];
	private boolean [] isEmpty =  new boolean [50];
	private String [] choices =  new String [50];


	/**
	 * 
	 */
	public Rooms()
	{

	}
	public Rooms(String aIntroduction, String aHowToPlay, String[] aRoomDescription, boolean[] aIsMonster,
			boolean[] aIsItem, boolean[] aIsElixer, boolean[] aIsArmor, boolean[] aIsWeapon, boolean[] aIsPuzzle, boolean[] aIsEmpty, String[] aChoices)
	{
		this.introduction = aIntroduction;
		this.howToPlay = aHowToPlay;
		this.roomDescription = aRoomDescription;
		this.isMonster = aIsMonster;
		this.isElixer = aIsElixer;
		this.isArmor = aIsArmor;
		this.isWeapon = aIsWeapon;
		this.isPuzzle = aIsPuzzle;
		this.isEmpty = aIsEmpty;
		this.choices = aChoices;
		
	}
	/**
	 * @return the introduction
	 */
	public String getIntroduction()
	{
		return introduction;
	}
	/**
	 * @return the howToPlay
	 */
	public String getHowToPlay()
	{
		return howToPlay;
	}
	/**
	 * @return the roomDescription
	 */
	public String[] getRoomDescription()
	{
		return roomDescription;
	}
	/**
	 * @return the isMonster
	 */
	public boolean[] getIsMonster()
	{
		return isMonster;
	}
	/**
	 * @return the isElixer
	 */
	public boolean[] getIsElixer()
	{
		return isElixer;
	}
	/**
	 * @return the isArmor
	 */
	public boolean[] getIsArmor()
	{
		return isArmor;
	}
	/**
	 * @return the isWeapon
	 */
	public boolean[] getIsWeapon()
	{
		return isWeapon;
	}
	/**
	 * @return the isPuzzle
	 */
	public boolean[] getIsPuzzle()
	{
		return isPuzzle;
	}
	/**
	 * @return the isEmpty
	 */
	public boolean[] getIsEmpty()
	{
		return isEmpty;
	}
	/**
	 * @return the choices
	 */
	public String[] getChoices()
	{
		return choices;
	}
	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction)
	{
		this.introduction = introduction;
	}
	/**
	 * @param howToPlay the howToPlay to set
	 */
	public void setHowToPlay(String howToPlay)
	{
		this.howToPlay = howToPlay;
	}
	/**
	 * @param roomDescription the roomDescription to set
	 */
	public void setRoomDescription(String[] roomDescription)
	{
		this.roomDescription = roomDescription;
	}
	/**
	 * @param isMonster the isMonster to set
	 */
	public void setIsMonster(boolean[] isMonster)
	{
		this.isMonster = isMonster;
	}
	/**
	 * @param isElixer the isElixer to set
	 */
	public void setIsElixer(boolean[] isElixer)
	{
		this.isElixer = isElixer;
	}
	/**
	 * @param isArmor the isArmor to set
	 */
	public void setIsArmor(boolean[] isArmor)
	{
		this.isArmor = isArmor;
	}
	/**
	 * @param isWeapon the isWeapon to set
	 */
	public void setIsWeapon(boolean[] isWeapon)
	{
		this.isWeapon = isWeapon;
	}
	/**
	 * @param isPuzzle the isPuzzle to set
	 */
	public void setIsPuzzle(boolean[] isPuzzle)
	{
		this.isPuzzle = isPuzzle;
	}
	/**
	 * @param isEmpty the isEmpty to set
	 */
	public void setIsEmpty(boolean[] isEmpty)
	{
		this.isEmpty = isEmpty;
	}
	/**
	 * @param choices the choices to set
	 */
	public void setChoices(String[] choices)
	{
		this.choices = choices;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Rooms [introduction=" + introduction + ", howToPlay="
				+ howToPlay + ", roomDescription="
				+ Arrays.toString(roomDescription) + ", isMonster="
				+ Arrays.toString(isMonster) + ", isElixer="
				+ Arrays.toString(isElixer) + ", isArmor="
				+ Arrays.toString(isArmor) + ", isWeapon="
				+ Arrays.toString(isWeapon) + ", isPuzzle="
				+ Arrays.toString(isPuzzle) + ", isEmpty="
				+ Arrays.toString(isEmpty) + ", choices="
				+ Arrays.toString(choices) + "]";
	}
	
	

}
