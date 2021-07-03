create or replace NONEDITIONABLE function get_friend_suggestions(p_id IN NUMBER) return varchar2
as ret varchar2(2000) := '';
begin

for x in (SELECT JSON_OBJECT('id' is id, 'Nume' is nume, 'Prenume' is prenume, 'Numar prieteni comuni' is nr_prieteni) student_json from (
    SELECT y.id_student2 AS id, (SELECT nume FROM studenti WHERE id=y.id_student2) AS nume, (SELECT prenume FROM studenti WHERE id = y.id_student2) AS prenume, COUNT(*) AS nr_prieteni FROM prieteni x
    LEFT JOIN prieteni y  ON y.id_student1 = x.id_student2         
    WHERE x.id_student1 = p_id
    AND  y.id_student2 not in (SELECT id_student1 FROM prieteni WHERE id_student2 = p_id)
    AND  y.id_student2 not in (SELECT id_student2  FROM prieteni WHERE id_student1 = p_id)
    GROUP BY y.id_student2 ORDER BY count(*) DESC
) WHERE ROWNUM < 6) loop
    ret := ret || x.student_json;
end loop;

return ret;
end;