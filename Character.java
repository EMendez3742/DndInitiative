/* This is the Character class, which includes information on Hp, spell slots,
initiative and other characer related information.
*/
public class Character {
	int spellSlot;
	int hp;
	float initiative;
	String name;
	String imageUrl;

	// constructor

	public Character(int hp, float initiative, String name) {
		this.hp = hp;
		this.initiative = initiative;
		this.name = name;
	}


	// getters and setters

	int getSpellSlot(){
		return spellSlot;
	}
	
	void setSpellSlot(int slots){
		spellSlot = slots;
	}

	int getHp(){
		return hp;
	}
	
	void setHp(int points){
		hp = points;
	}

	float getInitiative(){
		return initiative;
	}
	
	void setInitiative(int init){
		initiative = init;
	}

	String getName(){
		return name;
	}
	
	void setName(String n){
		name = n;
	}

	String getImageUrl(){
		return imageUrl;
	}
	
	void setImageUrl(String url){
		imageUrl = url;
	}

	// hp methods
	void takeHp(int points){
		hp -= points;
	}
	void addHp(int points){
		hp += points;
	}

	// spell slot methods
	void takeSpellSlot(int slots){
		spellSlot -= slots;
	}
	void addSpellSlot(int slots){
		spellSlot += slots;
	}
}
