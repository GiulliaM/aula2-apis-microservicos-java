package Courses;

import java.util.List;

public class BasicPlan implements SubscriptionPlan {
    @Override
    public boolean podeSeInscrever(List<Enrollment> listaCursosMatriculados) {
        // limite de cursos maximo atingido
        return listaCursosMatriculados.size() <= 2;
    }
}