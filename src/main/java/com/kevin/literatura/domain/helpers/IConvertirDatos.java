package com.kevin.literatura.domain.helpers;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
