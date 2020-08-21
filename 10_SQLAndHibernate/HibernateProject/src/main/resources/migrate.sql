DROP TABLE IF EXISTS Contracts;
CREATE TABLE Contracts (course_id INT(11) UNSIGNED NOT NULL, teacher_id INT(11) UNSIGNED NOT NULL) ENGINE=InnoDB;
ALTER TABLE Contracts ADD CONSTRAINT FKeyte4mvsbkiy1io9t3tptn65t FOREIGN KEY (teacher_id) REFERENCES Teachers (id);
ALTER TABLE Contracts ADD CONSTRAINT FKnvwkxn64npelx151ffsqlw7ou FOREIGN KEY (course_id) REFERENCES Courses (id);
INSERT INTO Contracts (course_id, teacher_id) SELECT Courses.id as course_id, Teachers.id as teacher_id FROM Courses JOIN Teachers ON Teachers.id = Courses.teacher_id;
