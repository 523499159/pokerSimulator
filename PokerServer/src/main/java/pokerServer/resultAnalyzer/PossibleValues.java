package pokerServer.resultAnalyzer;

public enum PossibleValues {
	//figure(combination, sameColor,isConsecutive)
	Nothing("11111",false,false),
	OnePair("2111",false,false),//color and consecutive valueas dosnt matter
	TwoPair("221",false,false),//color and consecutive valueas dosnt matter
	Three("311",false,false),//color and consecutive valueas dosnt matter
	Straight("11111",false,true),//conecutive
	Flush("11111",true,false), //same color
	FullOfHouse("32",false,false),//color and consecutive valueas dosnt matter
	FourOfKings("41",false,false),//color and consecutive valueas dosnt matter
	Royal("11111",true,true);// color and consecutive

	private final String combination;
    private final Boolean colorSame;
    private final Boolean consecutive;

    private PossibleValues(String com, Boolean same, Boolean con) {
        this.combination = com;
        this.colorSame= same;
        this.consecutive = con;
    }

	public String getCombination() {
		return combination;
	}

	public Boolean getColorSame() {
		return colorSame;
	}

	public Boolean getCon() {
		return consecutive;
	}

	public int getOrdinal(String com, Boolean same, Boolean con){
		for(PossibleValues p:PossibleValues.values())
		if(
				p.combination.equals(com) &&
				p.colorSame.equals(same) &&
				p.consecutive.equals(con))	//always true
		{
			return p.ordinal();
		}

		return 0;
	}
}