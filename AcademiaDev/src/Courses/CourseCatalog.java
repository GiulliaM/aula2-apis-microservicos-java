package Courses;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CourseCatalog {
    private final Map<String, Course> catalogoDeCursos = new HashMap<>();

    public CourseCatalog() {
    }

    public void adicionarCurso(Course curso) {
        if (!this.catalogoDeCursos.containsKey(curso.getTitle())) {
            this.catalogoDeCursos.put(curso.getTitle(), curso);
        }
    }

    public void listarCursos() {
        if (catalogoDeCursos.isEmpty()) {
            System.out.println("Nenhum curso disponivel.");
            return;
        }
        catalogoDeCursos.values().forEach(curso ->
                System.out.println(curso.getTitle() + " - " + curso.getDifficultyLevel())
        );
    }

    // NOVO MÉTODO: Necessário para os relatórios com Stream API depois
    public Collection<Course> getTodosCursos() {
        return catalogoDeCursos.values();
    }

    // NOVO MÉTODO: Necessário para buscar um curso na hora de matricular
    public Course buscarCurso(String title) {
        return catalogoDeCursos.get(title);
    }
}