alter table note add constraint note_unice unique (id_student, id_curs);
/

--varianta 1 | 27.109 secunde

set serveroutput on
declare
    v_counter number;
    v_id_student number := 1;
    v_id_maxim number;
begin
    for y in 1..1000000 loop
        select count(*) into v_counter from studenti s join note n on s.id = n.id_student join cursuri c on c.id = n.id_curs where c.titlu_curs = 'Logicã' and s.id=v_id_student;
        
        if v_counter = 0 then
            select max(id)+1 into v_id_maxim from note;
            insert into note values (v_id_maxim, v_id_student, 1, 10, sysdate, sysdate, sysdate);
        end if;
    end loop;
end;
/

--varianta 2 | 243.663 secunde

set serveroutput on;
declare
    v_id_student number := 1;
    v_id_maxim number;
begin
    for y in 1..1000000 loop
        declare
        begin
            select max(id)+1 into v_id_maxim from note;
            insert into note values (v_id_maxim, v_id_student, 1, 10, sysdate, sysdate, sysdate);
            
            exception
            when dup_val_on_index then
                dbms_output.put_line('Duplicate');
        end;
    end loop;
end;
/

create or replace function get_avg_grades(p_nume in varchar2, p_prenume in varchar2) return float as
v_medie float(20);
v_counter number;
student_nu_exista EXCEPTION;
PRAGMA EXCEPTION_INIT(student_nu_exista, -20001);
begin
    select count(*) into v_counter from studenti where nume=p_nume and prenume=p_prenume;
    if v_counter = 0 then
        raise student_nu_exista;
    else
        select avg(valoare) into v_medie from studenti s join note n on s.id = n.id_student where s.nume=p_nume and s.prenume=p_prenume;
    end if;
    
    return v_medie;
end;
/

set serveroutput on;
declare
    v_medie float(20);
    type varr is varray(1000) of varchar2(100);
    type lista is record
    (
        lista_nume varr := varr('Tudose', 'Asaftei', 'Negrutu', 'Amihaesei', 'Iacob', 'Vasile'),
        lista_prenume varr := varr('Danut', 'Catalin', 'Iaroslav', 'Sergiu', 'Cezar', 'Alexandru')
    );
    
    v_nume lista;
    
    student_nu_exista EXCEPTION;
    PRAGMA EXCEPTION_INIT(student_nu_exista, -20001);
begin
    for x in 1..v_nume.lista_nume.count loop
        begin
            v_medie := get_avg_grades(v_nume.lista_nume(x), v_nume.lista_prenume(x));
            dbms_output.put_line(v_medie);
            
            EXCEPTION
            WHEN student_nu_exista THEN
                dbms_output.put_line('Studentul cu numele ' || v_nume.lista_nume(x) || ' si prenumele ' || v_nume.lista_prenume(x) || ' nu exista.');
        end;
    end loop;
end;
/