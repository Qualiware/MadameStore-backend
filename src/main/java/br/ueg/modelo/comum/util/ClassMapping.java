package br.ueg.modelo.comum.util;

import br.ueg.modelo.comum.exception.BusinessException;
import br.ueg.modelo.application.exception.SistemaMessageCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassMapping {
    public static Map<String,Object> mapEntityToMapFileValues(Object entity){
        Class<?> cls =  entity.getClass();
        Entity clsEntity = cls.getAnnotation(Entity.class);
        if(clsEntity == null){
            throw new BusinessException(SistemaMessageCode.ERRO_INESPERADO,true, "Classe não anotada com @Entity");
        }
        Map<String, Object> mapFieldValues = new HashMap<>();

        int fields = 0;
        for(Field field: cls.getDeclaredFields()){
            Column column = field.getAnnotation(Column.class);
            JoinColumn joinClumn = field.getAnnotation(JoinColumn.class);
            String fieldName = null;
            if(column != null){
                fieldName = column.name();
            }
            if (joinClumn !=null){
                fieldName = joinClumn.name();
            }

            if(fieldName != null){
                fields++;
                field.setAccessible(true);
                Object fieldValue = null;
                try {
                    fieldValue = field.get(entity);
                } catch (IllegalAccessException e) {
                    // TODO melhorar o tratamento
                    e.printStackTrace();
                }
                mapFieldValues.put(fieldName,fieldValue);
            }
        }
        if(fields == 0 ){
            throw new BusinessException(SistemaMessageCode.ERRO_INESPERADO,true,"Nenhum campo anotado com @Column");
        }
        return mapFieldValues;
    }
}
