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

    public String getName() {
		return name;
	}

    public void setName(String name) {
		this.name = name;
	}

    public int getHealth() {
		return health;
	}

    public void setHealth(int health) {
		this.health = health;
	}

    public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}
	
}
