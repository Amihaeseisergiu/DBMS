DROP TABLE masini CASCADE CONSTRAINTS
/

CREATE TABLE masini (
  id INT NOT NULL PRIMARY KEY,
  id_student INT NOT NULL,
  marca VARCHAR(50),
  model VARCHAR(50),
  culoare VARCHAR(20),
  combustibil VARCHAR(20),
  nr_usi NUMBER(2),
  cutie_viteze CHAR(1),
  capacitate_cilindrica FLOAT(20),
  an_fabricatie DATE,
  created_at DATE,
  updated_at DATE,
  
  CONSTRAINT fk_masini_id_student FOREIGN KEY (id_student) REFERENCES studenti(id)
)
/

SET SERVEROUTPUT ON;
DECLARE
	TYPE var_varchar IS VARRAY(1000) OF VARCHAR2(50);
	TYPE var_number  IS VARRAY(1000) OF NUMBER(2);
	TYPE var_char    IS VARRAY(1000) OF CHAR(1);
	TYPE var_float   IS VARRAY(1000) OF FLOAT(20);
	TYPE var_date    IS VARRAY(1000) OF DATE;
	lista_marca var_varchar := var_varchar('alfa romeo', 'audi', 'bmw', 'chevrolet', 'citroen', 'dacia', 'fiat', 'ford', 'mercedes', 'nissan', 'renault', 'skoda', 'volkswagen');
	lista_model_alfa var_varchar := var_varchar('stelvio', 'mito', 'alfetta', 'giulia', 'giulietta', 'brera', 'alfasud', '8C competizione', '4C spider','33 giardinetta','155', '156 sportwagon', '145', '146', '147');
	lista_model_audi var_varchar := var_varchar('100 avant', 'A1', 'A2', 'A3', 'A4', 'A5', 'A6', 'A7', 'A8', 'Q2', 'Q3', 'Q5', 'Q7', 'Q8' , 'R8', 'RS3', 'RS4', 'RS5', 'RS6', 'S1', 'S2', 'S3', 'S4', 'S5', 'S6', 'S7', 'TT');
	lista_model_bmw var_varchar := var_varchar('1-serie', '2-serie', '3-serie', '4-serie', '5-serie', '6-serie', '7-serie', '8-serie', 'X1', 'X2', 'X3', 'X4', 'X5', 'X6', 'i8', 'Z1', 'Z2', 'Z3', 'Z4', 'Z5', 'Z6', 'Z7', 'Z8');
	lista_model_chevrolet var_varchar := var_varchar('alero', 'aveo', 'beretta', 'blazer', 'caprice', 'camaro', 'captiva', 'corsica', 'corvette', 'cruze', 'evada', 'epica', 'HHR', 'kalos', 'lacetti', 'lumina', 'matiz', 'nubira', 'orlando');
	lista_model_citroen var_varchar := var_varchar('2CV', 'AX', 'Axel', 'Berlingo', 'BX', 'C-crosser', 'C-zero', 'C1', 'C2', 'C3', 'C4', 'C5', 'C6', 'C7', 'C8', 'CX', 'DS1', 'DS2', 'DS3', 'DS4', 'DS5', 'Evasion', 'Dyane', 'Jumpy', 'Saxo','Nemo');
	lista_model_dacia var_varchar := var_varchar('dokker', 'duster', 'lodgy', 'logan', 'sandero', 'logan MCV');
	lista_model_fiat var_varchar := var_varchar('124', '126', '127', '500', 'argenta', 'barchetta', 'brava', 'bravo', 'cinquecento', 'coupe', 'croma', 'doblo', 'combinato', 'panda', 'multipla', 'marea', 'idea', 'punto', 'evo', 'tipo');
	lista_model_ford var_varchar := var_varchar('B-max', 'C-max', 'capri', 'cougar', 'edge', 'escord', 'cabrio', 'explorer', 'expedition', 'focus', 'mustang', 'fusion', 'galaxy', 'kuga', 'ka', 'mondeo', 'probe', 'scorpio', 'puma', 'sierra');
	lista_model_mercedes var_varchar := var_varchar('190', '200', 'A-class', 'AMG', 'B-class', 'C-class', 'citan', 'CL', 'CLA', 'CLC', 'E-class', 'CLK', 'G-class', 'GL', 'GLE', 'GLS', 'S-class', 'SLR', 'SL', 'Sprinter', 'Vaneo', 'Viano', 'V-class');
	lista_model_nissan var_varchar := var_varchar('100', '200', 'Almera', 'Roadster', 'tino', 'bluebird', 'murano', 'patrol', 'pathfinder', 'micra', 'maxima', 'sunny', 'primera', 'pulsar', 'qashqai', 'terrano');
	lista_model_renault var_varchar := var_varchar('captur', 'clio', 'avantime', 'fluence', 'estate', 'espace', 'grand modus', 'kangoo', 'fuego', 'kadjar', 'laguna', 'megane', 'scenic', 'wind', 'zoe', 'talisman', 'spider', 'scenic', 'safrane', 'nevada');
	lista_model_skoda var_varchar := var_varchar('citigo', 'fabia', 'favorit', 'forman', 'felicia', 'karog', 'octavia', 'kodiaq', 'combi', 'scala', 'superb', 'yeti', 'rapid', 'roomster');
	lista_model_volkswagen var_varchar := var_varchar('beetle', 'bora', 'corrado', 'caddy', 'crosspolo', 'derby', 'golf', 'eos', 'fox', 'multivan', 'passat', 'lupo', 'polo', 'touareg', 'tiguan', 'touran', 'vento', 'up');
	lista_culoare var_varchar := var_varchar('alb', 'albastru', 'argintiu', 'auriu', 'bej', 'cenusiu', 'galben' , 'gri', 'indigo', 'kaki', 'magenta', 'maro', 'mov', 'negru', 'oranj', 'rosu', 'roz', 'verde', 'purpuriu');
	lista_combustibil var_varchar := var_varchar('benzina', 'metanol', 'etanol', 'motorina', 'gaz lichid', 'gaz comprimat', 'electric');
	lista_nr_usi var_number := var_number(1, 2, 3, 4, 5, 6, 7, 8);
	lista_cutie_viteze var_char := var_char('A', 'M');
	lista_capacitate_cilindrica var_float := var_float(1.0, 1.2, 1.4, 1.6, 1.8, 2.0, 2.2, 2.4, 2.6, 2.8, 3.0, 3.2, 3.4, 3.6, 3.8, 4.0, 4.2, 4.4, 4.6, 4.8, 5.0);
	
	v_marca VARCHAR(50);
	v_model VARCHAR(50);
	v_culoare VARCHAR(20);
	v_combustibil VARCHAR(20);
	v_nr_usi NUMBER(2);
	v_cutie_viteze CHAR(1);
	v_capacitate_cilindrica FLOAT(20);
	v_an_fabricatie DATE;
    v_id_masina NUMBER := 1;
