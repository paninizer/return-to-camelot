package unit_16_to_18;



public class PlayerConfig {
	PlayerCharacter[] characters;
	GameLevel current;
	boolean isGenocide;
	boolean isPacifist;
	
	enum Actions {
		HEAL,
		ATTACK,
		DEFEND,
		MANA_ATTACK,
		SKILL
	}
}

