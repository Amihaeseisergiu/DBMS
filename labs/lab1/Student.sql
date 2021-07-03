set serveroutput on;

declare
    v_nume_student studenti.nume%TYPE := &i_nume;
    v_prenume_student studenti.prenume%TYPE := &i_prenume;
    v_nota_maxima note.valoare%TYPE;
    v_curs_id note.id_curs%TYPE;
    v_nume_materie cursuri.titlu_curs%TYPE;
    v_studentul_exista number(10);
    v_nr_studenti number(10);
    v_grupa studenti.grupa%TYPE;
    v_an studenti.an%TYPE;
begin
    select count(*) into v_studentul_exista from studenti s where s.nume=v_nume_student and s.prenume=v_prenume_student;
    
    if(v_studentul_exista > 0)
        then
            select valoare, id_curs, grupa, an into v_nota_maxima, v_curs_id, v_grupa, v_an from (select valoare, id_curs, grupa, an from studenti s join note n on s.id=n.id_student where s.nume=v_nume_student and s.prenume=v_prenume_student order by valoare desc) where rownum<2;
            select titlu_curs into v_nume_materie from cursuri where id=v_curs_id;
            
            select count(*) into v_nr_studenti from (select 'a' from studenti s join note n on s.id=n.id_student join cursuri c on n.id_curs=c.id where n.valoare=v_nota_maxima and c.titlu_curs=v_nume_materie and s.grupa=v_grupa and s.an=v_an);
            
            DBMS_OUTPUT.PUT_LINE('Studentul: ' || v_nume_student || ' are nota: ' || v_nota_maxima || ' la ' || v_nume_materie );
            DBMS_OUTPUT.PUT_LINE('Nr studenti care au aceeasi nota la aceeasi materie:' || v_nr_studenti);
        else DBMS_OUTPUT.PUT_LINE('Studentul nu exista');
    end if;
end;


select valoare from note where id_student = (select id from studenti where nume='Dodan' and prenume='Dan');