package com.Model;

public class Monster extends Actor{

	private int attackPower;	
	private String monsterAttributes;
	
	//call Monster from monster table
	public Monster(String monsterAttributes) {
		super();
		this.monsterAttributes = monsterAttributes;
	}
	
	protected int getAttackPower() {
		return attackPower;
		
	}

}
