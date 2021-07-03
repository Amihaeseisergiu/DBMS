--Amihaesei Sergiu, Grupa A3, Anul 2

set serveroutput on;
create or replace view catalog as select s.nume, s.prenume, c.titlu_curs, n.valoare
    from studenti s join note n on s.id=n.id_student join cursuri c on c.id=n.id_curs;
/

create or replace trigger delete_student
    instead of delete on catalog
declare
    v_id_student studenti.id%TYPE;
    v_count number;
begin
    select count(*) into v_count from studenti where nume=:OLD.nume and prenume=:OLD.prenume;
    if v_count = 1 then
        select id into v_id_student from studenti where nume=:OLD.nume and prenume=:OLD.prenume;
        dbms_output.put_line('Stergem pe: ' || :OLD.nume || ' ' || :OLD.prenume);
        delete from note where id_student=v_id_student;
        delete from prieteni where id_student1=v_id_student;
        delete from prieteni where id_student2=v_id_student;
        delete from studenti where id=v_id_student;
    end if;
end;
/

create or replace trigger insert_nota_student_inexistent
    instead of insert on catalog
declare
    v_student_exista number;
    v_curs_exista number;
    v_id_curs number;
    v_id_maxim_note number;
    v_id_maxim_studenti number;
    v_nr_matricol varchar2(6);
    v_an int;
    v_grupa char(2);
    v_bursa int;
    v_data_nastere date;
    v_email varchar2(40);
begin
    select count(*) into v_student_exista from studenti where nume=:NEW.nume and prenume=:NEW.prenume;
    select count(*) into v_curs_exista from cursuri where titlu_curs=:NEW.titlu_curs;
    
    if v_student_exista = 0 and v_curs_exista = 1 then
        select max(id)+1 into v_id_maxim_studenti from studenti;
        v_nr_matricol := FLOOR(DBMS_RANDOM.VALUE(100,999)) || CHR(FLOOR(DBMS_RANDOM.VALUE(65,91))) || CHR(FLOOR(DBMS_RANDOM.VALUE(65,91))) || FLOOR(DBMS_RANDOM.VALUE(0,9));
        v_an := TRUNC(DBMS_RANDOM.VALUE(0,3))+1;
        v_grupa := chr(TRUNC(DBMS_RANDOM.VALUE(0,2))+65) || chr(TRUNC(DBMS_RANDOM.VALUE(0,6))+49);
        v_bursa := '';
        IF (DBMS_RANDOM.VALUE(0,100)<10) THEN
            v_bursa := TRUNC(DBMS_RANDOM.VALUE(0,10))*100 + 500;
        END IF;
        v_data_nastere := TO_DATE('01-01-1974','MM-DD-YYYY')+TRUNC(DBMS_RANDOM.VALUE(0,365));
        v_email := lower(:NEW.nume ||'.'|| :NEW.prenume) || '@gmail.com';
        insert into studenti values (v_id_maxim_studenti, v_nr_matricol, :NEW.nume, :NEW.prenume, v_an, v_grupa, v_bursa, v_data_nastere, v_email, sysdate, sysdate);
        
        select id into v_id_curs from cursuri where titlu_curs=:NEW.titlu_curs;
        select max(id)+1 into v_id_maxim_note from note;
        insert into note values (v_id_maxim_note, v_id_maxim_studenti, v_id_curs, :NEW.valoare, sysdate, sysdate, sysdate);
    end if;
end;
/

create or replace trigger insert_nota_curs_inexistent
    instead of insert on catalog
declare
    v_student_exista number;
    v_curs_exista number;
    v_id_student number;
    v_id_maxim_note number;
    v_id_maxim_cursuri number;
    v_an int;
    v_semestru int;
    v_credite int;
begin
    select count(*) into v_student_exista from studenti where nume=:NEW.nume and prenume=:NEW.prenume;
    select count(*) into v_curs_exista from cursuri where titlu_curs=:NEW.titlu_curs;
    
    if v_student_exista = 1 and v_curs_exista = 0 then
        select max(id)+1 into v_id_maxim_cursuri from cursuri;
        v_an := TRUNC(DBMS_RANDOM.VALUE(0,3))+1;
        v_semestru := TRUNC(DBMS_RANDOM.VALUE(0,2))+1;
        v_credite := TRUNC(DBMS_RANDOM.VALUE(0,6))+1;
        insert into cursuri values (v_id_maxim_cursuri, :NEW.titlu_curs, v_an, v_semestru, v_credite, sysdate, sysdate);
        
        select id into v_id_student from studenti where nume=:NEW.nume and prenume=:NEW.prenume;
        select max(id)+1 into v_id_maxim_note from note;
        insert into note values (v_id_maxim_note, v_id_student, v_id_maxim_cursuri, :NEW.valoare, sysdate, sysdate, sysdate);
    end if;
