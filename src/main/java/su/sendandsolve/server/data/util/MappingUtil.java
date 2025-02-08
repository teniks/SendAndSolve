package su.sendandsolve.server.data.util;

import java.lang.reflect.Field;
import java.util.Arrays;

public class MappingUtil {
    public static void copyFields(Object source, Object target) {
        // Создание потока для обработки всех полей класса source
        Arrays.stream(source.getClass().getDeclaredFields())
                .forEach(field -> {
                    try {
                        // Установка доступа к полю
                        field.setAccessible(true);
                        // Извлечение значения поля
                        Object value = field.get(source);
                        // Поиск поля с таким же именем в классе target
                        Field targetField = target.getClass()
                                .getDeclaredField(field.getName());
                        // Установка доступа к полю в классе target
                        targetField.setAccessible(true);
                        // Установка значения поля в target
                        targetField.set(target, value);
                    }catch (Exception ignored){  }
                });
    }
}
