/* Sa se introduca de la tastatura un an (1,2 sau 3). 
Pentru fiecare grupa in parte din acel an se va gasi ID-ul studentului cu media maxima
si i se va da o bursa de 5000 lei. Afisati cati studenti au avut bursa marita.
In acelasi script PLSQL, prin intermediul unui cursor cu parametru afisati toate cursurile
din anul introdus la tastatura (parametrul dat va fi anul introdus).
*/

set serveroutput on;

accept i_an prompt "Introduceti anul:";
DECLARE
    v_an INTEGER;
    i_an INTEGER;
    v_student_id INTEGER;
    v_medie_maxima FLOAT(5);
    v_contor INTEGER;
    v_contor_toate INTEGER;
BEGIN
    v_an := &i_an;
    if(v_an between 1 and 3)
        then
            v_contor_toate := 0;
            for v_grupa in (select unique grupa from studenti where an=v_an) loop
                select "medie" into v_medie_maxima from (select avg(valoare) as "medie" from studenti s join note n on s.id=n.id_student where s.an=v_an and s.grupa=v_grupa.grupa order by avg(valoare) desc) where rownum < 2;
                
                v_contor := 0;
                for v_student in (select s.id from studenti s join note n on s.id=n.id_student group by s.id,valoare,s.an,s.grupa having avg(valoare)=v_medie_maxima and s.grupa=v_grupa.grupa and s.an=v_an) loop
                    update studenti set bursa=5000 where studenti.id=v_student.id;
                    v_contor := v_contor + 1;
                end loop;
                v_contor_toate := v_contor_toate + v_contor;
            end loop;
            DBMS_OUTPUT.PUT_LINE('In total au fost marite ' || v_contor_toate || ' burse.');
            
            declare
                cursor lista_cursuri (p_an studenti.an%TYPE) is select titlu_curs from cursuri where an=p_an;
                v_std_linie lista_cursuri%ROWTYPE;
            begin
                open lista_cursuri(v_an);
                LOOP
                    FETCH lista_cursuri INTO v_std_linie;
                    EXIT WHEN lista_cursuri%NOTFOUND;
                    DBMS_OUTPUT.PUT_LINE('Cursul: ' || v_std_linie.titlu_curs);
                END LOOP;
                CLOSE lista_cursuri; 
            end;
            
        else DBMS_OUTPUT.PUT_LINE('An invalid');
    end if;
END;