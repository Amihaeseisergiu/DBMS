create or replace type lista is table of float(20);
/

alter table studenti add lista_medii lista nested table lista_medii store as lista_medii_tab;
/

set serveroutput on;
declare
    lista_med lista;
begin
for x in (select id from studenti order by id desc) loop
    lista_med := lista();
    for y in (select s.id, avg(valoare) medie from studenti s join note n on n.id_student = s.id join cursuri c on c.id = n.id_curs group by s.id,c.an,c.semestru having s.id=x.id) loop
    
        lista_med.EXTEND(1);
        lista_med(lista_med.COUNT) := y.medie;
    end loop;
    execute immediate 'update studenti set lista_medii=:1 where id=:2' using lista_med,x.id;
end loop;

end;
/

select * from studenti;
/

create or replace function get_nr_grades(p_id in number) return number as
lista_med lista;
begin
    select lista_medii into lista_med from studenti where id=p_id;
    return lista_med.COUNT;
end;
/

select get_nr_grades(1) from dual;
/