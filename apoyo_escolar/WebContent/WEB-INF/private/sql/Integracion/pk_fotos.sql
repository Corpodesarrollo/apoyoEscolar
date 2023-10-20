CREATE OR REPLACE PROCEDURE extract_file() IS
vblob BLOB;
vstart NUMBER := 1;
bytelen NUMBER := 32000;
len NUMBER;
my_vr RAW(32000);
x NUMBER;
l_output utl_file.file_type;
BEGIN
-- define output directory
l_output := utl_file.fopen('oraload', 'filename.jpg','wb', 32760);

vstart := 1;
bytelen := 32000;

-- get length of blob
SELECT dbms_lob.getlength(ESTFOTO)
INTO len
FROM LOG_ESTUD_2008
WHERE ESTCODIGO = 942798;

-- save blob length
x := len;

-- select blob into variable
SELECT ESTFOTO
INTO vblob
FROM LOG_ESTUD_2008
WHERE ESTCODIGO = 942798;

-- if small enough for a single write
IF len < 32760 THEN
utl_file.put_raw(l_output,vblob);
utl_file.fflush(l_output);
ELSE -- write in pieces
vstart := 1;
WHILE vstart < len AND bytelen > 0
LOOP
   dbms_lob.READ(vblob,bytelen,vstart,my_vr);

   utl_file.put_raw(l_output,my_vr);
   utl_file.fflush(l_output); 

   -- set the start position for the next cut
   vstart := vstart + bytelen;

   -- set the end position if less than 32000 bytes
   x := x - bytelen;
   IF x < 32000 THEN
      bytelen := x;
   END IF;
END IF;
utl_file.fclose(l_output);
END LOOP;