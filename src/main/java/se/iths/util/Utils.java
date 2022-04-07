package se.iths.util;

import se.iths.exceptions.EntityBadRequestException;

import java.util.Map;

public class Utils {

    public static String stringBuilder(Map<String, String> queryMap, String entity) {
        StringBuilder queryString = new StringBuilder("SELECT s FROM " + entity + " s WHERE ");

        if(!queryMap.isEmpty()) {
            for (Map.Entry<String, String> param : queryMap.entrySet()) {
                queryString.append(" s.")
                        .append(param.getKey())
                        .append(" = '")
                        .append(param.getValue())
                        .append("' AND ");
            }
        } else {
            throw new EntityBadRequestException("You must add at least one parameter");
        }

        if(queryString.toString().endsWith("AND ")) {
            queryString.replace(queryString.length() -5,queryString.length(), "");
        }

        return queryString.toString();
    }
}
