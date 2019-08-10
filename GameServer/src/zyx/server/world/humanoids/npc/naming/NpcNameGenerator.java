package zyx.server.world.humanoids.npc.naming;

import zyx.game.vo.Gender;

public class NpcNameGenerator
{

	private static final String TEMPLATE = "%s %s";
	private static final NpcSetup SETUP = new NpcSetup();
	
	public static NpcSetup generateSetup()
	{
		boolean isFemale = Math.random() > 0.5;
		
		String firstName = isFemale ? getFrom(FEMALE_FIRST_NAMES) : getFrom(FEMALE_FIRST_NAMES);
		String lastName = getFrom(LAST_NAMES);
		
		SETUP.name = String.format(TEMPLATE, firstName, lastName);
		SETUP.gender = isFemale ? Gender.FEMALE : Gender.MALE;
		
		return SETUP;
	}
	
	private static String getFrom(String[] array)
	{
		int index = (int) (array.length * Math.random());
		return array[index];
	}
	
	public static final String[] MALE_FIRST_NAMES =
	{
		"Oliver",
		"Jake",
		"Noah",
		"Jack",
		"Connor",
		"Liam",
		"John",
		"Harry",
		"Callum",
		"Mason",
		"Robert",
		"Jacob",
		"Michael",
		"Charlie",
		"Kyle",
		"Thomas",
		"Joe",
		"Ethan",
		"David",
		"George",
		"Reece",
		"Richard",
		"Oscar",
		"Rhys",
		"Alexander",
		"Joseph",
		"James",
		"Charles",
		"William",
		"Damian",
		"Daniel"
	};

	public static final String[] FEMALE_FIRST_NAMES =
	{
		"Amelia",
		"Margaret",
		"Emma",
		"Mary",
		"Olivia",
		"Samantha",
		"Patricia",
		"Isla",
		"Bethany",
		"Sophia",
		"Jennifer",
		"Emily",
		"Elizabeth",
		"Poppy",
		"Joanne",
		"Linda",
		"Ava",
		"Megan",
		"Mia",
		"Barbara",
		"Isabella",
		"Victoria",
		"Susan",
		"Jessica",
		"Lauren",
		"Abigail",
		"Lily",
		"Michelle",
		"Madison",
		"Sophie",
		"Tracy",
		"Charlotte",
		"Sarah"
	};

	public static final String[] LAST_NAMES =
	{
		"Smith",
		"Murphy",
		"Li",
		"Jones",
		"O'Kelly",
		"Johnson",
		"Williams",
		"O'Sullivan",
		"Lam",
		"Brown",
		"Walsh",
		"Martin",
		"Taylor",
		"Gelbero",
		"Davies",
		"O'Brien",
		"Miller",
		"Roy",
		"Wilson",
		"Byrne",
		"Davis",
		"Tremblay",
		"Morton",
		"Singh",
		"Evans",
		"O'Ryan",
		"Garcia",
		"Lee",
		"White",
		"Wang",
		"Thomas",
		"O'Connor",
		"Rodriguez",
		"Gagnon",
		"Roberts",
		"O'Neill",
		"Anderson"
	};
}
