package unit_16_to_18;

public class LevelList {
	GameLevel[] levels;
	
	public void init(PlayerConfig config) throws Exception {
		if (config.current.name.equals("Init")) { // performing first time setup
			// load from preexisting template
			this.levels = new GameLevel[100];
			
			for (int i=0; i<10; i++) {
				for (int j=0; j<10; j++) { // each large section (world) has 10 smaller levels!
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

/*
 
 flowchart:

 +------------------+
  |    LevelList     |
  +------------------+
          |
          v
  +------------------+        +------------------+
  |      init()      | < --   |    PlayerConfig   |
  +------------------+        +------------------+
          |
          v
  +------------------+
  |  config.current.name.equals("Init") |
  +------------------+
          |
         / \
        /   \
       /     \
      /       \
  +-----+     +-----+
  |  Yes  |   |  No  | -> later update (2.0), loadsave feature
  +-----+     +-----+
      |         
      v         
  +------------------+
  |  load template   |
  +------------------+
          |
          v
  +------------------+
  |  levels = new GameLevel[100] |
  +------------------+
          |
          v
  +------------------+
  |  for i=0 to 9    |
  +------------------+
          |
          v
  +------------------+
  | for j=0 to 9     |
  +------------------+
          |
          v
  +------------------+
  |  create GameLevel |
  +------------------+
          |
          v
  +------------------+
  |    levels[(i*10)+j] = new GameLevel |
  +------------------+
          |
          v
  +------------------+
  |       continue       |
  +------------------+
          |
          v
  +------------------+
  |        end         |
  +------------------+

*/
