create or replace package laborator3 as 
    procedure cmmdc_procedure (p_a in number, p_b in number, p_rezultat out number);
    procedure cmmmc_procedure (p_a in number, p_b in number, p_rezultat out number);
    function cmmdc (p_a in number, p_b in number) return number;
    function cmmmc (p_a in number, p_b in number) return number;
end laborator3;

create or replace package body laborator3 is
procedure cmmdc_procedure (p_a in number, p_b in number, p_rezultat out number)
as p_a_copie number := p_a; p_b_copie number := p_b;
begin
        WHILE p_a_copie <> p_b_copie LOOP
            IF p_a_copie > p_b_copie THEN
                p_a_copie := p_a_copie - p_b_copie;
            ELSE p_b_copie := p_b_copie - p_a_copie;
            END IF;
        END LOOP;
        p_rezultat := p_a_copie;
end cmmdc_procedure;

procedure cmmmc_procedure(p_a in number, p_b in number, p_rezultat out number)
as p_a_copie number := p_a; p_b_copie number := p_b; p_cmmdc number;
begin
    cmmdc_procedure(p_a_copie, p_b_copie, p_cmmdc);
    p_rezultat := (p_a_copie*p_b_copie)/p_cmmdc;
end cmmmc_procedure;

function cmmdc (p_a in number, p_b in number) return number
as p_rezultat number;
begin
    cmmdc_procedure(p_a, p_b, p_rezultat);
    return p_rezultat;
end cmmdc;

function cmmmc (p_a in number, p_b in number) return number
as p_rezultat number;
begin
    cmmmc_procedure(p_a, p_b, p_rezultat);
    return p_rezultat;
end cmmmc;

end laborator3;