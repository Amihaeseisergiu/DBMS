drop table polygons;
/

drop type square;
/

drop type polygon;
/

create or replace type polygon as object
(
    nr_sides number,
    color varchar2(20),
    not final member function get_shape return varchar2,
    member function get_dimension return varchar2,
    map member function get_sides return number,
    constructor function polygon(nr_sides number) return self as result
) not final;
/

create or replace type body polygon as
    member function get_shape return varchar2 as
    v_shape varchar2(20);
    begin
        case 
            when self.nr_sides = 1 then v_shape := 'monogon';
            when self.nr_sides = 2 then v_shape := 'diagon';
            when self.nr_sides = 3 then v_shape := 'triangle';
            when self.nr_sides = 4 then v_shape := 'rectangle';
            when self.nr_sides = 5 then v_shape := 'pentagon';
            when self.nr_sides = 6 then v_shape := 'hexagon';
            when self.nr_sides = 7 then v_shape := 'heptagon';
            when self.nr_sides = 8 then v_shape := 'octagon';
            else v_shape := 'polygon';
        end case;
        
        return v_shape;
    end get_shape;
    
    member function get_dimension return varchar2 as
    begin
        return '2D';
    end get_dimension;
    
    map member function get_sides return number as
    begin
        return self.nr_sides;
    end get_sides;
    
    constructor function polygon(nr_sides number) return self as result as
    begin
        self.nr_sides := nr_sides;
        self.color := 'black';
        return;
    end;
end;
/

create or replace type square under polygon
(
    line_size number,
    member function get_area return varchar2,
    overriding member function get_shape return varchar2,
    constructor function square(line_size number) return self as result
);
/

create or replace type body square as
    member function get_area return varchar2 as
    begin
        return self.line_size*self.line_size;
    end get_area;
    
    overriding member function get_shape return varchar2 as
    begin
        return 'square';
    end get_shape;
    
    constructor function square(line_size number) return self as result as
    begin
        self.nr_sides := 4;
        self.color := 'black';
        self.line_size := line_size;
        return;    
    end;
end;
/

create table polygons(id number, obiect polygon);
/

set serveroutput on;
declare
    v_polygon1 polygon;
    v_polygon2 polygon;
    v_polygon3 polygon;
    v_square square;
begin
    v_polygon1 := polygon(3);
    v_polygon2 := polygon(5);
    v_polygon3 := polygon(6);
    v_square := square(3);
    dbms_output.put_line(v_polygon1.get_shape);
    dbms_output.put_line(v_square.get_area);
    
    insert into polygons values(1, v_polygon2);
    insert into polygons values(2, v_polygon1);
    insert into polygons values(3, v_polygon3);
    insert into polygons values(4, v_square); 
end;
/

select * from polygons order by obiect;
/


