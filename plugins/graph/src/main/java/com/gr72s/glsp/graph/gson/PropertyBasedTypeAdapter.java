/********************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Gson type adapter that can determine the actual Java class to use for a JSON
 * object based on a discriminator property.
 */
public abstract class PropertyBasedTypeAdapter<T> extends TypeAdapter<T> {

   private final Gson gson;

   private final String discriminator;

   public PropertyBasedTypeAdapter(final Gson gson, final String discriminator) {
      this.gson = gson;
      this.discriminator = discriminator;
   }

   @Override
   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   public T read(final JsonReader in) throws IOException {
      try {
         in.beginObject();
         T result = null;
         Map<String, JsonElement> unassignedProperties = null;
         while (in.hasNext()) {
            String propertyName = in.nextName();
            if (propertyName.equals(discriminator)) {
               if (result != null) {
                  throw new IllegalStateException("Property '" + discriminator + "' is defined twice.");
               }
               result = createInstance(in.nextString());
               if (unassignedProperties != null) {
                  for (Map.Entry<String, JsonElement> entry : unassignedProperties.entrySet()) {
                     assignProperty(result, entry.getKey(), entry.getValue());
                  }
               }
            } else if (result != null) {
               assignProperty(result, propertyName, in);
            } else {
               if (unassignedProperties == null) {
                  unassignedProperties = new HashMap<>();
               }
               unassignedProperties.put(propertyName, toTree(in));
            }
         }
         in.endObject();
         return result;
      } catch (IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }

   protected abstract T createInstance(String parameter);

   protected void assignProperty(final T instance, final String propertyName, final JsonReader in)
      throws IllegalAccessException, IOException {
      try {
         Field field = findField(instance.getClass(), propertyName);
         Object value = gson.fromJson(in, field.getGenericType());
         field.set(instance, value);
      } catch (NoSuchFieldException e) {
         // Ignore this property
         in.skipValue();
      }
   }

   protected void assignProperty(final T instance, final String propertyName, final JsonElement element)
      throws IllegalAccessException {
      try {
         Field field = findField(instance.getClass(), propertyName);
         Object value = gson.fromJson(element, field.getGenericType());
         field.set(instance, value);
      } catch (NoSuchFieldException e) {
         // Ignore this property
      }
   }

   protected Field findField(final Class<?> type, final String propertyName) throws NoSuchFieldException {
      try {
         Field field = type.getDeclaredField(propertyName);
         field.setAccessible(true);
         return field;
      } catch (NoSuchFieldException e) {
         Class<?> superType = type.getSuperclass();
         if (superType != null) {
            return findField(superType, propertyName);
         }
         throw e;
      }
   }

   protected JsonElement toTree(final JsonReader in) throws IOException {
      return JsonParser.parseReader(in);
   }

   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   protected void transfer(final JsonReader in, final JsonWriter out) throws IOException {
      JsonToken token = in.peek();
      switch (token) {
         case BEGIN_ARRAY:
            in.beginArray();
            out.beginArray();
            while (in.hasNext()) {
               transfer(in, out);
            }
            out.endArray();
            in.endArray();
            break;

         case BEGIN_OBJECT:
            in.beginObject();
            out.beginObject();
            while (in.hasNext()) {
               out.name(in.nextName());
               transfer(in, out);
            }
            out.endObject();
            in.endObject();
            break;

         case STRING:
            out.value(in.nextString());
            break;

         case NUMBER:
            out.value(in.nextDouble());
            break;

         case BOOLEAN:
            out.value(in.nextBoolean());
            break;

         case NULL:
            in.nextNull();
            out.nullValue();
            break;

         default:
            throw new IllegalStateException();
      }
   }

   @Override
   public void write(final JsonWriter out, final T value) throws IOException {
      if (value == null) {
         out.nullValue();
      } else {
         try {
            out.beginObject();
            Set<String> written = new HashSet<>();
            writeProperties(out, value, value.getClass(), written);
            if (!written.contains(discriminator)) {
               throw new RuntimeException("Object does not contain a field '" + discriminator + "'.");
            }
            out.endObject();
         } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
         }
      }
   }

   protected void writeProperties(final JsonWriter out, final T instance, final Class<?> type,
      final Set<String> written)
      throws IOException, IllegalAccessException {
      for (Field field : type.getDeclaredFields()) {
         int modifiers = field.getModifiers();
         if (!Modifier.isTransient(modifiers) && !Modifier.isStatic(modifiers) && written.add(field.getName())) {
            writeProperty(out, instance, field);
         }
      }
      Class<?> superType = type.getSuperclass();
      if (superType != null) {
         writeProperties(out, instance, superType, written);
      }
   }

   protected void writeProperty(final JsonWriter out, final T instance, final Field field)
      throws IOException, IllegalAccessException {
      field.setAccessible(true);
      out.name(field.getName());
      Object value = field.get(instance);
      if (value == null) {
         out.nullValue();
      } else if (value == instance) {
         throw new RuntimeException("Object has a reference to itself.");
      } else {
         gson.toJson(value, value.getClass(), out);
      }
   }

}
