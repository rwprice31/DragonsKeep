package com.Model;

abstract class Actor {
	
	private String name;
	private int health;	
	private int attackPower;
	
	//create a new Actor
	public Actor() {		
		health = 100;
		attackPower = 0;
	}
	
	//call an actor from exist account
	public Actor(String name) {
		this.name = name;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected int getHealth() {
		return health;
	}

	protected void setHealth(int health) {
		this.health = health;
	}

	protected int getAttackPower() {
		return attackPower;
	}

	protected void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}
	
}
