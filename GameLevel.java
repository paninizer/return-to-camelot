package unit_16_to_18;

import java.util.*;
import unit_16_to_18.PlayerCharacter.Classes;
import unit_16_to_18.PlayerCharacter.Skills;

public class GameLevel {
	
	static Random Rand = new Random();
	
	enum Environment {
		MARSH,
		FOREST,
		ALPINE,
		URBAN,
		FIELD,
		CAMELOT
	}
	
	PlayerCharacter[] enemies;
	Environment environment;
	String name;
	boolean isFinished;
	ArrayList<PlayerCharacter> aliveEnemiesChoices = new ArrayList<>();
	ArrayList<PlayerCharacter> aliveAlliesChoices = new ArrayList<>();
	int round = 1;
	
	public GameLevel(String name, int world, boolean isFinal) throws Exception { // normal generation constructor
		this.name = name;
		// randomizing enemies
		if (!isFinal) { // if not final level
			this.enemies = new PlayerCharacter[4 + world * 2]; // number depends on world number
			for (int i=0; i<this.enemies.length; i++) { // initialize
				PlayerCharacter enemy = new PlayerCharacter();

				final int classSelection = Rand.nextInt(0, 6);
				final double multiplier = 1.0 * world + 1;
				//handle selections
				this.handleSelection(enemy, classSelection, multiplier);
				
				//System.out.println(enemy.type);
				
				this.enemies[i] = enemy;
			}
		} else {
			this.enemies = new PlayerCharacter[1]; // final boss (Mordred)
			
			this.enemies[0] = new PlayerCharacter("Mordred", 1500, 1500, 1500, 1500, Skills.CLARENT, Classes.SOVEREIGN);
			
			//System.out.println("breakpoint reached");
		}
		
		
		if (world!=9) { //if not final world
			Environment env = null;
			final int envSelection = Rand.nextInt(0, 5);
			
			this.handleSelection(env, envSelection);
			this.environment = env;
		} else this.environment = Environment.CAMELOT; // set environment manually (final stages)
		
	}
	
	private void handleSelection(PlayerCharacter enemy, int selection, double multiplier) throws Exception { // func to generate enemies
		// TODO Auto-generated method stub
			switch (selection) { // switch the cases
				case 0 : 
					enemy.name = "Swordsman";
					enemy.type = Classes.SABER;
					enemy.hp = (int) (multiplier * 200);
					enemy.atk = (int) (multiplier * 10);
					enemy.dfs = enemy.atk;
					enemy.mna = (int) (multiplier * 15);
					enemy.skill = Skills.OFFENSE_UP;
					break;
				case 1 :
					enemy.name = "Archer";
					enemy.type = Classes.ARCHER;
					enemy.hp = (int) (multiplier * 150);
					enemy.atk = (int) (multiplier * 15);
					enemy.dfs = enemy.atk;
					enemy.mna = (int) (multiplier * 10);
					enemy.skill = Skills.ARROW_RAIN;					
					break;
				case 2 :
					enemy.name = "Lancer";
					enemy.type = Classes.LANCER;
					enemy.hp = (int) (multiplier * 200);
					enemy.atk = (int) (multiplier * 10);
					enemy.dfs = enemy.atk;
					enemy.mna = (int) (multiplier * 15);
					enemy.skill = Skills.DEFENSE_UP;						
					break;
				case 3 :
					enemy.name = "Mage";
					enemy.type = Classes.CASTER;
					enemy.hp = (int) (multiplier * 150);
					enemy.atk = (int) (multiplier * 13);
					enemy.dfs = enemy.atk;
					enemy.mna = (int) (multiplier * 75);
					enemy.skill = Skills.HP_RESTORE;	
					break;
				case 4 : 
					enemy.name = "Rogue";
					enemy.type = Classes.ASSASSIN;
					enemy.hp = (int) (multiplier * 50);
					enemy.atk = (int) (multiplier * 15);
					enemy.dfs = (int) (multiplier * 5);
					enemy.mna = (int) (multiplier * 25);
					enemy.skill = Skills.INVINCIBILITY_TEMP;	
					break;
				case 5 :
					enemy.name = "Knight";
					enemy.type = Classes.CAVALRY;
					enemy.hp = (int) (multiplier * 225);
					enemy.atk = (int) (multiplier * 12);
					enemy.dfs = enemy.atk;
					enemy.mna = (int) (multiplier * 15);
					enemy.skill = Skills.OFFENSE_UP;	
					break;
				default :
					throw new Exception("Invalid Params for value: selection."); // throw error (since this shouldn't happen)
			}
			enemy.calculateSkillCD(); // calculate the skill cooldown depending on skill
			return;
	}
	
