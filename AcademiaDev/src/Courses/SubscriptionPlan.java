package Courses;

import java.util.List;

public interface SubscriptionPlan {
    boolean podeSeInscrever(List<Enrollment> listaCursosMatriculados);
}