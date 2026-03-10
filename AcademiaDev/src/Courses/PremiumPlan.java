package Courses;

import java.util.List;

public class PremiumPlan implements SubscriptionPlan {
    @Override
    public boolean podeSeInscrever(List<Enrollment> listaCursosMatriculados) {
        return true;
        // plano premium pode se inscrever em cursos ilimitados
    }
}