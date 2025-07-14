package gr.aueb.cf.schoolapp;

import gr.aueb.cf.schoolapp.enums.GenderType;
import gr.aueb.cf.schoolapp.model.Course;
import gr.aueb.cf.schoolapp.model.Region;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.model.TeacherMoreInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class App {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("school8PU");
    private static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

//        Select active teachers - JPQL
        TypedQuery<Teacher> query = em.createQuery("SELECT t FROM Teacher t WHERE t.active = :active", Teacher.class);
        List<Teacher> teachers = query.setParameter("active", true).getResultList();
        teachers.forEach(System.out::println);

        //Select active teachers - Criteria API
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Teacher> criteriaQuery = cb.createQuery(Teacher.class);
        Root<Teacher> teacherRoot = criteriaQuery.from(Teacher.class);
        criteriaQuery.select(teacherRoot)
                .where(cb.isTrue(teacherRoot.get("active")));

        List<Teacher> teachers1 = em.createQuery(criteriaQuery).getResultList();
        teachers1.forEach(System.out::println);

        // Select active teachers from "Αθήνα" - JPQL

        String sql = "SELECT t FROM Teacher t WHERE active = :active AND t.region.title = :regionTitle";
        TypedQuery<Teacher> query1 = em.createQuery(sql, Teacher.class);

           List<Teacher> teachers2 = query1.setParameter("active", true).setParameter("regionTitle", "Αθήνα").getResultList();
            teachers2.forEach(System.out::println);

        // Select active teachers from "Αθήνα" - Criteria API

        CriteriaBuilder cb1 = em.getCriteriaBuilder();
        CriteriaQuery<Teacher> query2 = cb.createQuery(Teacher.class);
        Root<Teacher> teacher = query2.from(Teacher.class);
        Join<Teacher, Region> region = teacher.join("region");
        ParameterExpression<String> regionTitle = cb.parameter(String.class);

        query2.select(teacher)
                .where(
                        cb.and(
                                cb.isTrue(teacher.get("active")),
                                cb.equal(region.get("title"), regionTitle)
                        )
                );

        List<Teacher> teachers3 = em.createQuery(query2)
                .setParameter(regionTitle, "Αθήνα")
                .getResultList();
        teachers3.forEach(System.out::println);


        // Select courses with comments containing 'Coding' - JPQL
        String sql1 = "SELECT c FROM Course c WHERE c.comments LIKE : comment";
        TypedQuery<Course> query3 = em.createQuery(sql1, Course.class);
        List<Course> courses = query3
                .setParameter("comment", "%Coding%")
                .getResultList();
        courses.forEach(System.out::println);

        // Select courses with comments containing 'Coding' - Criteria API
        CriteriaBuilder cb2 = em.getCriteriaBuilder();
        CriteriaQuery<Course> query4 = cb2.createQuery(Course.class);
        Root<Course> course = query4.from(Course.class);
        ParameterExpression<String> comment = cb2.parameter(String.class);

        query4.select(course)
                .where(
                        cb2.like(course.get("comments"), comment));

        List<Course> courses1 = em.createQuery(query4).setParameter(comment, "%Coding%").getResultList();
        courses1.forEach(System.out::println);

        // Select male/female teachers born after a specific date - JPQL
        String sql2 = "SELECT t FROM Teacher t " +
                "WHERE t.teacherMoreInfo.gender = :gender AND t.teacherMoreInfo.dateOfBirth > :date";
        TypedQuery<Teacher> query5 = em.createQuery(sql2, Teacher.class);

        List<Teacher> teachers4 = query5
                .setParameter("gender", GenderType.MALE)
                .setParameter("date", LocalDateTime.of(1970, 1, 1, 0, 0, 0))
                .getResultList();

        teachers4.forEach(System.out::println);

        // Select male/female teachers born after a specific date - Criteria API
        CriteriaBuilder cb3 = em.getCriteriaBuilder();
        CriteriaQuery<Teacher> query6 = cb3.createQuery(Teacher.class);
        Root<Teacher> teacher1 = query6.from(Teacher.class);
        Join<Teacher, TeacherMoreInfo> teacherMoreInfoPath = teacher1.join("teacherMoreInfo");
        ParameterExpression<GenderType> gender = cb3.parameter(GenderType.class);
        ParameterExpression<LocalDateTime> dateOfBirth = cb3.parameter(LocalDateTime.class);

        query6.select(teacher1)
                .where(
                        cb3.and(
                       cb3.equal(teacherMoreInfoPath.get("gender"), gender),
                       cb3.greaterThan(teacherMoreInfoPath.get("dateOfBirth"), dateOfBirth)
                        ));

        List<Teacher> teachers5 = em.createQuery(query6)
                .setParameter(gender, GenderType.MALE)
                .setParameter(dateOfBirth, LocalDateTime.of(1970, 1, 1, 0, 0, 0))
                .getResultList();
        teachers5.forEach(System.out::println);

        // Select for every region => region.title and count for inactive teachers per region - JPQL
        String sql3 = "SELECT r.title, count(t) FROM Region r LEFT JOIN r.teachers t " +
                "WHERE t.active = false OR t.active IS NULL GROUP BY r.title";
        TypedQuery<Object[]> query7 = em.createQuery(sql3,Object[].class);

        List<Object[]> countInactiveTeachersForAllRegions = query7.getResultList();

        for (Object[] row : countInactiveTeachersForAllRegions) {
            for (Object item : row) {
                System.out.print(item + " ");
            }
            System.out.println();
        }

        // Select for every region => region.title and count for inactive teachers per region - Criteria API
        CriteriaBuilder cb4 = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery1 = cb4.createQuery(Object[].class);
        Root<Region> regionRoot = criteriaQuery1.from(Region.class);
        Join<Region, Teacher> teacherJoin = regionRoot.join("teachers", JoinType.LEFT);

        criteriaQuery1.multiselect(
                regionRoot.get("title"),
                cb.count(teacherJoin)
        ).where(
                cb4.or(
                        cb4.isFalse(teacherJoin.get("active")),
                        cb4.isNull(teacherJoin.get("active"))

                )
        ).groupBy(regionRoot.get("title"));

        List<Object[]> countInactiveTeachersPerRegions = query7.getResultList();

        for (Object[] row : countInactiveTeachersPerRegions) {
            for (Object item : row) {
                System.out.print(item + " ");
            }
            System.out.println();
        }

        List<Teacher> teachers7 = findByCriteria(null, null, true);
            teachers7.forEach(System.out::println);
    }




    // filtering teachers με Multiple Criteria
    public static List<Teacher> findByCriteria(String firstname, String lastname, Boolean active) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = cb.createQuery(Teacher.class);
        Root<Teacher> teacher = query.from(Teacher.class);

        List<Predicate> predicates = new ArrayList<>();

        if (firstname != null) {
            predicates.add(cb.like(teacher.get("firstname"), firstname + "%"));
        }

        if (lastname != null) {
            predicates.add(cb.like(teacher.get("lastname"), lastname + "%"));
        }

        if (active != null) {
            predicates.add(cb.equal(teacher.get("active"), active));
        }

        query.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(query).getResultList();
    }
}
