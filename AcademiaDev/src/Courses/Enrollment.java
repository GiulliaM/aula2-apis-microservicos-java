package Courses;

import Users.Student;

public class Enrollment {
    private Course cursoMatriculado;
    private Student aluno;
    private int progress; // porcentagem do progresso do curso

    public Enrollment(Course cursoMatriculado, Student aluno) {
        this.cursoMatriculado = cursoMatriculado;
        this.aluno = aluno;
        this.progress = 0;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if (progress <= 100 && progress >= 0) {
            this.progress = progress;
        } else {
            // Lançando a exceção customizada conforme o requisito
            throw new EnrollmentException("A porcentagem de conclusão do curso nao pode ser maior do que 100 nem negativa.");
        }
    }

    public Course getCursoMatriculado() { return cursoMatriculado; }
    public void setCursoMatriculado(Course cursoMatriculado) { this.cursoMatriculado = cursoMatriculado; }

    public Student getAluno() { return aluno; }
    public void setAluno(Student aluno) { this.aluno = aluno; }
}