BEGIN

	DBMS_OUTPUT.PUT_LINE('Inserarea a 300 masini...');
	
	FOR v_i IN (SELECT ID FROM (SELECT id FROM studenti ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM < 301) LOOP
	
		v_marca := lista_marca(TRUNC(DBMS_RANDOM.VALUE(0,lista_marca.count))+1);
		
		IF v_marca = 'alfa romeo' THEN v_model := lista_model_alfa(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_alfa.count))+1);
		ELSIF v_marca = 'audi' THEN v_model := lista_model_audi(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_audi.count))+1);
		ELSIF v_marca = 'bmw' THEN v_model := lista_model_bmw(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_bmw.count))+1);
		ELSIF v_marca = 'chevrolet' THEN v_model := lista_model_chevrolet(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_chevrolet.count))+1);
		ELSIF v_marca = 'citroen' THEN v_model := lista_model_citroen(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_citroen.count))+1);
		ELSIF v_marca = 'dacia' THEN v_model := lista_model_dacia(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_dacia.count))+1);
		ELSIF v_marca = 'fiat' THEN v_model := lista_model_fiat(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_fiat.count))+1);
		ELSIF v_marca = 'ford' THEN v_model := lista_model_ford(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_ford.count))+1);
		ELSIF v_marca = 'mercedes' THEN v_model := lista_model_mercedes(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_mercedes.count))+1);
		ELSIF v_marca = 'nissan' THEN v_model := lista_model_nissan(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_nissan.count))+1);
		ELSIF v_marca = 'renault' THEN v_model := lista_model_renault(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_renault.count))+1);
		ELSIF v_marca = 'skoda' THEN v_model := lista_model_skoda(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_skoda.count))+1);
		ELSE v_model := lista_model_volkswagen(TRUNC(DBMS_RANDOM.VALUE(0,lista_model_volkswagen.count))+1);
		END IF;
		
		v_culoare := lista_culoare(TRUNC(DBMS_RANDOM.VALUE(0,lista_culoare.count))+1);
		v_combustibil := lista_combustibil(TRUNC(DBMS_RANDOM.VALUE(0,lista_combustibil.count))+1);
		v_nr_usi := lista_nr_usi(TRUNC(DBMS_RANDOM.VALUE(0,lista_nr_usi.count))+1);
		v_cutie_viteze := lista_cutie_viteze(TRUNC(DBMS_RANDOM.VALUE(0,lista_cutie_viteze.count))+1);
		v_capacitate_cilindrica := lista_capacitate_cilindrica(TRUNC(DBMS_RANDOM.VALUE(0,lista_capacitate_cilindrica.count))+1);
		
        SELECT TO_DATE(TRUNC(DBMS_RANDOM.VALUE(TO_CHAR(DATE '1999-01-01','J'),TO_CHAR(sysdate, 'J'))),'J') into v_an_fabricatie FROM DUAL;
        
		insert into masini values(v_id_masina, v_i.id, v_marca, v_model, v_culoare, v_combustibil, v_nr_usi, v_cutie_viteze, v_capacitate_cilindrica, v_an_fabricatie, sysdate, sysdate);
        v_id_masina := v_id_masina + 1;
    
	END LOOP;
	
	DBMS_OUTPUT.PUT_LINE('Gata!');
END;
