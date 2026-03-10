package systemFunctions;

import Courses.Course;
import Courses.CourseStatus;
import Courses.DifficultyLevel;
import Courses.Enrollment;
import Users.Student;
import Users.User;

import java.util.*;
import java.util.stream.Collectors;

public class ReportService {

    // 1. Lista de cursos por nível de dificuldade, ordenada alfabeticamente
    public static void listarCursosPorDificuldade(Collection<Course> cursos, DifficultyLevel nivel) {
        System.out.println("\n--- Cursos nível " + nivel + " ---");
        cursos.stream()
                .filter(c -> c.getDifficultyLevel() == nivel)
                .sorted(Comparator.comparing(Course::getTitle))
                .forEach(c -> System.out.println("- " + c.getTitle() + " (Instrutor: " + c.getInstructorName() + ")"));
    }

    // 2. Relação de instrutores únicos (Set) que ministram cursos ACTIVE
    public static void listarInstrutoresAtivos(Collection<Course> cursos) {
        System.out.println("\n--- Instrutores Ativos ---");
        Set<String> instrutores = cursos.stream()
                .filter(c -> c.getStatus() == CourseStatus.ACTIVE)
                .map(Course::getInstructorName)
                .collect(Collectors.toSet()); // O Set garante que não haverá nomes repetidos

        instrutores.forEach(System.out::println);
    }

    // 3. Agrupamento de alunos por plano de assinatura
    public static void agruparAlunosPorPlano(Collection<User> usuarios) {
        System.out.println("\n--- Alunos por Plano ---");
        Map<String, List<Student>> agrupamento = usuarios.stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                // Agrupa usando o nome da classe do plano (BasicPlan ou PremiumPlan)
                .collect(Collectors.groupingBy(s -> s.getPlano().getClass().getSimpleName()));

        agrupamento.forEach((plano, alunos) -> {
            System.out.println("Plano: " + plano);
            alunos.forEach(a -> System.out.println("  - " + a.getName()));
        });
    }

    // 4. Média geral de progresso considerando todas as matrículas
    public static void exibirMediaGeralProgresso(Collection<User> usuarios) {
        System.out.println("\n--- Média de Progresso Global ---");
        OptionalDouble media = usuarios.stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .flatMap(s -> s.getListaDeMatriculasAtuais().stream()) // Junta todas as listas de matrículas em um único Stream
                .mapToInt(Enrollment::getProgress) // Pega apenas o número do progresso
                .average();

        if (media.isPresent()) {
            System.out.printf("A média geral de conclusão dos cursos é: %.2f%%\n", media.getAsDouble());
        } else {
            System.out.println("Ainda não há matrículas no sistema.");
        }
    }

    // 5. O aluno com mais matrículas (Obrigatório retornar Optional)
    public static Optional<Student> getAlunoComMaisMatriculas(Collection<User> usuarios) {
        return usuarios.stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .max(Comparator.comparingInt(s -> s.getListaDeMatriculasAtuais().size()));
    }
}