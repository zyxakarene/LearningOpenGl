package zyx.net.writers;

import java.util.HashMap;
import zyx.net.data.ByteArrayObject;
import zyx.net.data.WriteableDataArray;
import zyx.net.data.WriteableDataObject;

public class Serializers
{
	public static final short TYPE_BOOLEAN = 0;
	public static final short TYPE_BYTE = 1;
	public static final short TYPE_SHORT = 2;
	public static final short TYPE_CHARACTER = 3;
	public static final short TYPE_INTEGER = 4;
	public static final short TYPE_LONG = 5;
	public static final short TYPE_FLOAT = 6;
	public static final short TYPE_DOUBLE = 7;
	public static final short TYPE_STRING = 8;
	public static final short TYPE_OBJECT = 9;
	public static final short TYPE_ARRAY = 10;
	public static final short TYPE_BYTE_ARRAY = 11;
	
	public final AbstractSerializer BOOLEAN_SERIALIZER = new BooleanSerializer();
	public final AbstractSerializer BYTE_SERIALIZER = new ByteSerializer();
	public final AbstractSerializer SHORT_SERIALIZER = new ShortSerializer();
	public final AbstractSerializer CHARACTER_SERIALIZER = new CharacterSerializer();
	public final AbstractSerializer INTEGER_SERIALIZER = new IntegerSerializer();
	public final AbstractSerializer LONG_SERIALIZER = new LongSerializer();
	public final AbstractSerializer FLOAT_SERIALIZER = new FloatSerializer();
	public final AbstractSerializer DOUBLE_SERIALIZER = new DoubleSerializer();
	public final AbstractSerializer STRING_SERIALIZER = new StringSerializer();
	public final AbstractSerializer OBJECT_SERIALIZER = new ObjectSerializer();
	public final AbstractSerializer ARRAY_SERIALIZER = new ArraySerializer();
	public final AbstractSerializer BYTE_ARRAY_SERIALIZER = new ByteArraySerializer();
	
	private static Serializers instance = new Serializers();

	private AbstractSerializer[] serializerTypeMap;
	private HashMap<Class, AbstractSerializer> serializerClassMap;
	
	public Serializers()
	{
		serializerTypeMap = new AbstractSerializer[12];
		serializerClassMap = new HashMap<>();
		
		serializerTypeMap[TYPE_BOOLEAN] = BOOLEAN_SERIALIZER;
		serializerTypeMap[TYPE_BYTE] = BYTE_SERIALIZER;
		serializerTypeMap[TYPE_SHORT] = SHORT_SERIALIZER;
		serializerTypeMap[TYPE_CHARACTER] = CHARACTER_SERIALIZER;
		serializerTypeMap[TYPE_INTEGER] = INTEGER_SERIALIZER;
		serializerTypeMap[TYPE_LONG] = LONG_SERIALIZER;
		serializerTypeMap[TYPE_FLOAT] = FLOAT_SERIALIZER;
		serializerTypeMap[TYPE_DOUBLE] = DOUBLE_SERIALIZER;
		serializerTypeMap[TYPE_STRING] = STRING_SERIALIZER;
		serializerTypeMap[TYPE_OBJECT] = OBJECT_SERIALIZER;
		serializerTypeMap[TYPE_ARRAY] = ARRAY_SERIALIZER;
		serializerTypeMap[TYPE_BYTE_ARRAY] = BYTE_ARRAY_SERIALIZER;
		
		serializerClassMap.put(Boolean.class, BOOLEAN_SERIALIZER);
		serializerClassMap.put(Byte.class, BYTE_SERIALIZER);
		serializerClassMap.put(Short.class, SHORT_SERIALIZER);
		serializerClassMap.put(Character.class, CHARACTER_SERIALIZER);
		serializerClassMap.put(Integer.class, INTEGER_SERIALIZER);
		serializerClassMap.put(Long.class, LONG_SERIALIZER);
		serializerClassMap.put(Float.class, FLOAT_SERIALIZER);
		serializerClassMap.put(Double.class, DOUBLE_SERIALIZER);
		serializerClassMap.put(String.class, STRING_SERIALIZER);
		serializerClassMap.put(WriteableDataObject.class, OBJECT_SERIALIZER);
		serializerClassMap.put(WriteableDataArray.class, ARRAY_SERIALIZER);
		serializerClassMap.put(ByteArrayObject.class, BYTE_ARRAY_SERIALIZER);
	}
	
	public static Serializers getInstance()
	{
		return instance;
	}
	
	public AbstractSerializer getFromType(short type)
	{
		return serializerTypeMap[type];
	}
	
	public AbstractSerializer GetFromClass(Class type)
	{
		return serializerClassMap.get(type);
	}
}
