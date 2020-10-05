package stub;

import java.util.List;

public class GradeService {
    private final GradeSystem gradeSystem;

    public GradeService(GradeSystem gradeSystem) {
        this.gradeSystem = gradeSystem;
    }

    public double calculateAverageGrades(long studentId) {
        List<Double> doubles = gradeSystem.gradesFor(studentId);

        double averageGrade = doubles.stream()
                .mapToDouble((grade) -> grade)
                .average()
                .orElse(0);

        return averageGrade;
    }
}
