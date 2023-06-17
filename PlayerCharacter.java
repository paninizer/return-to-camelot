package unit_16_to_18;

import java.util.ArrayList;

public class PlayerCharacter {
	enum Skills {
		EXCALIBUR,
		AVALON,
		AVALON_PROTO,
		CAMELOT_WALL,
		RHONGOMYNIAD,
		RHONGOMYNIAD_CHARGE,
		HOLY_HAND_GRENADE,
		CURSED_ONSET,
		CURSED_ONSET_PROTO,
		UNWAVERING_LOYALTY,
		LONGINUS_ZERO,
		GALATINE,
		FAILNAUGHT,
		IRA_LUPUS,
		RABBIT,
		
		CLARENT,
		HP_RESTORE,
		ARROW_RAIN,
		DEFENSE_UP,
		OFFENSE_UP,
		INVINCIBILITY_TEMP
	}
	
	enum Classes {
		SABER,
		CASTER,
		ARCHER,
		LANCER,
		SHIELDER,
		ASSASSIN,
		CAVALRY,
		SOVEREIGN
	}
	
	String name;
	int level = 0;
	int hp, maxhp;
	int atk, dfs, mna;
	int baseatk, basedfs, basemna;
	int invicibilityDuration;
	boolean isAvailable = true;
	//int shld;
	Classes type;
	boolean isSkillRcg = true;
	int skillCD;
	boolean isInvincible;
	Skills skill;
	
	//constructor for character with >0 level
	public PlayerCharacter(String name, int level, int hp, int atk, int dfs, int mna, Skills skill, Classes type) {
		this.name = name;
		this.level = level;
		this.maxhp = hp;
		this.baseatk = atk;
		this.basedfs = dfs;
		this.basemna = mna;
		this.skill = skill;
		this.type = type;
	}
	
	//constructor overload for initializing save (all characters are level 0)
	public PlayerCharacter(String name, int hp, int atk, int dfs, int mna, Skills skill, Classes type) {
		this.name = name;
		this.maxhp = hp;
		this.baseatk = atk;
		this.basedfs = dfs;
		this.basemna = mna;
		this.skill = skill;
		this.type = type;
	}
	
	//constructor overload for creating enemy characters (stats are randomized later)
	public PlayerCharacter() {
		
	}
	
	public void calculateSkillCD() {
		switch (this.skill) {
			case EXCALIBUR:
				this.skillCD = 5;
				break;
			case AVALON:
				this.skillCD = 5;
				break;
			case AVALON_PROTO:
				this.skillCD = 7;
				break;
			case CURSED_ONSET:
				this.skillCD = 6;
				break;
			case CURSED_ONSET_PROTO:
				this.skillCD = 9;
				break;
			case CAMELOT_WALL:
				this.skillCD = 3;
				break;
			case RHONGOMYNIAD:
				this.skillCD = 5;
				break;
			case RHONGOMYNIAD_CHARGE:
				this.skillCD = 6;
				break;
			case HOLY_HAND_GRENADE:
				this.skillCD = 5;
				break;
			case UNWAVERING_LOYALTY:
				this.skillCD = 2;
				break;
			case LONGINUS_ZERO:
				this.skillCD = 6;
				break;
			case GALATINE:
				this.skillCD = 8;
				break;
			case FAILNAUGHT:
				this.skillCD = 6;
				break;
			case IRA_LUPUS:
				this.skillCD = 7;
				break;
			case RABBIT:
				this.skillCD = 10;
				break;
				
			case CLARENT:
				this.skillCD = 3;
				break;
			case HP_RESTORE:
				this.skillCD = 3;
				break;
			case OFFENSE_UP:
				this.skillCD = 2;
				break;
			case DEFENSE_UP:
				this.skillCD = 2;
				break;
			case ARROW_RAIN:
				this.skillCD = 4;
				break;
			case INVINCIBILITY_TEMP:
				this.skillCD = 4;
				break;
		
			default:
				break;
		}
	}
	
