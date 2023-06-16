package unit_16_to_18;



public class PlayerConfig {
	PlayerCharacter[] characters;
	GameLevel current;
	
	int levelsCompleted = 0;
	
	enum Actions {
		HEAL,
		ATTACK,
		DEFEND,
		MANA_ATTACK,
		SKILL
	}
}

