package Users;

import Courses.Course;
import Courses.CourseStatus; // Importação do Enum
import Courses.Enrollment;
import Courses.EnrollmentException; // Importação da exceção customizada
import Courses.SubscriptionPlan;

import java.util.ArrayList;
import java.util.List; // Utilizando a interface List

public class Student extends User {
    // Declarando com a interface List
    private List<Enrollment> listaDeMatriculasAtuais = new ArrayList<>();
    private SubscriptionPlan plano; // Uma variável que pode ser do tipo BasicPlan ou PremiumPlan

    public Student(String name, String email, SubscriptionPlan plano) {
        super(name, email); // Parâmetros para criação da classe pai
        this.plano = plano;
    }

    public void matricular(Course cursoDesejado) {
        // Plano permite a matrícula?
        if (!this.plano.podeSeInscrever(listaDeMatriculasAtuais)) {
            throw new EnrollmentException("O seu plano não permite mais matrículas.");
        }

        // O curso está ativo? (Comparação atualizada para usar o Enum)
        if (cursoDesejado.getStatus() != CourseStatus.ACTIVE) {
            throw new EnrollmentException("O curso desejado não está ativo.");
        }

        // O aluno já não está matriculado nesse curso?
        if (this.listaDeMatriculasAtuais.stream().anyMatch(matricula -> matricula.getCursoMatriculado().equals(cursoDesejado))) {
            throw new EnrollmentException("O aluno já está matriculado nesse curso.");
        }

        // Aqui passou por todas as verificações, só falta cadastrar
        Enrollment novaMatricula = new Enrollment(cursoDesejado, this);

        // Adicionando a matrícula nova aos cursos atuais do aluno
        this.listaDeMatriculasAtuais.add(novaMatricula);
    }


    public List<Enrollment> getListaDeMatriculasAtuais() {
        return listaDeMatriculasAtuais;
    }

    public void setListaDeMatriculasAtuais(List<Enrollment> listaDeMatriculasAtuais) {
        this.listaDeMatriculasAtuais = listaDeMatriculasAtuais;
    }

    public SubscriptionPlan getPlano() {
        return plano;
    }

    public void setPlano(SubscriptionPlan plano) {
        this.plano = plano;
    }
}