	private void handleSelection(Environment env, int selection) { // overload for environment
		switch (selection) { // switch case
			case 0 :
				env = Environment.ALPINE;
				break;
			case 1 : 
				env = Environment.FIELD;
				break;
			case 2 :
				env = Environment.MARSH;
				break;
			case 3 :
				env = Environment.FOREST;
				break;
			case 4 :
				env = Environment.URBAN;
				break;
			default :
				env = Environment.CAMELOT;
				break;
		}
	}
	
	private void handleSelection(PlayerConfig player) throws Exception { // overload for player handling
		System.out.print("Choose a character (0-"+ (this.aliveAlliesChoices.size()-1) + "): ");
		//String input = MainGame.br.readLine(); //calls on pre-existing reader to save memory
		
		boolean isValidInput = false;
		int selector = 0; // scope level declaration
		while (!isValidInput) {
			
			String input = MainGame.br.readLine(); // read input
			
			if (!MainGame.isInteger(input)) { // if not int
				System.out.println();
				System.out.println("==========EXCEPTION=========");
				System.out.println("|| Input must be a number!");
				System.out.println("==========EXCEPTION=========");
				System.out.println();
				System.out.print("Choose a character (0-"+ (this.aliveAlliesChoices.size()-1) + "): ");
				continue;
			}
			selector = Integer.parseInt(input);
			
			if (selector<0 || selector>(this.aliveAlliesChoices.size()-1)) { // if not valid
				System.out.println();
				System.out.println("==========EXCEPTION=========");
				System.out.println("|| Input must be at least 0 and no more than "+ (this.aliveAlliesChoices.size()-1));
				System.out.println("==========EXCEPTION=========");
				System.out.println();
				System.out.print("Choose a character (0-"+ (this.aliveAlliesChoices.size()-1) + "): ");		
			} else isValidInput = true;
		}
		
		PlayerCharacter character = this.aliveAlliesChoices.get(selector); // prop for func
		
		ArrayList<String> actionsList = new ArrayList<>();
		
		actionsList.add("attack");
		
		if (character.dfs > 0) {
			actionsList.add("defend");
		}
		if (character.mna > 0) {
			actionsList.add("mana attack");
		}
		if (!character.isSkillRcg) {
			actionsList.add("skill");
		}
		
		isValidInput = false;
		while (!isValidInput) {
			System.out.println("Available actions: ");
			for (String action : actionsList) {
				System.out.println("| -- " + action);
			}
			
			System.out.println();
			System.out.print("Select an action: ");
		
			String input = MainGame.br.readLine(); // read input
			
			for (int i=0; i<actionsList.size(); i++) {
				if (actionsList.get(i).equals(input.toLowerCase())) {
					selector = i;
					isValidInput = true;
					break;
				}
			}
			
			if (!isValidInput) System.out.println("Invalid input, please make sure your input matches one of the options and try again.");
		}
		
		this.handleSelection(actionsList.get(selector), character);      

	}
	
	private void handleSelection(String selector, PlayerCharacter character) throws Exception {
		
		switch (selector) { // limited options so switch case can be used
			case "attack":
				this.attack(character); // pass in prop
				break;
		
			case "defend":
				this.defend(character);
				break;
			
			case "skill":
				this.skill(character);
				break;
				
			case "mana attack":
				this.manaAtk(character);
				break;
				
			default:
				break;
		}
		
		this.calculateSkillRcg(); // calculate skill recharge of all alive characters
		
	}
	
	private void attack(PlayerCharacter character) throws Exception {
		
		this.printEnemies();
		
		System.out.print("Choose a target (0-"+ (this.aliveEnemiesChoices.size()-1) + "): ");
		//String input = MainGame.br.readLine(); // calls on pre-existing reader to save memory
		
		boolean isValidInput = false;
		int selector = 0; // scope level declaration
		while (!isValidInput) {
			
			String input = MainGame.br.readLine(); // read input
			
			if (!MainGame.isInteger(input)) { // if not int
				System.out.println();
				System.out.println("==========EXCEPTION=========");
				System.out.println("|| Input must be a number!");
				System.out.println("==========EXCEPTION=========");
				System.out.println();
				System.out.print("Choose a character (0-"+ (this.aliveEnemiesChoices.size()-1) + "): ");
				continue;
			}
			selector = Integer.parseInt(input);
			
			if (selector<0 || selector>(this.aliveEnemiesChoices.size()-1)) { // if not valid
				System.out.println();
				System.out.println("==========EXCEPTION=========");
				System.out.println("|| Input must be at least 0 and no more than "+ (this.aliveEnemiesChoices.size()-1));
				System.out.println("==========EXCEPTION=========");
				System.out.println();
				System.out.print("Choose a character (0-"+ (this.aliveEnemiesChoices.size()-1) + "): ");		
			} else isValidInput = true;
		}
		
		PlayerCharacter target = this.aliveEnemiesChoices.get(selector);
		
		int dmg = (int) (character.atk * (double) (1.00-(Double.valueOf(target.dfs) /100.00)) * ( (double) target.calculateDMG(character)));
		
		target.hp -= dmg;
		System.out.println("=== "+target.name+" is dealt "+dmg+" damage!");
		
		if (target.hp < 0 && !target.isInvincible) { // if enemy is defeated
			this.aliveEnemiesChoices.remove(selector);
			System.out.println("=== "+target.name+" has been defeated!");
		}
	}
	
