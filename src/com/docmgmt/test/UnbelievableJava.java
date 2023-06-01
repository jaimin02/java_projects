package com.docmgmt.test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class UnbelievableJava {
	static void pleaseDoNotDoThis() throws Exception {
		Field field = Boolean.class.getField("FALSE");
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, true);
	}

	
	public static void main(String[] args) throws Exception {

		Class<?> cache = Integer.class.getDeclaredClasses()[0];
		Field c = cache.getDeclaredField("cache");
		c.setAccessible(true);

		Integer[] array = (Integer[]) c.get(cache);
		array[132] = array[133];
		System.out.format("%d",(2+2));
		
		pleaseDoNotDoThis();
		doit(1);
		

	}

	static void doit(long x) {
		System.out.format("(x == x + 2) = (%d == %d) = %s\n", x, x + 2,
				(x == x + 2));
	}
}
