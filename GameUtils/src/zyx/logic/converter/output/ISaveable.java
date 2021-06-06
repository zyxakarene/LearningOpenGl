package zyx.logic.converter.output;

import java.io.DataOutputStream;
import java.io.IOException;

public interface ISaveable
{
	void save(DataOutputStream out) throws IOException;
}
