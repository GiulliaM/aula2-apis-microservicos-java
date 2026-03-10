package systemFunctions;

import java.lang.reflect.Field;
import java.util.List;

public class GenericCsvExporter {

    public static <T> String exportarParaCsv(List<T> itens, List<String> colunasEscolhidas) {
        if (itens == null || itens.isEmpty() || colunasEscolhidas == null || colunasEscolhidas.isEmpty()) {
            return "Nenhum dado para exportar.";
        }

        StringBuilder csv = new StringBuilder();

        // 1. Montar o cabeçalho
        csv.append(String.join(",", colunasEscolhidas)).append("\n");

        // 2. Iterar sobre os itens (linhas)
        for (T item : itens) {
            Class<?> clazz = item.getClass();
            String prefixo = "";

            // 3. Buscar os valores das colunas escolhidas usando Reflection
            for (String nomeColuna : colunasEscolhidas) {
                try {
                    Field field = clazz.getDeclaredField(nomeColuna);
                    field.setAccessible(true); // Quebra o bloqueio do 'private' temporariamente
                    Object valor = field.get(item);

                    csv.append(prefixo).append(valor != null ? valor.toString() : "");
                    prefixo = ",";

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    // Se o usuário pedir uma coluna que não existe, deixa em branco
                    csv.append(prefixo).append("N/A");
                    prefixo = ",";
                }
            }
            csv.append("\n");
        }

        return csv.toString();
    }
}