end;
/

create or replace trigger insert_nota_curs_student_inexistent
    instead of insert on catalog
declare
    v_student_exista number;
    v_curs_exista number;
    v_id_maxim_note number;
    v_id_maxim_cursuri number;
    v_id_maxim_studenti number;
    v_curs_an int;
    v_semestru int;
    v_credite int;
    v_nr_matricol varchar2(6);
    v_student_an int;
    v_grupa char(2);
    v_bursa int;
    v_data_nastere date;
    v_email varchar2(40);
begin
    select count(*) into v_student_exista from studenti where nume=:NEW.nume and prenume=:NEW.prenume;
    select count(*) into v_curs_exista from cursuri where titlu_curs=:NEW.titlu_curs;
    
    if v_student_exista = 0 and v_curs_exista = 0 then
        select max(id)+1 into v_id_maxim_cursuri from cursuri;
        v_curs_an := TRUNC(DBMS_RANDOM.VALUE(0,3))+1;
        v_semestru := TRUNC(DBMS_RANDOM.VALUE(0,2))+1;
        v_credite := TRUNC(DBMS_RANDOM.VALUE(0,6))+1;
        insert into cursuri values (v_id_maxim_cursuri, :NEW.titlu_curs, v_curs_an, v_semestru, v_credite, sysdate, sysdate);
        
        select max(id)+1 into v_id_maxim_studenti from studenti;
        v_nr_matricol := FLOOR(DBMS_RANDOM.VALUE(100,999)) || CHR(FLOOR(DBMS_RANDOM.VALUE(65,91))) || CHR(FLOOR(DBMS_RANDOM.VALUE(65,91))) || FLOOR(DBMS_RANDOM.VALUE(0,9));
        v_student_an := TRUNC(DBMS_RANDOM.VALUE(0,3))+1;
        v_grupa := chr(TRUNC(DBMS_RANDOM.VALUE(0,2))+65) || chr(TRUNC(DBMS_RANDOM.VALUE(0,6))+49);
        v_bursa := '';
        IF (DBMS_RANDOM.VALUE(0,100)<10) THEN
            v_bursa := TRUNC(DBMS_RANDOM.VALUE(0,10))*100 + 500;
        END IF;
        v_data_nastere := TO_DATE('01-01-1974','MM-DD-YYYY')+TRUNC(DBMS_RANDOM.VALUE(0,365));
        v_email := lower(:NEW.nume ||'.'|| :NEW.prenume) || '@gmail.com';
        insert into studenti values (v_id_maxim_studenti, v_nr_matricol, :NEW.nume, :NEW.prenume, v_student_an, v_grupa, v_bursa, v_data_nastere, v_email, sysdate, sysdate);
        
        select max(id)+1 into v_id_maxim_note from note;
        insert into note values (v_id_maxim_note, v_id_maxim_studenti, v_id_maxim_cursuri, :NEW.valoare, sysdate, sysdate, sysdate);
    end if;
end;
/

create or replace trigger update_nota_student
    instead of update on catalog
declare
    v_id_student number := -1;
    v_id_curs number := -1;
begin
    select id into v_id_student from studenti where nume=:NEW.nume and prenume=:NEW.prenume;
    select id into v_id_curs from cursuri where titlu_curs=:NEW.titlu_curs;
    if :OLD.valoare < :NEW.valoare and v_id_student > 0 and v_id_curs > 0 then
        update note set valoare = :NEW.valoare, updated_at = sysdate where id_student=v_id_student and id_curs=v_id_curs;
    end if;  
end;
/

delete from catalog where nume='Prisecaru' and prenume='Ioana Oana';
/

insert into catalog values ('Amihaesei', 'Sergiu', 'Sisteme de operare', 10);
/

insert into catalog values ('Costan', 'Anda Stefana', 'Yoga', 10);
/

insert into catalog values ('Iacob', 'Cezar', 'Esports', 9);
/

update catalog set valoare=10 where nume='Iacob' and prenume='Cezar';
/
