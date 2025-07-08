# hibernate-project

Ένα maven project που προστέθηκαν dependencies 
* hibernate
* mysql-connector-j
* lombok.

Από τον Lombok χρησιμοποίησα Annotations για Constructors, Getters & Setters.

Δημιούργησα ένα school-app με package model και κλάσεις Course, Teacher, Region, TeacherMoreInfo.Έφτιαξα τις σχέσεις μεταξύ τους
(ManyToMany-OneToMany-OneToOne) και οι σχέσεις αυτές διαχειρίστηκαν μέσω μεθόδων.

Χρησιμοποίησα και ένα package enums για GenderType & LessonType.

Δημιουργήθηκε η βάση σε MySQL και φόρτωσα δεδομένα για να τρέξω κάποια queries.