	private void defend(PlayerCharacter character) { // raise defense by random amount
		if (character.dfs < 50) { // if defense is smaller than 50%
			int raisedAmt = Rand.nextInt(1, 5);
			character.dfs += raisedAmt;
			System.out.println(character.name + "'s defense has been raised by " + raisedAmt + "!");
			return;
		}
		
		System.out.println(character.name + " cannot defend anymore!!! Defense stats cannot be raised!");
		
		if (character.mna < 500) {
			int raisedAmt = Rand.nextInt(1, 100);
			character.mna += raisedAmt;
			System.out.println(character.name + "'s mana has been raised by " + raisedAmt + "!");
			return;
		}
		
		System.out.println(character.name + " cannot regenerate mana anymore!!! Mana cannot be raised!");

	}
	
	private void skill(PlayerCharacter character) {
		double multiplier = 2.00 + (character.level / 10); // skill multiplier by level
		
		System.out.println(character.name+" uses " + character.skill + "!");
		
		switch (character.skill) {
			case EXCALIBUR:
				for (int i=0; i<this.aliveEnemiesChoices.size(); i++) { // base saber class
					PlayerCharacter enemy = this.aliveEnemiesChoices.get(i);
					if (!enemy.isInvincible) {
						int dmg = (int) (character.atk * multiplier * (double) (1.00-(Double.valueOf(enemy.dfs) /100.00)) * ( (double) enemy.calculateDMG(character)));
						
						enemy.hp -= dmg;
						
						System.out.println("=== "+enemy.name+" is dealt "+dmg+" damage!");
					}
				}
				break;
			case HOLY_HAND_GRENADE:
				for (int i=0; i<this.aliveEnemiesChoices.size(); i++) { // same skill different class
					PlayerCharacter enemy = this.aliveEnemiesChoices.get(i);
					if (!enemy.isInvincible) {
						int dmg = (int) (character.atk * multiplier * (double) (1.00-(Double.valueOf(enemy.dfs) /100.00)) * ( (double) enemy.calculateDMG(character)));
						
						enemy.hp -= dmg;
						
						System.out.println("=== "+enemy.name+" is dealt "+dmg+" damage!");
					}
				}
				break;
			case GALATINE:
				for (int i=0; i<this.aliveEnemiesChoices.size(); i++) { // AoE dmg, same as other classes
					PlayerCharacter enemy = this.aliveEnemiesChoices.get(i);
					if (!enemy.isInvincible) {
						int dmg = (int) (character.atk * multiplier * (double) (1.00-(Double.valueOf(enemy.dfs) /100.00)) * ( (double) enemy.calculateDMG(character)));
						
						enemy.hp -= dmg;
						
						System.out.println("=== "+enemy.name+" is dealt "+dmg+" damage!");
					}
				}
				
				// raise atk & dfs by 5*multiplier
				for (int i=0; i<this.aliveAlliesChoices.size(); i++) { // AoE dmg, same as other classes
					PlayerCharacter ally = this.aliveAlliesChoices.get(i);
					
					ally.atk += 5*multiplier;
					
					if (ally.dfs < 50) {
						ally.dfs += 5*multiplier;
					}
				}
				
				break;
		
			default:
				return;
		}
	}
	
	private void manaAtk(PlayerCharacter character) {
		
	}
	
	private void calculateSkillRcg() {
		for (int i=0; i<this.aliveAlliesChoices.size(); i++) {
			if (this.aliveAlliesChoices.get(i).skillCD > 0) { // skill
				this.aliveAlliesChoices.get(i).skillCD --;
				continue;
			}
			
			this.aliveAlliesChoices.get(i).isSkillRcg = false;
			this.aliveAlliesChoices.get(i).calculateSkillCD();
		}
		
		for (int i=0; i<this.aliveAlliesChoices.size(); i++) { // invincibility
			if (this.aliveAlliesChoices.get(i).invicibilityDuration > 0) {
				this.aliveAlliesChoices.get(i).invicibilityDuration--;
				continue;
			}
			
			this.aliveAlliesChoices.get(i).isInvincible = false;
		}
		
		for (int i=0; i<this.aliveEnemiesChoices.size(); i++) { // skill
			if (this.aliveEnemiesChoices.get(i).skillCD > 0) {
				this.aliveEnemiesChoices.get(i).skillCD --;
				continue;
			}
			
			this.aliveEnemiesChoices.get(i).isSkillRcg = false;
			this.aliveEnemiesChoices.get(i).calculateSkillCD();
		}
		
		for (int i=0; i<this.aliveEnemiesChoices.size(); i++) { // invincibility
			if (this.aliveEnemiesChoices.get(i).invicibilityDuration > 0) {
				this.aliveEnemiesChoices.get(i).invicibilityDuration--;
				continue;
			}
			
			this.aliveEnemiesChoices.get(i).isInvincible = false;
		}
	}
	
