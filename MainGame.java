package unit_16_to_18;

import java.util.*;

import unit_16_to_18.PlayerCharacter.Classes;
import unit_16_to_18.PlayerCharacter.Skills;

import java.io.*;

public class MainGame {

	//public objects that can be accessed in other classes
	public static PlayerConfig player = new PlayerConfig();
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static StringTokenizer st;
	
	public static void main(String[] args) throws Exception, IOException {
		// TODO Auto-generated method stub
		initSave();
	}

	//game other variables (private)
	private static PlayerCharacter[][] choice = new PlayerCharacter[4][4];
	
	
	/* GAME FUNCTIONS */
	public static void initSave() throws Exception {

		HelperPrint(0);
		java.util.concurrent.TimeUnit.SECONDS.sleep(5);
		HelperPrint(1);
		
		initCharacterArray();
		
		player.characters = new PlayerCharacter[4];
		player.current = new GameLevel();
		player.isGenocide = false;
		player.isPacifist = false;
		
		chooseCharacters();
		
		runGame();
		
	}
	
	public static void runGame() throws Exception {
		LevelList levels = new LevelList();
		levels.init(player);
		
		levels.levels[0].startLevel(player);
	}
	
	
	/* HELPER FUNCTIONS */
	/* helper print props and variables */
	static String[] classesProps = {"SABER: ", 
							 "LANCER: ", 
							 "CASTER: ",
							 "ARCHER: ",
							 "CAVALRY: ",
							 "ASSASSIN: ",
							 "SHIELDER: ",
							 "SOVEREIGN: "};
	static String[] classesDetails = {"Sword wielding, sabers are the dominant forces in close quarters, but struggles against ranged enemies.", 
			 "Spear wielding, they can deflect arrows with ease, but they cannot resist sabers in prolonged combats.", 
			 "Supports the team, and counters all assassins that strike from the dark. They need to be protected, though, as their HP cannot sustain hits.",
			 "Shoots ranged projectiles with very high damage, but struggles against lancers and assassins.",
			 "Lancers on horses, they deal heavy damage but cannot sustain their mana and can be easily defeated by assassins and casters who can scare the horse.",
			 "Striking from the shadows, they can deal heavy damage on unsuspecting enemies, but they can be attacked by casters.",
			 "Granting protection from all damage sources, they can tank a ton of damage but have limited offensive powers.",
			 "Secret class. Status Unknown but can overpower any known class."};
	
