package zyx.server.world.wallet;

public class MoneyJar
{

	private static MoneyJar instance = new MoneyJar();

	private int totalTips;
	private int totalCost;
	private int totalEarning;

	public static MoneyJar getInstance()
	{
		return instance;
	}

	private MoneyJar()
	{
	}

	public void addCost(int cost)
	{
		totalCost += cost;
		System.out.println("Added " + cost + " cost to a total of " + totalCost + ". New grand total: " + getTotalEarnings());
	}

	public void payEarning(int amount)
	{
		totalEarning += amount;
		System.out.println("Added " + amount + " earnings to a total of " + totalEarning + ". New grand total: " + getTotalEarnings());
	}

	public void addTip(int amount)
	{
		totalTips += amount;
		System.out.println("Added " + amount + " tips to a total of " + totalTips + ". New grand total: " + getTotalEarnings());
	}
	
	public void clean()
	{
		totalCost = 0;
		totalEarning = 0;
		totalTips = 0;
	}

	private int getTotalEarnings()
	{
		return totalTips + totalEarning - totalCost;
	}

}
