import Courses.Course;
import Courses.CourseCatalog;
import Courses.BasicPlan;
import Courses.PremiumPlan;
import Courses.CourseStatus;
import Courses.DifficultyLevel;
import Users.Admin;
import Users.Student;
import Users.User;

import java.time.Duration; // Importação necessária para o Duration
import java.util.Map;

public class InitialData {

    public static void carregarDados(CourseCatalog catalogo, Map<String, User> usuariosSistema) {

        // 1. Criando os Cursos com Duration e os novos Enums
        Course c1 = new Course("Java Foundations", "Curso básico de Java", "Prof. Isaque", Duration.ofHours(10), DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Course c2 = new Course("Spring Boot na Prática", "Desenvolvimento web com Spring", "Profa. Maria", Duration.ofHours(15), DifficultyLevel.INTERMEDIATE, CourseStatus.ACTIVE);
        Course c3 = new Course("Arquitetura de Microsserviços", "Escale suas aplicações", "Prof. Isaque", Duration.ofHours(20), DifficultyLevel.ADVANCED, CourseStatus.ACTIVE);
        Course c4 = new Course("Lógica de Programação", "Passos iniciais", "Prof. Alan", Duration.ofHours(5), DifficultyLevel.BEGINNER, CourseStatus.INACTIVE);
        Course c5 = new Course("Mensageria com Kafka", "Comunicação assíncrona", "Profa. Maria", Duration.ofHours(12), DifficultyLevel.ADVANCED, CourseStatus.ACTIVE);
        Course c6 = new Course("Segurança da Informação", "Fundamentos e inventário de ativos", "Prof. Robson Lopes", Duration.ofHours(40), DifficultyLevel.INTERMEDIATE, CourseStatus.ACTIVE);
        Course c7 = new Course("API e Microserviços", "Processamento de dados com Stream API", "Prof. Alan", Duration.ofHours(30), DifficultyLevel.ADVANCED, CourseStatus.ACTIVE);

        // Guardando os cursos no Catálogo que veio do Main
        catalogo.adicionarCurso(c1);
        catalogo.adicionarCurso(c2);
        catalogo.adicionarCurso(c3);
        catalogo.adicionarCurso(c4);
        catalogo.adicionarCurso(c5);
        catalogo.adicionarCurso(c6);
        catalogo.adicionarCurso(c7);

        // 2. Instanciando os Planos de Assinatura
        BasicPlan planoBasico = new BasicPlan();
        PremiumPlan planoPremium = new PremiumPlan();

        // 3. Criando os Alunos e o Admin do desafio
        Student carlos = new Student("Carlos", "carlos@email.com", planoBasico);
        Student ana = new Student("Ana", "ana@email.com", planoPremium);
        Admin admin = new Admin("Super Admin", "admin@email.com"); // Facilita os testes de menu!

        // Guardando os usuários no Mapa (Dicionário) do Main. A chave é o email!
        usuariosSistema.put(carlos.getEmail(), carlos);
        usuariosSistema.put(ana.getEmail(), ana);
        usuariosSistema.put(admin.getEmail(), admin);

        System.out.println("Dados iniciais carregados.");
    }
}