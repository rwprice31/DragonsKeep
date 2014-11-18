package Model;

abstract class Actor
{
	
	private String name;
	private int health;	
	private int attackPower;

	//call an actor from exist account
	public Actor(String name) {
		this(name, 100, 0);
	}

    public Actor (String name, int health)
    {
        this(name, health, 0);
    }

    protected Actor(String name, int health, int attackPower)
    {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
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
