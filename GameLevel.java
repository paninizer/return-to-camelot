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
	boolean isFinished = false;
	ArrayList<PlayerCharacter> aliveEnemiesChoices = new ArrayList<>();
	ArrayList<PlayerCharacter> aliveAlliesArray = new ArrayList<>();
	ArrayList<PlayerCharacter> availableAlliesChoices = new ArrayList<>();
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
				
				enemy.level = world; //display purposes
				//handle selections
				this.handleSelection(enemy, classSelection, multiplier);
				
				//System.out.println(enemy.type);
				
				this.enemies[i] = enemy;
			}
		} else {
			this.enemies = new PlayerCharacter[1]; // final boss (Mordred)
			
			this.enemies[0] = new PlayerCharacter("Mordred", 10, 15000, 150, 50, 1500, Skills.CLARENT, Classes.SOVEREIGN);
			
			//System.out.println("breakpoint reached");
		}
		
		
		if (world!=9) { //if not final world
			int envSelection = Rand.nextInt(0, 5);
			this.environment = this.handleSelection(envSelection);
		} else this.environment = Environment.CAMELOT; // set environment manually (final stages)
		
	}
	
	private void handleSelection(PlayerCharacter enemy, int selection, double multiplier) throws Exception { // func to generate enemies
		// TODO Auto-generated method stub
			switch (selection) { // switch the cases
				case 0 : 
					enemy.name = "Swordsman";
					enemy.type = Classes.SABER;
					enemy.maxhp = (int) (multiplier * 200);
					enemy.hp = enemy.maxhp;
					enemy.atk = (int) (multiplier * 10);
					enemy.dfs = enemy.atk;
					enemy.mna = (int) (multiplier * 15);
					enemy.skill = Skills.OFFENSE_UP;
					break;
				case 1 :
					enemy.name = "Archer";
					enemy.type = Classes.ARCHER;
					enemy.maxhp = (int) (multiplier * 150);
					enemy.hp = enemy.maxhp;
					enemy.atk = (int) (multiplier * 15);
					enemy.dfs = enemy.atk;
					enemy.mna = (int) (multiplier * 10);
					enemy.skill = Skills.ARROW_RAIN;					
					break;
				case 2 :
					enemy.name = "Lancer";
					enemy.type = Classes.LANCER;
					enemy.maxhp = (int) (multiplier * 200);
					enemy.hp = enemy.maxhp;
					enemy.atk = (int) (multiplier * 10);
					enemy.dfs = enemy.atk;
					enemy.mna = (int) (multiplier * 15);
					enemy.skill = Skills.DEFENSE_UP;						
					break;
				case 3 :
					enemy.name = "Mage";
					enemy.type = Classes.CASTER;
					enemy.maxhp = (int) (multiplier * 150);
					enemy.hp = enemy.maxhp;
					enemy.atk = (int) (multiplier * 13);
					enemy.dfs = enemy.atk;
					enemy.mna = (int) (multiplier * 75);
					enemy.skill = Skills.HP_RESTORE;	
					break;
				case 4 : 
					enemy.name = "Rogue";
					enemy.type = Classes.ASSASSIN;
					enemy.maxhp = (int) (multiplier * 50);
					enemy.hp = enemy.maxhp;
					enemy.atk = (int) (multiplier * 15);
					enemy.dfs = (int) (multiplier * 5);
					enemy.mna = (int) (multiplier * 25);
					enemy.skill = Skills.INVINCIBILITY_TEMP;	
					break;
				case 5 :
					enemy.name = "Knight";
					enemy.type = Classes.CAVALRY;
					enemy.maxhp = (int) (multiplier * 225);
					enemy.hp = enemy.maxhp;
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
	
	private Environment handleSelection(int selection) { // overload for environment
		
		Environment env = null;
		
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
		return env;
	}
	
	private void handleSelection(PlayerConfig player) throws Exception { // overload for player handling
		System.out.println("Available Choices: ");
		this.printAllies();
		
		System.out.print("Choose a character (0-"+ (this.availableAlliesChoices.size()-1) + "): ");
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
				System.out.print("Choose a character (0-"+ (this.availableAlliesChoices.size()-1) + "): ");
				continue;
			}
			selector = Integer.parseInt(input);
			
			if (selector<0 || selector>(this.aliveAlliesArray.size()-1)) { // if not valid
				System.out.println();
				System.out.println("==========EXCEPTION=========");
				System.out.println("|| Input must be at least 0 and no more than "+ (this.aliveAlliesArray.size()-1));
				System.out.println("==========EXCEPTION=========");
				System.out.println();
				System.out.print("Choose a character (0-"+ (this.availableAlliesChoices.size()-1) + "): ");
			} else isValidInput = true;
		}
		
		PlayerCharacter character = this.availableAlliesChoices.get(selector); // prop for func
		
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
		int actionSelect = 0;
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
					actionSelect = i;
					isValidInput = true;
					break;
				}
			}
			
			if (!isValidInput) System.out.println("Invalid input, please make sure your input matches one of the options and try again.");
		}
		
		character.isAvailable = false;
		this.availableAlliesChoices.remove(selector);
		//System.out.println(character.isAvailable); debug
		
		this.handleSelection(actionsList.get(actionSelect), character);      

	}
	
	private void handleSelection(String selector, PlayerCharacter character) throws Exception { // handle selection of actions
		
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
		
	}
	
	private void attack(PlayerCharacter character) throws Exception { // attack the enemy
		
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
		
		int dmg = (int) (character.atk * (double) (1.00-(Double.valueOf(target.dfs) / 100.00)) * ( (double) target.calculateDMG(character)));
		
		if (!target.isInvincible) target.hp -= dmg;
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
	
	private void skill(PlayerCharacter character) throws Exception { // ally character skill
		double multiplier = 2.00 + (character.level / 10); // skill multiplier by level
		
		System.out.println(character.name+" uses " + character.skill + "!");
		
		switch (character.skill) {
		
		//affects all allies/enemies cases
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
			case RHONGOMYNIAD_CHARGE:
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
				for (int i=0; i<this.aliveEnemiesChoices.size(); i++) { // AoE dmg, same as arthur
					PlayerCharacter enemy = this.aliveEnemiesChoices.get(i);
					if (!enemy.isInvincible) {
						int dmg = (int) (character.atk * multiplier * (double) (1.00-(Double.valueOf(enemy.dfs) /100.00)) * ( (double) enemy.calculateDMG(character)));
						
						enemy.hp -= dmg;
						
						System.out.println("=== "+enemy.name+" is dealt "+dmg+" damage!");
					}
				}
				
				// raise atk & dfs by 5*multiplier
				for (int i=0; i<this.aliveAlliesArray.size(); i++) { 
					PlayerCharacter ally = this.aliveAlliesArray.get(i);
					
					ally.atk += 5*multiplier;
					
					if (ally.dfs < 50) {
						ally.dfs += 5*multiplier;
					}
				}
				break;
			case UNWAVERING_LOYALTY:
				// raise atk & dfs by 15*multiplier
				for (int i=0; i<this.aliveAlliesArray.size(); i++) { 
					PlayerCharacter ally = this.aliveAlliesArray.get(i);
					
					ally.atk += 15*multiplier;
					
					if (ally.dfs < 50) {
						ally.dfs += 15*multiplier;
					}
				}
				break;
			case CAMELOT_WALL:
				for (int i=0; i<this.aliveAlliesArray.size(); i++) { 
					PlayerCharacter ally = this.aliveAlliesArray.get(i);
					
					ally.isInvincible = true;
					ally.invicibilityDuration = 2;
					
					if (ally.dfs < 85) {
						ally.dfs += 20*multiplier;
					}
				}
				break;
			case AVALON:
				for (int i=0; i<this.aliveAlliesArray.size(); i++) { 
					PlayerCharacter ally = this.aliveAlliesArray.get(i);
					
					ally.isInvincible = true;
					ally.invicibilityDuration = 1;
					
					if (ally.hp < ally.maxhp) {
						ally.hp = ally.maxhp;
					}
				}
				break;
			case AVALON_PROTO:
				for (int i=0; i<this.aliveAlliesArray.size(); i++) { 
					PlayerCharacter ally = this.aliveAlliesArray.get(i);
					
					ally.isInvincible = true;
					ally.invicibilityDuration = 1;
					
					if (ally.hp > 25) {
						ally.hp -= 15;
						ally.atk += 15 * multiplier;
					}
				}
				break;
				
			// affects only 1 ally / enemy skill cases
			case RHONGOMYNIAD:

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
				
				int dmg = (int) (character.atk * 4 * multiplier * ( (double) target.calculateDMG(character)));
				
				target.hp -= dmg;
				System.out.println("=== "+target.name+" is dealt "+dmg+" damage!");
				
				if (target.hp < 0) { // if enemy is defeated (ignores invincibility)
					this.aliveEnemiesChoices.remove(selector);
					System.out.println("=== "+target.name+" has been defeated!");
				}
				break;
			case LONGINUS_ZERO:
				this.printEnemies();
				
				System.out.print("Choose a target (0-"+ (this.aliveEnemiesChoices.size()-1) + "): ");
				//String input = MainGame.br.readLine(); // calls on pre-existing reader to save memory
				
				isValidInput = false;
				selector = 0; // scope level declaration
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
				
				target = this.aliveEnemiesChoices.get(selector);
				
				dmg = (int) (character.atk * 6 * multiplier * (double) (1.00-(Double.valueOf(target.dfs) / 1000.00)) * ( (double) target.calculateDMG(character)));
				
				if (!target.isInvincible) target.hp -= dmg;
				else System.out.println("Target is invincible! The skill deals effectively no damage!");
				
				System.out.println("=== "+target.name+" is dealt "+dmg+" damage!");
				
				if (target.hp < 0 && !target.isInvincible) { // if enemy is defeated
					this.aliveEnemiesChoices.remove(selector);
					System.out.println("=== "+target.name+" has been defeated!");
				}
				
				character.hp += (int) (dmg / 25);
				break;
			case IRA_LUPUS:
				this.printEnemies();
				
				System.out.print("Choose a target (0-"+ (this.aliveEnemiesChoices.size()-1) + "): ");
				//String input = MainGame.br.readLine(); // calls on pre-existing reader to save memory
				
				isValidInput = false;
				selector = 0; // scope level declaration
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
				
				target = this.aliveEnemiesChoices.get(selector);
				
				dmg = (int) (character.atk * 6 * multiplier * (double) (1.00-(Double.valueOf(target.dfs) / 1000.00)) * ( (double) target.calculateDMG(character)));
				
				if (!target.isInvincible) target.hp -= dmg;
				else System.out.println("Target is invincible! The skill deals effectively no damage!");
				System.out.println("=== "+target.name+" is dealt "+dmg+" damage!");
				
				if (target.hp < 0 && !target.isInvincible) { // if enemy is defeated
					this.aliveEnemiesChoices.remove(selector);
					System.out.println("=== "+target.name+" has been defeated!");
				}
				
				character.atk += (int) (dmg / 25);
				break;
			case FAILNAUGHT:
				this.printEnemies();
				
				System.out.print("Choose a target (0-"+ (this.aliveEnemiesChoices.size()-1) + "): ");
				//String input = MainGame.br.readLine(); // calls on pre-existing reader to save memory
				
				isValidInput = false;
				selector = 0; // scope level declaration
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
				
				target = this.aliveEnemiesChoices.get(selector);
				
				dmg = (int) (character.atk * 6 * multiplier * ( (double) target.calculateDMG(character)));
				
				target.hp -= dmg;
				System.out.println("=== "+target.name+" is dealt "+dmg+" damage!");
				
				if (target.hp < 0) { // if enemy is defeated (ignores invincibility)
					this.aliveEnemiesChoices.remove(selector);
					System.out.println("=== "+target.name+" has been defeated!");
					break;
				}
				
				this.aliveEnemiesChoices.get(selector).dfs -= (int) (dmg / 25);
				break;
			default:
				return;
		}
	}
	
	private void manaAtk(PlayerCharacter character) throws Exception { // mana attack
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
		
		if (character.mna < 100) System.out.print("Enter a value of mana used (max "+ character.mna + "): ");
		else System.out.print("Enter a value of mana used (max 100): ");
		//String input = MainGame.br.readLine(); // calls on pre-existing reader to save memory
		
		isValidInput = false;
		int manause = 0; // scope level declaration
		while (!isValidInput) {
			
			String input = MainGame.br.readLine(); // read input
			
			if (!MainGame.isInteger(input)) { // if not int
				System.out.println();
				System.out.println("==========EXCEPTION=========");
				System.out.println("|| Input must be a number!");
				System.out.println("==========EXCEPTION=========");
				System.out.println();
				System.out.print("Enter a value of mana used (max 100): ");
				continue;
			}
			manause = Integer.parseInt(input);
			
			if (manause < 0 || manause > 100) { // if not valid
				System.out.println();
				System.out.println("==========EXCEPTION=========");
				System.out.println("|| Input must be at least 0 and no more than 100!");
				System.out.println("==========EXCEPTION=========");
				System.out.println();
				if (character.mna < 100) System.out.print("Enter a value of mana used (max "+ character.mna + "): ");
				else System.out.print("Enter a value of mana used (max 100): ");
				continue;
			} 
			
			if (character.mna - manause < 0) {
				System.out.println();
				System.out.println("==========EXCEPTION=========");
				System.out.println("|| Input must be no more than "+ character.mna);
				System.out.println("==========EXCEPTION=========");
				System.out.println();
				System.out.print("Enter a value of mana used (max "+ character.mna + "): ");
				continue;
			}
			
			isValidInput = true;
		}
		
		character.mna -= manause;
		int dmg = (int) (character.atk * (double) (1.00-(Double.valueOf(target.dfs) / 100.00 - manause / 100.00)) * ( (double) target.calculateDMG(character)));
		
		if (!target.isInvincible) target.hp -= dmg;
		System.out.println("=== "+target.name+" is dealt "+dmg+" damage!");
		
		if (target.hp < 0 && !target.isInvincible) { // if enemy is defeated
			this.aliveEnemiesChoices.remove(selector);
			System.out.println("=== "+target.name+" has been defeated!");
		}
	}
	
	private void calculateSkillRcg() { // calculate skill recharge of all characters after a round
		for (int i=0; i<this.aliveAlliesArray.size(); i++) {
			if (this.aliveAlliesArray.get(i).skillCD > 0) { // skill
				this.aliveAlliesArray.get(i).skillCD --;
				continue;
			}
			
			this.aliveAlliesArray.get(i).isSkillRcg = false;
			this.aliveAlliesArray.get(i).calculateSkillCD();
		}
		
		for (int i=0; i<this.aliveAlliesArray.size(); i++) { // invincibility
			if (this.aliveAlliesArray.get(i).invicibilityDuration > 0) {
				this.aliveAlliesArray.get(i).invicibilityDuration--;
				continue;
			}
			
			this.aliveAlliesArray.get(i).isInvincible = false;
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
	
	private void enemyRound() { // enemy's round
		for (int i=0; i<this.aliveEnemiesChoices.size(); i++) {
			if (!this.aliveEnemiesChoices.get(i).isSkillRcg) {
				this.aliveEnemiesChoices.get(i).skill(this.aliveAlliesArray);
			} else {
				this.aliveEnemiesChoices.get(i).attack(this.aliveAlliesArray);
			}
			
			this.ifFriendlyDead(); // check if any friendlies are dead
		}
	}
	
	private void ifFriendlyDead() { // recursive function to see which allies died
		for (int i=0; i<this.aliveAlliesArray.size(); i++) {
			if (this.aliveAlliesArray.get(i).hp < 0 && !this.aliveAlliesArray.get(i).isInvincible) {
				System.out.println("=== "+this.aliveAlliesArray.get(i).name+" has been defeated!");
				this.aliveAlliesArray.remove(i);
				this.ifFriendlyDead(); // call on itself again since size has been changed and can result in missed/out of bounds
			}
		}
	}
	
	public void startLevel(PlayerConfig player) throws Exception { // starts the level
		
		this.printDialogue(player.levelsCompleted);
		
		System.out.println("\n\n"+this.name+"\n\n --- Location: "+this.environment);
		System.out.println(" --- Enemies total: " + this.enemies.length);
		System.out.println("\n\n --- Enemies list: ");
		this.printEnemies();
		
		System.out.println("\n\nChoose your character setup: ");
		MainGame.chooseCharacters();
		
		this.reset(player);
		
		System.out.println("\n\nYour character setup: ");
		this.printAllies(player);
		
		while (!this.isFinished) { // if the enemies are not dead yet!
			this.roundStart(player);
			this.round++;
		}
		
		
	}

	private void roundStart(PlayerConfig player) throws Exception { // starts the current round
		
		for (int i=0; i<this.aliveAlliesArray.size(); i++) {
			this.aliveAlliesArray.get(i).isAvailable = true;
		}
		
		System.out.println("Round " + this.round + ": \n\n");
		
		for (int j=0; j<this.aliveAlliesArray.size(); j++) {
			
			java.util.concurrent.TimeUnit.SECONDS.sleep(1);
			System.out.print("Enemies remaining: ");
			System.out.println(this.aliveEnemiesChoices.size());
			this.printEnemies();
			
			java.util.concurrent.TimeUnit.SECONDS.sleep(2);
			
			System.out.print("Allies remaining: ");
			System.out.println(this.aliveAlliesArray.size());
			this.printAllies(player);
			this.handleSelection(player); // player rounds
			
			if (this.aliveEnemiesChoices.size() == 0) {
				this.isFinished = true;
				player.levelsCompleted++;
				
				for (int i = 0; i < this.aliveAlliesArray.size(); i++) {
					PlayerCharacter playerChar =  this.aliveAlliesArray.get(i);
					System.out.println(playerChar.name + " gained 1 XP!");
				}
				
				if (player.levelsCompleted % 5 == 0) {
					for (int i = 0; i < this.aliveAlliesArray.size(); i++) {
						PlayerCharacter playerChar =  this.aliveAlliesArray.get(i);
						playerChar.level++;
						System.out.println(" === " + playerChar.name + " leveled up! Now they're Level " + playerChar.level);
						playerChar.atk += 10;
						playerChar.dfs += 2;
					}
				}
				return;
			}
		}
		
		this.enemyRound();
		
		if (this.aliveAlliesArray.size() == 0) {
			System.out.println("You failed to beat the level! You must have at least 1 character alive by the end!\n");

			boolean isValidInput = false;
			while (!isValidInput) {
				System.out.print("Do you want to restart the current level? "
						+ "\n If not, all your progress will be lost! Y/N: ");
				
				String input = MainGame.br.readLine().toUpperCase();
				if (input.equals("Y") || input.equals("YES")) {
					
					isValidInput = true;
					continue; // stops execution of further loop components, readability ++
				}
				if (input.equals("N") || input.equals("NO")) {
					System.exit(0); // exits program (stops any further program flow, erases everything)
				}
				
				System.out.println("Invalid input! Please input Yes (Y) or No (N)!\n");
			}
			
		}
		
		this.calculateSkillRcg(); // calculate skill recharge of all alive characters
	}
	
	private void reset(PlayerConfig player) { // resets character stats for level (restart/new level)
		for (int i=0; i<player.characters.length; i++) {
			PlayerCharacter character = player.characters[i];
			character.hp = character.maxhp;
			character.atk = character.baseatk;
			character.dfs = character.basedfs;
			character.mna = character.basemna;
			character.calculateSkillCD();
		}
	}
	
	/* HELPER FUNCTIONS */
	private void printEnemies() throws Exception { // print enemies and add them to active array
		this.aliveEnemiesChoices.clear();
		String leftAlignFormat = "| %-21s | %-12s | %-25s | %-20s |%n";
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		System.out.format("|      Characters       |    Class     |        Properties         |        Skill         |%n");
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		for (int i=0; i<this.enemies.length; i++) {
			if (this.enemies[i].hp>0 || this.enemies[i].isInvincible) {
				String property = "Atk="+this.enemies[i].atk+", Def%="+this.enemies[i].dfs+", Mna="+this.enemies[i].mna;
				System.out.format(leftAlignFormat, this.enemies[i].name, this.enemies[i].type, property, this.enemies[i].skill);
				System.out.format(leftAlignFormat, "", "Level " + this.enemies[i].level, "Hitpoints: "+ this.enemies[i].hp + "/"+ this.enemies[i].maxhp, "Is recharging: "+this.enemies[i].isSkillRcg);
				System.out.format(leftAlignFormat, "", "", "Is invincible: "+this.enemies[i].isInvincible, "Skill CD: "+this.enemies[i].skillCD);
				String invindura = "";
				if (this.enemies[i].isInvincible) {
					invindura = "Invicible for: " + this.enemies[i].invicibilityDuration;
				}
				System.out.format(leftAlignFormat, "", "", invindura, "");
				this.aliveEnemiesChoices.add(this.enemies[i]);
			}
		}
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		System.out.println();
	}
	
	private void printAllies(PlayerConfig player) throws Exception { // print allies, add them to alive array for display
		this.aliveAlliesArray.clear();
		String leftAlignFormat = "| %-21s | %-12s | %-25s | %-20s |%n";
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		System.out.format("|      Characters       |    Class     |        Properties         |        Skill         |%n");
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		for (int i=0; i<player.characters.length; i++) {
			PlayerCharacter character = player.characters[i];

			String property = "Atk="+character.atk+", Def%="+character.dfs+", Mna="+character.mna;
			System.out.format(leftAlignFormat, character.name, character.type, property, character.skill);
			System.out.format(leftAlignFormat, "", "Level " + character.level, "Hitpoints: "+ character.hp + "/"+ character.maxhp, "Is recharging: "+character.isSkillRcg);
			System.out.format(leftAlignFormat, "", "", "Is invincible: "+character.isInvincible, "Skill CD: "+character.skillCD);
			String invindura = "";
			if (character.isInvincible) {
				invindura = "Invicible for: " + character.invicibilityDuration;
			}
			System.out.format(leftAlignFormat, "", "", invindura, "");
			this.aliveAlliesArray.add(character);
		}
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		System.out.println();
	}
	
	private void printAllies() throws Exception { // print alies, add them to available to attack (each can only go once per round)
		//this.aliveAlliesChoices.clear();
		this.availableAlliesChoices.clear();
		String leftAlignFormat = "| %-21s | %-12s | %-25s | %-20s |%n";
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		System.out.format("|      Characters       |    Class     |        Properties         |        Skill         |%n");
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		for (int i=0; i<this.aliveAlliesArray.size(); i++) {
			PlayerCharacter character = this.aliveAlliesArray.get(i);
			if (character.isAvailable) {
				String property = "Atk="+character.atk+", Def%="+character.dfs+", Mna="+character.mna;
				System.out.format(leftAlignFormat, character.name, character.type, property, character.skill);
				System.out.format(leftAlignFormat, "", "Level " + character.level, "Hitpoints: "+ character.hp + "/"+ character.maxhp, "Is recharging: "+character.isSkillRcg);
				System.out.format(leftAlignFormat, "", "", "Is invincible: "+character.isInvincible, "Skill CD: "+character.skillCD);
				String invindura = "";
				if (character.isInvincible) {
					invindura = "Invicible for: " + character.invicibilityDuration;
				}
				System.out.format(leftAlignFormat, "", "", invindura, "");
				this.availableAlliesChoices.add(character);
			}

			//if (character.hp>0 || character.isInvincible) this.aliveAlliesChoices.add(character);
		}
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		System.out.println();
	}

	private void printDialogue(int id) throws Exception { // prints all dialogues needed!!!
		
		java.util.concurrent.TimeUnit.SECONDS.sleep(2);
		System.out.println("Cutscene");
		System.out.println("===================================================");
		switch (id) {
			case 0:				
				System.out.println("On a fateful day, when King Arthur had put down yet another rebellion against his country, he started to head back to Camelot"
						+ ", for it has been a long time since he returned to his beloved city and wife.");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("Yet, there was something awry. The vultures circled, looking for prey, and there was nobody about on the once busy roads in Britain.\n");
				System.out.println("In the distance, a forest was burning. Shouts, curses, and cries can be heard everywhere.\n\n");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("Dead peasants lay everywhere, while others tried their best to defend their families from the assailants dressed in red. The bows twanged and many defenders fell.");
				System.out.println("The once peaceful land of Britain... It pains King Arthur to watch. He called on his generals and army, ready to put down the troublemakers.");
				break;
			
			case 1:
				System.out.println("After killing most of the insurgents, King Arthur noticed that they were sporting the banners of Camelot!");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("Many peasants also tried to fight back against King Arthur's army, despite them helping.\n");
				java.util.concurrent.TimeUnit.SECONDS.sleep(3);
				System.out.println("After King Arthur firmly restrained a peasant and explained that he was King Arthur, the peasant threw aside his sword and sobbed uncontrollably.");
				System.out.println("King Arthur questioned him about the incident, and he said that terrorists from Camelot have been pillaging the countryside, with orders from the \"King of Britain\"");
				System.out.println(", and that whoever tries to question the orders will be killed mercilessly.");
				System.out.println("Another villager, bloodied in the fight, thrusted his sword into the ground: \"We did not believe that you will order such a thing, Your Majesty, so we reasoned with the brutes and then...\"");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("After leaving a portion of his army to assist the peasants and anybody else that is fleeing from the fake king's army, King Arthur proceeded on, towards Camelot, to regain control");
				break;
			
			case 9:
				System.out.println("The enemies got more skilled as they progressed onwards, and King Arthur already lost many of his soldiers.");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("They came upon many burned out villages, with most of the inhabitants dead or hiding, the inhabitants fought on until the very bitter end.");
				System.out.println("Merlin said a few words of prayer for the dead, and everyone proceeded with ever-wearying journey...");
				break;
				
			case 29:
				System.out.println("From what few words and documents they could gather, it seemed like after King Arthur set out to eradicate the rebellion, there was a commotion within Camelot, although what it was is not certain.");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("The exact number dead cannot be determined, but it was a bloody battle. A red fire stretching to heaven can be seen often, striking the plains and screams, oh the screams.");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("Upon hearing this, King Arthur's heart tightened. It was his son, Mordred's, ability to emit a red flame that can melt entire armies.");
				System.out.println("Gawain stayed silent, observing his King's thoughts. But he spoke eventually: \"The rebellion was just a distraction so they could take Camelot...\"");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("King Arthur replied: \"Then we must get back as soon as possible!\"");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("\"Our priority is to help the people! It was what you upheld as the true principle of the Round Table!\"");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("\"Very well, we proceed onwards to the next city!\" King Arthur cleared his mind.");
				break;
				
			case 49:
				System.out.println("More and more deaths, it has blended into everyone's thoughts. Death and marauders. King Arthur stood, his hair flowing like a lion, staring at the sunset.");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("By diverting much of his army, King Arthur has saved countless citizens. Yet the terrorists kept coming, in countless waves. To defeat them, King Arthur needs to gather his army and drive to Camelot");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("He turned amid the dying sun, his shadows stooping under his responsibilites...");
				break;
				
			case 69:
				System.out.println("By reducing the fake king's army of terrorists, King Arthur's army has thinned its ranks, and now more grim than ever.");
				System.out.println("King Arthur, after riding ahead for a while, spotted another large army of terrorists, yet they used a different banner this time, a banner of blood red, white, and black");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("The bows twanged, the swords clashed, and spells flew through the ranks like birds. The fight was on!");
				break;
				
			case 79:
				System.out.println("The enemy army has been defeated. Its general surrendered amidst spears, swords, and bows cut down all of his men.");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("He was brought to King Arthur, who sternly questioned: \"Who art thy master? Who commanded thee to kill my countrymen?\"");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("The general grinned as he said menacingly: \"King Mordred!\"");
				System.out.println("Everyone was taken aback. How could Mordred, Arthur's son, commit such an atrocity? But nevertheless, if it is Mordred, he must be punished, thought King Arthur.");
				break;
				
			case 89:
				System.out.println("They have broken through fierce resistance and now are within sight of the city walls. The closer they got, the worse the landscape was destroyed by Mordred's army.");
				System.out.println("Dead bodies lined the roads, and in front of the gate to the city, Mordred has used his powers to incinerate those loyal to Arthur. Charred bodies, armour, swords, and spears lay strewn on the fields, which themselves were burning.");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("The gate opened.");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("King Arthur saw a giant army rolling out into the field of Camlaan, and its head rode a knight in white, black, and blood red armour wielding a blood red sword: Mordred.");
				System.out.println("King Arthur unsheathed his sword and challenged Mordred, who laughs mercilessly and ordered his army to advance.");
				System.out.println("There will be no choice but to fight to the end, and King Arthur's knights and army are ready. They readied their weapons, despite the massive number of enemies arranged before them");
				break;
				
			case 98:
				System.out.println("Despite terrible odds, King Arthur's soldiers have managed to defeat much of Mordred's army before falling.");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("Although King Arthur fought hard, he was not able to stop Mordred using his powers to incinerate both his and Arthur's remaining armies."
						+ "\nBodies piled to form hills, spears, swords, and arrows lay embedded within King Arthur's valiant warriors.");
				System.out.println("The final battle has come, and King Arthur knew it will be the last.");
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
				break;
				
			default:
				break;
		}
		System.out.println("===================================================");
	}
	

	public GameLevel() { // overload initializing first save
		this.name = "Init";
	}
}

/*
 * flowchart: too much
 * 
 * @param
 * 
 * 
 * */
