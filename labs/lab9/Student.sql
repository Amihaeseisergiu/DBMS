--Amihaesei Sergiu, Grupa A3, Anul 2
--functia getType pentru conversia tipului de date in varchar2 este luata de aici: https://pastebin.com/ZsFQVWbQ


create or replace procedure generare_catalog(id_materie IN INTEGER) as
   v_cursor_id INTEGER;
   v_ok INTEGER;
   v_rec_tab DBMS_SQL.DESC_TAB;
   v_nr_col NUMBER;
   v_total_coloane NUMBER;
   
   v_titlu_curs VARCHAR2(1000);
   v_create_string VARCHAR2(2000);
   v_insert_string VARCHAR2(2000);
   v_first BOOLEAN := true;
   v_table_exists NUMBER;
begin
  v_cursor_id := DBMS_SQL.OPEN_CURSOR;
  DBMS_SQL.PARSE(v_cursor_id, 'SELECT SUBSTR(REPLACE(REPLACE(REPLACE(titlu_curs,'' ''), '',''), ''-''), 0, 29) FROM cursuri where id=' || id_materie, DBMS_SQL.NATIVE);
  DBMS_SQL.DEFINE_COLUMN(v_cursor_id, 1, v_titlu_curs, 1000); 
   
  v_ok := DBMS_SQL.EXECUTE(v_cursor_id);
  
  IF DBMS_SQL.FETCH_ROWS(v_cursor_id)>0 THEN 
      DBMS_SQL.COLUMN_VALUE(v_cursor_id, 1, v_titlu_curs); 
      DBMS_SQL.CLOSE_CURSOR(v_cursor_id);
      
      v_create_string := 'CREATE TABLE ' || v_titlu_curs || '(';
      
      v_cursor_id  := DBMS_SQL.OPEN_CURSOR;
      DBMS_SQL.PARSE(v_cursor_id , 'SELECT nume,prenume,nr_matricol, 
      valoare, data_notare FROM studenti s join 
      note n on n.id_student=s.id', DBMS_SQL.NATIVE);
      v_ok := DBMS_SQL.EXECUTE(v_cursor_id );
      DBMS_SQL.DESCRIBE_COLUMNS(v_cursor_id, v_total_coloane, v_rec_tab);
    
      v_nr_col := v_rec_tab.first;
      IF (v_nr_col IS NOT NULL) THEN
        LOOP
            IF (v_first) THEN
                v_create_string := v_create_string || v_rec_tab(v_nr_col).col_name || ' ' || gettype(v_rec_tab,v_nr_col);
                v_nr_col := v_rec_tab.next(v_nr_col);
                v_first := false;
            ELSE
                v_create_string := v_create_string || ', ' || v_rec_tab(v_nr_col).col_name || ' ' || gettype(v_rec_tab,v_nr_col);
                v_nr_col := v_rec_tab.next(v_nr_col);
            END IF;
          EXIT WHEN (v_nr_col IS NULL);
        END LOOP;
      END IF;
      DBMS_SQL.CLOSE_CURSOR(v_cursor_id);
      
      v_create_string := v_create_string || ')';
      
      v_cursor_id := DBMS_SQL.OPEN_CURSOR;
      DBMS_SQL.PARSE(v_cursor_id, 'SELECT COUNT(*) FROM user_tables WHERE table_name=upper(''' || v_titlu_curs || ''')', DBMS_SQL.NATIVE);
      DBMS_SQL.DEFINE_COLUMN(v_cursor_id, 1, v_table_exists); 
      v_ok := DBMS_SQL.EXECUTE(v_cursor_id);
      
      IF DBMS_SQL.FETCH_ROWS(v_cursor_id)>0 THEN
          DBMS_SQL.COLUMN_VALUE(v_cursor_id, 1, v_table_exists); 
          DBMS_SQL.CLOSE_CURSOR(v_cursor_id);
          IF (v_table_exists >= 1) THEN
              v_cursor_id := DBMS_SQL.OPEN_CURSOR;
              DBMS_SQL.PARSE(v_cursor_id, 'DROP TABLE ' || v_titlu_curs, DBMS_SQL.NATIVE);
              v_ok := DBMS_SQL.EXECUTE(v_cursor_id);
              DBMS_SQL.CLOSE_CURSOR(v_cursor_id);
          END IF;
      ELSE
          DBMS_SQL.CLOSE_CURSOR(v_cursor_id);
      END IF;
      
      v_cursor_id := DBMS_SQL.OPEN_CURSOR;
      DBMS_SQL.PARSE(v_cursor_id, v_create_string, DBMS_SQL.NATIVE);
      v_ok := DBMS_SQL.EXECUTE(v_cursor_id);
      DBMS_SQL.CLOSE_CURSOR(v_cursor_id);
      
      v_cursor_id := DBMS_SQL.OPEN_CURSOR;
      DBMS_SQL.PARSE(v_cursor_id , 'INSERT INTO ' || v_titlu_curs ||
      ' (nume, prenume, nr_matricol, valoare, data_notare) SELECT 
      s.nume, s.prenume, s.nr_matricol, n.valoare, n.data_notare 
      FROM studenti s join note n on n.id_student = s.id where 
      n.id_curs = ' || id_materie, DBMS_SQL.NATIVE);   
      v_ok := DBMS_SQL.EXECUTE(v_cursor_id);
      DBMS_SQL.CLOSE_CURSOR(v_cursor_id);
      
   ELSE
      DBMS_OUTPUT.PUT_LINE('Materia nu exista');
      DBMS_SQL.CLOSE_CURSOR(v_cursor_id);
   END IF;
end;
/

set serveroutput on;

begin
    generare_catalog(4);
end;