package com.umg.utils;

import com.umg.dto.PageResponse;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PaginationUtils {

    /**
     * Pagina y filtra cualquier lista de objetos.
     *
     * @param todos       La lista completa de datos traída de la BD.
     * @param page        Página actual (0-based).
     * @param size        Tamaño de la página.
     * @param query       El filtro en formato RQL del frontend.
     * @param fieldGetter Una función que le dice a Java cómo obtener el valor de un campo dinámico (usando Reflexión).
     * @param <T>         El tipo de dato (Cliente, Cuenta, etc).
     */
    public static <T> PageResponse<T> paginate(List<T> todos, int page, int size, String query, Function<String, Function<T, Object>> fieldGetter) {

        if (query != null && !query.isEmpty()) {
            String[] conditions = query.split(" and ");
            for (String condition : conditions) {
                if (condition.contains("==")) {
                    String[] parts = condition.split("==");
                    String field = parts[0].replace("(", "").trim();
                    String term = parts[1].replace("'", "").replace("*", "").trim().toLowerCase();

                    Function<T, Object> getter = fieldGetter.apply(field);
                    if (getter != null) {
                        todos = todos.stream()
                                .filter(item -> {
                                    Object value = getter.apply(item);
                                    return value != null && value.toString().toLowerCase().contains(term);
                                })
                                .collect(Collectors.toList());
                    }
                }
            }
        }

        int total = todos.size();
        int start = Math.min(page * size, total);
        int end = Math.min(start + size, total);
        List<T> content = todos.subList(start, end);

        return new PageResponse<>(content, total, size, page);
    }
}