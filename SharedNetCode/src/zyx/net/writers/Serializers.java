package zyx.net.writers;

import java.util.HashMap;
import zyx.net.data.WriteableDataArray;
import zyx.net.data.WriteableDataObject;

public class Serializers
{
	public static final short TYPE_BOOLEAN = 1;
	public static final short TYPE_BYTE = 2;
	public static final short TYPE_SHORT = 3;
	public static final short TYPE_CHARACTER = 4;
	public static final short TYPE_INTEGER = 5;
	public static final short TYPE_LONG = 6;
	public static final short TYPE_FLOAT = 7;
	public static final short TYPE_DOUBLE = 8;
	public static final short TYPE_STRING = 9;
	public static final short TYPE_OBJECT = 10;
	public static final short TYPE_ARRAY = 11;
	
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
	
	private static Serializers instance = new Serializers();

	private HashMap<Short, AbstractSerializer> serializerTypeMap;
	private HashMap<Class, AbstractSerializer> serializerClassMap;
	
	public Serializers()
	{
		serializerTypeMap = new HashMap<>();
		serializerClassMap = new HashMap<>();
		
		serializerTypeMap.put(TYPE_BOOLEAN, BOOLEAN_SERIALIZER);
		serializerTypeMap.put(TYPE_BYTE, BYTE_SERIALIZER);
		serializerTypeMap.put(TYPE_SHORT, SHORT_SERIALIZER);
		serializerTypeMap.put(TYPE_CHARACTER, CHARACTER_SERIALIZER);
		serializerTypeMap.put(TYPE_INTEGER, INTEGER_SERIALIZER);
		serializerTypeMap.put(TYPE_LONG, LONG_SERIALIZER);
		serializerTypeMap.put(TYPE_FLOAT, FLOAT_SERIALIZER);
		serializerTypeMap.put(TYPE_DOUBLE, DOUBLE_SERIALIZER);
		serializerTypeMap.put(TYPE_STRING, STRING_SERIALIZER);
		serializerTypeMap.put(TYPE_OBJECT, OBJECT_SERIALIZER);
		serializerTypeMap.put(TYPE_ARRAY, ARRAY_SERIALIZER);
		
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
	}
	
	public static Serializers getInstance()
	{
		return instance;
	}
	
	public AbstractSerializer getFromType(short type)
	{
		return serializerTypeMap.get(type);
	}
	
	public AbstractSerializer GetFromClass(Class type)
	{
		return serializerClassMap.get(type);
	}
}
