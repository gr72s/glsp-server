/********************************************************************************
 * Copyright (c) 2019-2024 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package com.gr72s.glsp.graph.gson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A custom type adapter for enums that uses integer values.
 */
public class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {

   public static class Factory implements TypeAdapterFactory {

      @Override
      @SuppressWarnings({ "unchecked", "rawtypes" })
      public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
         Class<?> rawType = typeToken.getRawType();
         if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
            return null;
         }
         if (!rawType.isEnum()) {
            rawType = rawType.getSuperclass();
         }
         try {
            return new EnumTypeAdapter(rawType);
         } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
         }
      }

   }

   protected static String VALUE_FIELD_NAME = "value";

   protected final Map<String, T> nameToConstant = new HashMap<>();
   protected final Map<Integer, T> valueToConstant = new HashMap<>();
   protected final Map<T, Integer> constantToValue = new HashMap<>();

   EnumTypeAdapter(final Class<T> classOfT) throws IllegalAccessException {
      try {
         Field valueField = classOfT.getDeclaredField(VALUE_FIELD_NAME);
         if (valueField.getType() != int.class && valueField.getType() != Integer.class) {
            throw new IllegalArgumentException("The field 'value' must contain an integer value.");
         }
         valueField.setAccessible(true);
         for (T constant : classOfT.getEnumConstants()) {
            nameToConstant.put(constant.name(), constant);
            Integer constValue = (Integer) valueField.get(constant);
            valueToConstant.put(constValue, constant);
            constantToValue.put(constant, constValue);
         }
      } catch (NoSuchFieldException e) {
         for (T constant : classOfT.getEnumConstants()) {
            nameToConstant.put(constant.name(), constant);
            int constValue = constant.ordinal();
            valueToConstant.put(constValue, constant);
            constantToValue.put(constant, constValue);
         }
      }
   }

   @Override
   public T read(final JsonReader in) throws IOException {
      JsonToken peek = in.peek();
      if (peek == JsonToken.NULL) {
         in.nextNull();
         return null;
      } else if (peek == JsonToken.NUMBER) {
         return valueToConstant.get(in.nextInt());
      } else {
         String string = in.nextString();
         try {
            return valueToConstant.get(Integer.parseInt(string));
         } catch (NumberFormatException e) {
            return nameToConstant.get(string);
         }
      }
   }

   @Override
   public void write(final JsonWriter out, final T value) throws IOException {
      if (value != null) {
         out.value(constantToValue.get(value));
      } else {
         out.value((String) null);
      }
   }

}
