--Amihaesei Sergiu, Grupa A3, Anul 2

set serveroutput on;
DECLARE
  v_fisier UTL_FILE.FILE_TYPE;
  v_line varchar2(32767);
BEGIN
  v_fisier:=UTL_FILE.FOPEN('MYDIR','note.csv','W');
  FOR x IN (SELECT * FROM NOTE) LOOP
    v_line := x.id || ',' || x.id_student || ',' || x.id_curs || ',' || x.valoare || ',' || x.data_notare || ',' || x.created_at || ',' || x.updated_at;
    UTL_FILE.PUT_LINE(v_fisier, v_line);
  END LOOP;
  UTL_FILE.FCLOSE(v_fisier);
END;
/

delete from note;
/

set serveroutput on;
DECLARE
    v_fisier UTL_FILE.FILE_TYPE;
    v_line varchar2(32767);
    type v_array is varray(10) of varchar2(1000);
    v_coloane v_array;
BEGIN
    v_fisier:=UTL_FILE.FOPEN('MYDIR','note.csv','R');
    LOOP BEGIN
        UTL_FILE.GET_LINE(v_fisier, v_line);
        
        v_coloane := v_array();
        FOR y IN (select regexp_substr(v_line ,'[^,]+', 1, level) as name from dual connect by regexp_substr(v_line, '[^,]+', 1, level) is not null) LOOP
            v_coloane.extend(1);
            v_coloane(v_coloane.count) := y.name;
        END LOOP;
        
        INSERT INTO note VALUES (v_coloane(1), v_coloane(2), v_coloane(3), v_coloane(4), v_coloane(5), v_coloane(6), v_coloane(7));
        
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            EXIT;   
        END;
    END LOOP;
    
  UTL_FILE.FCLOSE(v_fisier);
END;
/