	public double calculateDMG(PlayerCharacter dealer) {
		
		double multiplier = 1.00;
		
		if (this.type == Classes.SHIELDER) return 0.70;
		if (this.type == Classes.SOVEREIGN) return 0.50;
		
		if (this.type == Classes.LANCER) {
			switch (dealer.type) {
				case SABER:
					multiplier = 1.50;
					break;
				case ARCHER:
					multiplier = 0.50;
					break;
				case CASTER:
					multiplier = 0.75;
					break;
				default:
					break;
			}
		} else if (this.type == Classes.SABER) {
			switch (dealer.type) {
				case ARCHER:
					multiplier = 1.50;
					break;
				case CASTER:
					multiplier = 1.25;
					break;
				case LANCER:
					multiplier = 0.50;
					break;
				default:
					break;
			}
		} else if (this.type == Classes.ARCHER) {
			switch (dealer.type) {
				case LANCER:
					multiplier = 1.50;
					break;
				case SABER:
					multiplier = 0.50;
					break;
				default:
					break;
			}
		} else if (this.type == Classes.CASTER) {
			switch (dealer.type) {
				case CAVALRY:
					multiplier = 1.50;
					break;
				case LANCER:
					multiplier = 1.25;
					break;
				case ASSASSIN:
					multiplier = 0.50;
					break;
				default:
					break;
			}
		} else if (this.type == Classes.CAVALRY) {
			switch (dealer.type) {
				case ASSASSIN: 
					multiplier = 1.75;
					break;
				case CASTER:
					multiplier = 1.50;
					break;
				default:
					multiplier = 0.75;
					break;
			}
		} else if (this.type == Classes.ASSASSIN) {
			switch (dealer.type) {
				case CASTER:
					multiplier = 1.75;
					break;
				default:
					multiplier = 0.50;
					break;
			}
		}
		
		return multiplier;
	}
	
	public void skill(ArrayList<PlayerCharacter> aliveAllies) { // enemies to use skill
		System.out.println("=== "+this.name+" uses " + this.skill + "!");
		
		switch (this.skill) {
			case CLARENT:
				for (int i=0; i<aliveAllies.size(); i++) { // AoE dmg 
					if (!aliveAllies.get(i).isInvincible) {
						int dmg = (int) (this.atk * (double) (1.00-(Double.valueOf(aliveAllies.get(i).dfs) /100.00)) * ( (double) aliveAllies.get(i).calculateDMG(this)));
						aliveAllies.get(i).hp -= dmg;
						System.out.println("=== "+aliveAllies.get(i).name+" is dealt "+dmg+" damage!");
					}
				}
				break;
			
			case ARROW_RAIN:
				for (int i=0; i<aliveAllies.size(); i++) { // same skill, but different classes
					if (!aliveAllies.get(i).isInvincible) {
						int dmg = (int) (this.atk * (double) (1.00-(Double.valueOf(aliveAllies.get(i).dfs) /100.00)) * ( (double) aliveAllies.get(i).calculateDMG(this)));
						aliveAllies.get(i).hp -= dmg;
						System.out.println("=== "+aliveAllies.get(i).name+" is dealt "+dmg+" damage!");
					}
				}
				break;
			
			case OFFENSE_UP:
				this.atk += 15;
				break;
				
			case DEFENSE_UP:
				this.dfs += 15;
				break;
				
			case HP_RESTORE:
				this.hp += 250;
				break;
			
			case INVINCIBILITY_TEMP: // sets temporary invincibility
				this.isInvincible = true;
				this.invicibilityDuration = 2;
				break;
				
			default:
				break;
		}
	}
	
	public void attack(ArrayList<PlayerCharacter> aliveAllies) { // for enemies to attack allies
		int rand = GameLevel.Rand.nextInt(0, aliveAllies.size());
		
		PlayerCharacter target = aliveAllies.get(rand);
		
		int dmg = (int) (this.atk * (double) (1.00-(Double.valueOf(target.dfs) /100.00)) * ( (double) target.calculateDMG(this)));
		
		target.hp -= dmg;
		System.out.println("=== "+target.name+" is dealt "+dmg+" damage!");
	}
}


/*
 
 flowchart:
 
 same layer variables or functions w/ no relations are just | to indicate that they belong to the class
 with the top variable/enum having the v to represent direct hiearcy
 
  +------------------+
  | PlayerCharacter  |
  +------------------+
          |
          v
  +------------------+
  |  enum Skills     |
  +------------------+
          |
          |
  +------------------+
  |  enum Classes    |
  +------------------+
          |
          |
  +------------------+
  |  name, level, hp, atk, dfs, mna, skill, type |
  +------------------+
          |
          |
  +------------------+
  | PlayerCharacter()|
  +------------------+
          |
		  v
  +------------------+
  | initialize variables
  +------------------+
		|
		|
  +------------------+
  |     calculateSkillCD() |
  +------------------+
		|
		|
  +------------------+
  | calculateDMG()   |
  +------------------+
        |
        |  
  +------------------+
  |      skill()      |
  +------------------+
        |
        |
  +------------------+
  |     attack()     |
  +------------------+

  */
