

package com.google.common.collect;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.core.java.util.Map_CustomFieldSerializerBase;
import java.util.LinkedHashMap;
import java.util.Map;


public class RegularImmutableMap_CustomFieldSerializer {

  public static void deserialize(SerializationStreamReader reader, ImmutableMap<?, ?> instance) {}

  public static ImmutableMap<Object, Object> instantiate(SerializationStreamReader reader)
      throws SerializationException {
    Map<Object, Object> entries = new LinkedHashMap<>();
    Map_CustomFieldSerializerBase.deserialize(reader, entries);
    return ImmutableMap.copyOf(entries);
  }

  public static void serialize(SerializationStreamWriter writer, ImmutableMap<?, ?> instance)
      throws SerializationException {
    Map_CustomFieldSerializerBase.serialize(writer, instance);
  }
}