	private void enemyRound() {
		for (int i=0; i<this.aliveEnemiesChoices.size(); i++) {
			if (!this.aliveEnemiesChoices.get(i).isSkillRcg) {
				this.aliveEnemiesChoices.get(i).skill(this.aliveAlliesChoices);
			} else {
				this.aliveAlliesChoices.get(i).attack(this.aliveAlliesChoices);
			}
			
			this.ifFriendlyDead(); // check if any friendlies are dead
		}
	}
	
	private void ifFriendlyDead() {
		for (int i=0; i<this.aliveAlliesChoices.size(); i++) {
			if (this.aliveAlliesChoices.get(i).hp < 0 && !this.aliveAlliesChoices.get(i).isInvincible) {
				System.out.println("=== "+this.aliveAlliesChoices.get(i).name+" has been defeated!");
				this.aliveAlliesChoices.remove(i);
				this.ifFriendlyDead(); // call on itself again since size has been changed and can result in missed/out of bounds
			}
		}
	}
	
	public void startLevel(PlayerConfig player) throws Exception {
		while (!this.isFinished) { // if the enemies are not dead yet!
			this.roundStart(player);
			this.round++;
		}
	}
	
	private void roundStart(PlayerConfig player) throws Exception {
		System.out.println("Round " + this.round + ": \n\n");
		
		System.out.println("Enemies remaining: " + this.aliveEnemiesChoices.size());
		
		java.util.concurrent.TimeUnit.SECONDS.sleep(1);
		
		this.printEnemies();
		
		java.util.concurrent.TimeUnit.SECONDS.sleep(2);
		
		this.printAllies(player);
		
		for (int i=0; i<this.aliveAlliesChoices.size(); i++) {
			this.handleSelection(player);
		}
		
		this.enemyRound();
	}
	
	/* HELPER FUNCTIONS */
	private void printEnemies() throws Exception {
		this.aliveEnemiesChoices.clear();
		String leftAlignFormat = "| %-21s | %-12s | %-25s | %-20s |%n";
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		System.out.format("|      Characters       |    Class     |        Properties         |        Skill         |%n");
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		for (int i=0; i<this.enemies.length; i++) {
			if (this.enemies[i].hp>0 || this.enemies[i].isInvincible) {
				String property = "Atk="+this.enemies[i].atk+", Def%="+this.enemies[i].dfs+", Mna="+this.enemies[i].mna;
				System.out.format(leftAlignFormat, this.enemies[i].name, this.enemies[i].type, property, this.enemies[i].skill);
				System.out.format(leftAlignFormat, "", "", "Hitpoints: "+this.enemies[i].hp, "Is recharging: "+this.enemies[i].isSkillRcg);
				System.out.format(leftAlignFormat, "", "", "Is invincible: "+this.enemies[i].isInvincible, "Skill CD: "+this.enemies[i].skillCD);
				System.out.format(leftAlignFormat, "", "", "", "");
				this.aliveEnemiesChoices.add(this.enemies[i]);
			}
		}
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		System.out.println();
	}
	
	private void printAllies(PlayerConfig player) throws Exception {
		this.aliveAlliesChoices.clear();
		String leftAlignFormat = "| %-21s | %-12s | %-25s | %-20s |%n";
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		System.out.format("|      Characters       |    Class     |        Properties         |        Skill         |%n");
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		for (int i=0; i<player.characters.length; i++) {
			if (player.characters[i].hp>0 || player.characters[i].isInvincible) {
				String property = "Atk="+player.characters[i].atk+", Def%="+player.characters[i].dfs+", Mna="+player.characters[i].mna;
				System.out.format(leftAlignFormat, player.characters[i].name, player.characters[i].type, property, player.characters[i].skill);
				System.out.format(leftAlignFormat, "", "", "Hitpoints: "+player.characters[i].hp, "Is recharging: "+player.characters[i].isSkillRcg);
				System.out.format(leftAlignFormat, "", "", "Is invincible: "+player.characters[i].isInvincible, "Skill CD: "+player.characters[i].skillCD);
				System.out.format(leftAlignFormat, "", "", "", "");
				this.aliveAlliesChoices.add(player.characters[i]);
			}
		}
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		System.out.println();
	}

	public GameLevel() { // overload initializing first save
		this.name = "Init";
	}
}
