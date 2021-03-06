package zyx.engine.resources.rules;

import java.util.ArrayList;
import java.util.regex.Pattern;
import zyx.utils.cheats.Print;

public class ResourceRulePart
{

	private static final char PLACEHOLDER = '#';
	private static final Pattern DIVIDER_PATTERN = Pattern.compile("\\.");
	private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("#");

	private ArrayList<String[]> ruleSegments;
	private ArrayList<String> rulePaths;
	private ArrayList<ResourceRule> rules;

	public ResourceRulePart()
	{
		ruleSegments = new ArrayList<>();
		rulePaths = new ArrayList<>();
		rules = new ArrayList<>();
	}

	public void addRule(ResourceRule rule)
	{
		String[] parts = DIVIDER_PATTERN.split(rule.template);

		ruleSegments.add(parts);
		rulePaths.add(rule.path);
		rules.add(rule);
	}

	public void parsePath(String resource, ParsedResource out)
	{
		String[] resourceSplit = DIVIDER_PATTERN.split(resource);
		int index = getRuleIndex(resourceSplit);

		if (index < 0)
		{
			throw new RuntimeException("No resource rule given for: " + resource);
		}
		
		String[] segments = ruleSegments.get(index);

		String path = rulePaths.get(index);

		String resourcePart;
		String segmentPart;

		int len = resourceSplit.length;
		for (int i = 0; i < len; i++)
		{
			resourcePart = resourceSplit[i];
			segmentPart = segments[i];

			if (segmentPart.charAt(0) == PLACEHOLDER)
			{
				path = PLACEHOLDER_PATTERN.matcher(path).replaceFirst(resourcePart);
			}
		}

		out.path = path;
		out.rule = rules.get(index);
	}

	private int getRuleIndex(String[] resourceSplit)
	{
		int len = ruleSegments.size();
		for (int i = 0; i < len; i++)
		{
			String[] segments = ruleSegments.get(i);
			if (validateParts(resourceSplit, segments))
			{
				return i;
			}
		}

		return -1;
	}

	private boolean validateParts(String[] resourceSplit, String[] segments)
	{
		int len = resourceSplit.length;

		String resource;
		String segment;
		for (int i = 0; i < len; i++)
		{
			resource = resourceSplit[i];
			segment = segments[i];

			if (segment.charAt(0) == PLACEHOLDER)
			{
				//Is a placeholder, so ignore
				continue;
			}

			if (segment.equals(resource) == false)
			{
				//The two segments are not the same, so fail it
				return false;
			}
		}

		//All parts are either the same, or placeholders
		return true;
	}
}
