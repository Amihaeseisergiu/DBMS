--Amihaesei Sergiu, Grupa A3, Anul 2

set serveroutput on;
declare

v_first boolean;
v_nr_linii integer;

begin
    for x in (select * from user_tables) loop
        if (x.nested = 'YES') then
            dbms_output.put_line('Tabelul ' || x.table_name || ' are ' || x.num_rows || ' inregistrari si este nested table');
        else
            dbms_output.put_line('Tabelul ' || x.table_name || ' are ' || x.num_rows || ' inregistrari si nu este nested table');
        end if;
        
        v_first := true;
        for y in (select * from user_constraints natural join user_cons_columns where table_name = x.table_name) loop
            if v_first then
                dbms_output.put_line('Constrangerile tabelului:');
                v_first := false;
            end if;
            dbms_output.put_line('Constrangerea ' || y.constraint_name || ' este de tipul ' || y.constraint_type || ' si are coloana implicata ' || y.column_name);
        end loop;
        
        if v_first then
            dbms_output.put_line('Tabelul nu are constrangeri');
        end if;
        
        v_first := true;
        for y in (select * from user_indexes where table_name = x.table_name) loop
            if v_first then
                dbms_output.put_line('Indecsii tabelului:');
                v_first := false;
            end if;
            dbms_output.put_line('Indexul ' || y.index_name);
        end loop;
        
        if v_first then
            dbms_output.put_line('Tabelul nu are indecsi');
        end if;
    end loop;
    
    dbms_output.put_line('');
    
    for x in (select * from user_procedures) loop
        if x.object_type = 'FUNCTION' then
            select max(line) into v_nr_linii from user_source where name = x.object_name;
            if x.deterministic = 'NO' then
                dbms_output.put_line('Functia ' || x.object_name || ' are ' || v_nr_linii || ' linii si nu este determinista');
            else
                dbms_output.put_line('Functia ' || x.object_name || ' are ' || v_nr_linii || ' linii si este determinista');
            end if;
        elsif x.object_type = 'PROCEDURE' then
            select max(line) into v_nr_linii from user_source where name = x.object_name;
            if x.deterministic = 'NO' then
                dbms_output.put_line('Procedura ' || x.object_name || ' are ' || v_nr_linii || ' linii si nu este determinista');
            else
                dbms_output.put_line('Procedura ' || x.object_name || ' are ' || v_nr_linii || ' linii si este determinista');
            end if;
        end if;
    end loop;
    
    dbms_output.put_line('');
    
    for x in (select * from user_views) loop
        dbms_output.put_line('View-ul ' || x.view_name || ' are textul ' || x.text || ' cu ' || x.text_length || ' caractere');
    end loop;
    
    dbms_output.put_line('');
    
    for x in (select * from user_indexes) loop
        dbms_output.put_line('Indexul ' || x.index_name || ' de tipul ' || x.index_type || ' este format peste tabelul ' || x.table_name || ' si este ' || x.uniqueness);
        dbms_output.put_line('Coloanele implicate:');
        for y in (select * from user_ind_columns where index_name = x.index_name) loop
            dbms_output.put_line('Coloana ' || y.column_name || ' la pozitia ' || y.column_position);
        end loop;
    end loop;
    
    dbms_output.put_line('');
    
    for x in (select * from user_types) loop
        if x.typecode = 'COLLECTION' then
            select max(line) into v_nr_linii from user_type_versions where type_name = x.type_name;
            dbms_output.put_line('Tipul ' || x.type_name || ' este o colectie cu ' || v_nr_linii || ' linii');
        else
            select max(line) into v_nr_linii from user_type_versions where type_name = x.type_name;
            dbms_output.put_line('Tipul ' || x.type_name || ' este un obiect cu ' || v_nr_linii || ' linii');
            v_first := true;
            for y in (select * from user_type_attrs where type_name = x.type_name) loop
                if v_first then
                    dbms_output.put_line('Atributele sale:');
                    v_first := false;
                end if;
                dbms_output.put_line('Atributul ' || y.attr_name || ' de tipul ' || y.attr_type_name);
            end loop;
            if v_first then
                dbms_output.put_line('Obiectul nu are atribute');
            end if;
            
            v_first := true;
            for y in (select * from user_type_methods where type_name = x.type_name) loop
                if v_first then
                    dbms_output.put_line('Metodele sale:');
                    v_first := false;
                end if;
                if y.inherited = 'NO' then
                    dbms_output.put_line('Metoda ' || y.method_name || ' de tip ' || y.method_type || ' are ' || y.parameters || ' parametri si nu este mostenita');
                else
                    dbms_output.put_line('Metoda ' || y.method_name || ' de tip ' || y.method_type || ' are ' || y.parameters || ' parametri si este mostenita');
                end if;
            end loop;
            if v_first then
                dbms_output.put_line('Obiectul nu are metode');
            end if;
        end if;
    end loop;
    
    dbms_output.put_line('');
    
    for x in (select distinct(object_name) from user_procedures where object_type = 'PACKAGE') loop
        select max(line) into v_nr_linii from user_source where name=x.object_name;
        dbms_output.put_line('Pachetul ' || x.object_name || ' are ' || v_nr_linii || ' linii');
        dbms_output.put_line('Procedurile din pachet:');
        for y in (select * from user_procedures where object_name = x.object_name) loop
            if y.procedure_name != '(null)' then
                dbms_output.put_line('Procedura ' || y.procedure_name);
            end if;
        end loop;
    end loop;
end;