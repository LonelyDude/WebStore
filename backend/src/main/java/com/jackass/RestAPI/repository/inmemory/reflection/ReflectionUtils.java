package com.jackass.RestAPI.repository.inmemory.reflection;

import java.lang.reflect.ParameterizedType;

public class ReflectionUtils {

    public static Class getGenericParameterClass(Class actualClass, int parameterIndex) {
        return (Class) ((ParameterizedType) actualClass.getGenericSuperclass()).getActualTypeArguments()[parameterIndex];
    }

    public static Class getGenericParameterClass(Class actualClass) {
        return (Class) ((ParameterizedType) actualClass.getGenericSuperclass()).getActualTypeArguments()[0];
    }

}