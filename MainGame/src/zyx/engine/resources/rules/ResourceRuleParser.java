package zyx.engine.resources.rules;

import java.util.HashMap;

public class ResourceRuleParser
{

	private static final char DIVIDER = '.';

	private HashMap<Integer, ResourceRulePart> converterMap;
	private ParsedResource response;

	public ResourceRuleParser()
	{
		response = new ParsedResource();
		converterMap = new HashMap<>();
	}

	public ParsedResource parseResource(String resource)
	{
		int segmentCount = getSegmentCount(resource);
		ResourceRulePart converter = converterMap.get(segmentCount);
		
		converter.parsePath(resource, response);
		
		return response;
	}
	
	public void addRules()
	{
		ResourceRule[] rules = ResourceRule.values();

		for (ResourceRule rule : rules)
		{
			addRule(rule);
		}
	}

	private void addRule(ResourceRule rule)
	{
		ResourceRulePart part = converterMap.get(rule.segments);
		if(part == null)
		{
			part = new ResourceRulePart();
			converterMap.put(rule.segments, part);
		}
		
		part.addRule(rule);
	}
	
	private int getSegmentCount(String resource)
	{
		int count = 1;

		char[] chars = resource.toCharArray();
		int len = chars.length;
		for (int i = 0; i < len; i++)
		{
			if (chars[i] == DIVIDER)
			{
				count++;
			}
		}

		return count;
	}
}
