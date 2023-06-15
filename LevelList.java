package unit_16_to_18;

public class LevelList {
	GameLevel[] levels;
	
	public void init(PlayerConfig config) throws Exception {
		if (config.current.name.equals("Init")) { // performing first time setup
			// load from preexisting template
			this.levels = new GameLevel[100];
			
			for (int i=0; i<10; i++) {
				for (int j=0; j<10; j++) { // each large section has 10 smaller levels!
					String act = "Act " + i + ": Level " + j;
					if (i==9&&j==9) {
						levels[(i*10)+j] = new GameLevel(act, i, true);
						continue;
					}
						
					levels[(i*10)+j] = new GameLevel(act, i, false);
				}
			}
		}
	}
}
