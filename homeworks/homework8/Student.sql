DECLARE
  v_fisier UTL_FILE.FILE_TYPE;
BEGIN
  v_fisier:=UTL_FILE.FOPEN('MYDIR','tema8.xml','W');
  UTL_FILE.PUT_LINE(v_fisier,'<?xml version="1.0"?>');
  UTL_FILE.PUT_LINE(v_fisier,'<ROWSET>');
  
  FOR x IN (SELECT s.id as id, s.nr_matricol, s.nume, s.prenume, s.an as an, s.grupa, s.bursa, s.data_nastere,
  s.email, s.created_at as created_at, s.updated_at as updated_at, n.id as id_1, n.id_student, n.id_curs, n.valoare, n.data_notare,
  n.created_at as created_at_1, n.updated_at as updated_at_1, c.id as id_2, c.titlu_curs, c.an as an_1, c.semestru, c.credite,
  c.created_at as created_at_2, c.updated_at as updated_at_2
  FROM studenti s JOIN note n ON s.id = n.id_student JOIN cursuri c ON c.id = n.id_curs) LOOP
    UTL_FILE.PUT_LINE(v_fisier,'<ROW>');
    
    UTL_FILE.PUT_LINE(v_fisier,'<ID>' || x.id || '</ID>');
    UTL_FILE.PUT_LINE(v_fisier,'<NR_MATRICOL>' || x.nr_matricol || '</NR_MATRICOL>');
    UTL_FILE.PUT_LINE(v_fisier,'<NUME>' || x.nume || '</NUME>');
    UTL_FILE.PUT_LINE(v_fisier,'<PRENUME>' || x.prenume || '</PRENUME>');
    UTL_FILE.PUT_LINE(v_fisier,'<AN>' || x.an || '</AN>');
    UTL_FILE.PUT_LINE(v_fisier,'<GRUPA>' || x.grupa || '</GRUPA>');
    UTL_FILE.PUT_LINE(v_fisier,'<BURSA>' || x.bursa || '</BURSA>');
    UTL_FILE.PUT_LINE(v_fisier,'<DATA_NASTERE>' || x.data_nastere || '</DATA_NASTERE>');
    UTL_FILE.PUT_LINE(v_fisier,'<EMAIL>' || x.email || '</EMAIL>');
    UTL_FILE.PUT_LINE(v_fisier,'<CREATED_AT>' || x.created_at || '</CREATED_AT>');
    UTL_FILE.PUT_LINE(v_fisier,'<UPDATED_AT>' || x.updated_at || '</UPDATED_AT>');
    UTL_FILE.PUT_LINE(v_fisier,'<ID_1>' || x.id_1 || '</ID_1>');
    UTL_FILE.PUT_LINE(v_fisier,'<ID_STUDENT>' || x.id_student || '</ID_STUDENT>');
    UTL_FILE.PUT_LINE(v_fisier,'<ID_CURS>' || x.id_curs || '</ID_CURS>');
    UTL_FILE.PUT_LINE(v_fisier,'<VALOARE>' || x.valoare || '</VALOARE>');
    UTL_FILE.PUT_LINE(v_fisier,'<DATA_NOTARE>' || x.data_notare || '</DATA_NOTARE>');
    UTL_FILE.PUT_LINE(v_fisier,'<CREATED_AT_1>' || x.created_at_1 || '</CREATED_AT_1>');
    UTL_FILE.PUT_LINE(v_fisier,'<UPDATED_AT_1>' || x.updated_at_1 || '</UPDATED_AT_1>');
    UTL_FILE.PUT_LINE(v_fisier,'<ID_2>' || x.id_2 || '</ID_2>');
    UTL_FILE.PUT_LINE(v_fisier,'<TITLU_CURS>' || x.titlu_curs || '</TITLU_CURS>');
    UTL_FILE.PUT_LINE(v_fisier,'<AN_1>' || x.an_1 || '</AN_1>');
    UTL_FILE.PUT_LINE(v_fisier,'<SEMESTRU>' || x.semestru || '</SEMESTRU>');
    UTL_FILE.PUT_LINE(v_fisier,'<CREDITE>' || x.credite || '</CREDITE>');
    UTL_FILE.PUT_LINE(v_fisier,'<CREATED_AT_2>' || x.created_at_2 || '</CREATED_AT_2>');
    UTL_FILE.PUT_LINE(v_fisier,'<UPDATED_AT_2>' || x.updated_at_2 || '</UPDATED_AT_2>');
    
    UTL_FILE.PUT_LINE(v_fisier,'</ROW>');
  END LOOP;
  
  UTL_FILE.PUT_LINE(v_fisier,'</ROWSET>');
  UTL_FILE.FCLOSE(v_fisier);
END;
/