	public static void HelperPrint(int id) throws InterruptedException {
		switch (id) {
		
			case 0:
				System.out.println("Once upon a time...");
				System.out.println();
				java.util.concurrent.TimeUnit.SECONDS.sleep(1);
				System.out.println("There was a King fair and proud. He is the rightful King to Britain, and his name was King Arthur.\nBritain flourished under his rule"
						+ ",\nbut tragedy struck when his son, Mordred, betrayed him and ursurped the throne."
						+ "\nNow you have to help King Arthur take back what's"
						+ " rightfully his.\nFair winds traveller, and may you bring back the true King.");
				break;
			case 1:
				System.out.println("\n");
				System.out.println("Tutorial: \n"
						+ "Here's the essential game mechanics you must know. Let's start with the Classes: \n");
				
				String leftAlignFormat = "| %-11s | %-148s |%n";
				System.out.format("+-------------+------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
				System.out.format("| Classes     |                                                                             Properties                                                               |%n");
				System.out.format("+-------------+------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
				for (int i = 0; i < classesProps.length; i++) {
				    System.out.format(leftAlignFormat, classesProps[i], classesDetails[i]);
				}
				System.out.format("+-------------+------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
				System.out.println();
				
				System.out.println("Simplified Class Affinity: ");
				System.out.println("Saber, Cavalry > Lancer		Lancer > Archer, Caster			Archer, Cavalry > Saber\n"
								 + "Caster > Assassin		Assassin, Caster > Cavalry  		Assassin > any non Caster/Sovereign\n"
								 + "Sovereign > any class		any class < Shielder");
				System.out.println();
				break;
				
			default:
				break;
		}
		
	}
	
	static void chooseCharacters() throws Exception {
		for (int i=0; i<4; i++) {
			HelperPrint(choice[i]);
			int returned = handleSelection(choice[i], "Select a character (0-3): ", "number", choice[i].length);
			
			player.characters[i] = choice[i][returned];
		}
	}
	
	//overload method to support character array printing
	public static void HelperPrint(PlayerCharacter[] array) {
		String leftAlignFormat = "| %-21s | %-12s | %-25s | %-20s |%n";
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		System.out.format("|      Characters       |    Class     |        Properties         |        Skill         |%n");
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		for (int i=0; i<array.length; i++) {
			array[i].calculateSkillCD();
			String property = "Atk="+array[i].atk+", Def="+array[i].dfs+", Mna="+array[i].mna;
			System.out.format(leftAlignFormat, array[i].name, array[i].type, property, array[i].skill);
			System.out.format(leftAlignFormat, "", "", "Hitpoints: "+array[i].hp, "");
		}
		System.out.format("+-----------------------+--------------+---------------------------+----------------------+%n");
		System.out.println();
	}
	
	
	/* HELPER FUNCTIONS */
	public static void initCharacterArray() {
		choice[0][0] = new PlayerCharacter("King Arthur (saber)", 200, 100, 25, 450, Skills.EXCALIBUR, Classes.SABER);
		choice[0][1] = new PlayerCharacter("King Arthur (lancer)", 200, 100, 25, 450, Skills.RHONGOMYNIAD, Classes.LANCER);		
		choice[0][2] = new PlayerCharacter("King Arthur (archer)", 150, 175, 15, 450, Skills.HOLY_HAND_GRENADE, Classes.ARCHER);		
		choice[0][3] = new PlayerCharacter("King Arthur (cavalry)", 250, 125, 35, 250, Skills.RHONGOMYNIAD_CHARGE, Classes.CAVALRY);
		
		choice[1][0] = new PlayerCharacter("Merlin", 150, 50, 25, 1500, Skills.AVALON, Classes.CASTER);		
		choice[1][1] = new PlayerCharacter("Morgan", 150, 100, 5, 750, Skills.CURSED_ONSET, Classes.CASTER);		
		choice[1][2] = new PlayerCharacter("Merlin (prototype)", 150, 150, 15, 250, Skills.AVALON_PROTO, Classes.CASTER);		
		choice[1][3] = new PlayerCharacter("Morgan (prototype)", 100, 75, 15, 1000, Skills.CURSED_ONSET_PROTO, Classes.CASTER);		
		
		choice[2][0] = new PlayerCharacter("Galahad", 350, 75, 30, 200, Skills.CAMELOT_WALL, Classes.SHIELDER);		
		choice[2][1] = new PlayerCharacter("Bedivere", 200, 75, 20, 350, Skills.UNWAVERING_LOYALTY, Classes.SABER);
		choice[2][2] = new PlayerCharacter("Percival", 200, 100, 15, 200, Skills.LONGINUS_ZERO, Classes.LANCER);		
		choice[2][3] = new PlayerCharacter("Gawain", 225, 150, 25, 250, Skills.GALATINE, Classes.SABER);		
		
		choice[3][0] = new PlayerCharacter("Tristan", 150, 225, 5, 350, Skills.FAILNAUGHT, Classes.ARCHER);	
		choice[3][1] = new PlayerCharacter("Lucan", 250, 150, 25, 200, Skills.UNWAVERING_LOYALTY, Classes.CAVALRY);
		choice[3][2] = new PlayerCharacter("Gareth", 200, 100, 20, 250, Skills.IRA_LUPUS, Classes.LANCER);
		choice[3][3] = new PlayerCharacter("Rabbit of Caerbannog", 75, 1000, 0, 0, Skills.RABBIT, Classes.ASSASSIN);
	}
	
	public static int handleSelection(Object array, String selectMsg, String selectMethod, int length) throws Exception {
		if (!array.getClass().isArray()) throw new Exception("Params incorrect: must be an array"); // if not array that can be selected
		
		System.out.print(selectMsg);
		
		boolean isValidInput = false;
		int selector = 0; // scope level declaration
		
		if (selectMethod.equals("number")) {
			while (!isValidInput) {
				
				String input = br.readLine(); // read input
				
				while (!isInteger(input)) { // if not int
					System.out.println();
					System.out.println("==========EXCEPTION=========");
					System.out.println("|| Input must be a number!");
					System.out.println("==========EXCEPTION=========");
					System.out.println();
					System.out.print(selectMsg);
					input = br.readLine();
				}
				selector = Integer.parseInt(input);
				
				if (selector<0 || selector>(length-1)) { // if not valid
					System.out.println();
					System.out.println("==========EXCEPTION=========");
					System.out.println("|| Input must be at least 0 and no more than "+ (length-1));
					System.out.println("==========EXCEPTION=========");
					System.out.println();
					System.out.print(selectMsg);					
				} else isValidInput = true;
			}
			
			return selector;
			
		} else if (selectMethod.equals("character")) {
			while (!isValidInput) {
				String input = br.readLine(); // read input
				
				while (!isCharacter(input)) { // if not int
					System.out.println();
					System.out.println("==========EXCEPTION=========");
					System.out.println("|| Input must be a character!");
					System.out.println("==========EXCEPTION=========");
					System.out.println();
					System.out.print(selectMsg);
					input = br.readLine();
				}
				selector = Integer.parseInt(input)-65;
				
				if (selector<0 || selector>(length-1)) { // if not valid
					System.out.println();
					System.out.println("==========EXCEPTION=========");
					System.out.println("|| Input must be at least A and no more than "+ ((char)(65+length-1)));
					System.out.println("==========EXCEPTION=========");
					System.out.println();
					System.out.print(selectMsg);					
				} else isValidInput = true;			
				
				return selector;
			}
		} 
		
		throw new Exception("Params incorrect: handleSelection(array, message, method) must have either \"number\" or \"character\" passed in");
	}
	
	public static boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}
	
	public static boolean isCharacter(String str) {
		if (str.length() > 1) return false;
		char character = str.charAt(0);
		
		if (!Character.isAlphabetic(character)) return false;
		
		return true;
	}
}
