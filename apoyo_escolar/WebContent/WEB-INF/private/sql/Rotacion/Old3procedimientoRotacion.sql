CREATE OR REPLACE PACKAGE PK_ROTACION AS
	PROCEDURE getAsignatura(id in NUMERIC,inst in NUMERIC,sede in NUMERIC,jor in NUMERIC,met in NUMERIC,grado in NUMERIC,vigencia in NUMERIC,horas in numeric);
	PROCEDURE getAsignaturaActual(id in NUMERIC,inst in NUMERIC,sede in NUMERIC,jor in NUMERIC,met in NUMERIC,grado in NUMERIC,vigencia in NUMERIC,horas in numeric,asig in numeric);
	PROCEDURE getDocEf(id in NUMERIC,inst in NUMERIC,sede in NUMERIC,jor in NUMERIC,met in NUMERIC,grado in NUMERIC,vigencia in NUMERIC,asignatura in numeric,docente in numeric,espacio in numeric,ih in numeric);
END PK_ROTACION;
/

CREATE OR REPLACE PACKAGE BODY PK_ROTACION AS
  PROCEDURE getAsignatura(id in NUMERIC,inst in NUMERIC,sede in NUMERIC,jor in NUMERIC,met in NUMERIC,grado in NUMERIC,vigencia in NUMERIC,horas in numeric) IS
	asig number(6);
	ih number(3);
	cat number(2);
	doc number(12);
	esp number(12);
	jerGra number(12);
	jerJor number(12);
	cont number(2);
	i number(3);
	bloque number(3);	
	--Traer todos los grados asignatura
	CURSOR cursor_asig IS
			SELECT GRAASICODASIGN as asig_ ,GRAASIIH as ih_ 
			FROM GRADO_ASIGNATURA,ASIGNATURA
			where ASICODIGO=GRAASICODASIGN
			and GRAASICODINST=inst
			and ASICODINST=GRAASICODINST
			and GRAASICODMETOD=met 
			and ASICODMETOD=GRAASICODMETOD
			and GRAASICODGRADO=grado
			and GRAASIVIGENCIA=vigencia
			and ASIVIGENCIA=GRAASIVIGENCIA
			and GRAASIIH>0
			order by ASIPRIORIDAD desc,GRAASIIH desc,GRAASICODASIGN asc;
  BEGIN
  	doc:=0;
	esp:=0;
	cont:=0;
	i:=0;
	delete from ROT_ASIG_TEMP where ID=id;
	--traer jer 1- 7	
	SELECT nvl(max(G_JERCODIGO),0) into jerGra
		FROM G_JERARQUIA
		where G_JERTIPO=1
		and G_JERNIVEL=7
		and G_JERINST=inst
		and G_JERSEDE=sede
		and G_JERJORN=jor
		and G_JERMETOD=met
		and G_JERGRADO=grado;
	--traer jer 1- 8	
	SELECT nvl(max(G_JERCODIGO),0) into jerJor
		FROM G_JERARQUIA
		where G_JERTIPO=1
		and G_JERNIVEL=6
		and G_JERINST=inst
		and G_JERSEDE=sede
		and G_JERJORN=jor;
		
	FOR rec in cursor_asig LOOP	
	  	doc:=0;
		esp:=0;
		cont:=0;
		cat:=0;
		bloque:=0;
		--calcula categoria 
		if rec.ih_<=0 or rec.ih_=1 then
			cat:=0;
		else
			cat:=round(rec.ih_/horas);			
		end if;
		if rec.ih_<=0 then
			rec.ih_:=1;
		end if;
		--calcula docente		 
			SELECT count(ROTDAGDOCENTE) into cont
			from ROT_DOC_ASIG_GRADO
			where ROTDAGJERGRADO=jerGra 
			and ROTDAGASIGNATURA=rec.asig_
			and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=rec.ih_;
			if cont=0 then
				doc:=0;
			else
				if cont=1 then				   
					SELECT ROTDAGDOCENTE into doc
					from ROT_DOC_ASIG_GRADO
					where ROTDAGJERGRADO=jerGra 
					and ROTDAGASIGNATURA=rec.asig_
					and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=rec.ih_;
				else
					doc:=-1;
				end if;
			end if;
			--calcular espacio   
			cont:=0;
			if doc>0 then		
				SELECT count(RTEFDOESPACIO) into cont
				FROM ROT_ESPACIO_DOCENTE
				where RTEFDODOCENTE=doc
				and RTEFDOJERCODIGO=jerJor;
				if cont=0 then
				   esp:=0;
				else
					if cont=1 then
						SELECT nvl(max(RTEFDOESPACIO),0) into esp
						FROM ROT_ESPACIO_DOCENTE
						where RTEFDODOCENTE=doc
						and RTEFDOJERCODIGO=jerJor;
					else						
						esp:=-1;
					end if;
				end if;	
			end if;
			if esp=0 then
				SELECT count(ROTEAGESPACIO) into cont
				FROM ROT_ESPACIO_ASIG_GRADO
				where ROTEAGASIGNATURA=rec.asig_ 
				and ROTEAGJERGRADO=grado
				and ROTEAGJERJORNADA=jerJor;
				if cont=0 then
					esp:=0;
				else
					if cont=1 then
						SELECT max(ROTEAGESPACIO) into esp
						FROM ROT_ESPACIO_ASIG_GRADO
						where ROTEAGASIGNATURA=rec.asig_ 
						and ROTEAGJERGRADO=grado
						and ROTEAGJERJORNADA=jerJor;					
					else
						esp:=-1;
					end if;					
				end if;			
			end if;
		--buscar el bloque minimo 
		SELECT count(ROTFASBLQMINIMO) into bloque
		from ROT_ESTRUCTURA_GRADO,ROT_ESTRUCTURA,ROT_FIJAR_ASIGNATURA
		where RTESGR_STRCOD=ROTSTRCODIGO
		and ROTFASESTRUCTURA=ROTSTRCODIGO
		and RTESGRGRADO=ROTFASGRADO
		and ROTFASASIGNATURA=rec.asig_
		and RTESGRGRADO=grado
		and ROTSTRJERCODIGO=jerJor;
		if bloque>0 then
			SELECT nvl(ROTFASBLQMINIMO,0) into bloque
			from ROT_ESTRUCTURA_GRADO,ROT_ESTRUCTURA,ROT_FIJAR_ASIGNATURA
			where RTESGR_STRCOD=ROTSTRCODIGO
			and ROTFASESTRUCTURA=ROTSTRCODIGO
			and RTESGRGRADO=ROTFASGRADO
			and ROTFASASIGNATURA=rec.asig_
			and RTESGRGRADO=grado
			and ROTSTRJERCODIGO=jerJor;
		end if;	
		--insertar asignaturas
		INSERT INTO ROT_ASIG_TEMP (ID, ASIGNATURA, CATEGORIA, DOCENTE, ESPACIO,IH,ORDEN,BLOQUE) VALUES (id,rec.asig_,cat,doc,esp,rec.ih_,i,bloque);
		i:=i+1;		
	END LOOP;
	commit;
	EXCEPTION
	WHEN others THEN
		close cursor_asig;
		RAISE_APPLICATION_ERROR (500, 'Error interno getAsignatura');
	END getAsignatura;

  PROCEDURE getAsignaturaActual(id in NUMERIC,inst in NUMERIC,sede in NUMERIC,jor in NUMERIC,met in NUMERIC,grado in NUMERIC,vigencia in NUMERIC,horas in numeric,asig in numeric) IS
	ih number(3);
	cat number(2);
	doc number(12);
	esp number(12);
	jerGra number(12);
	jerJor number(12);
	cont number(2);
	i number(3);
	bloque number(3);	
	--Traer todos los grados asignatura
	CURSOR cursor_asig IS
		SELECT GRAASICODASIGN as asig_ ,GRAASIIH as ih_ 
		FROM GRADO_ASIGNATURA,ASIGNATURA
		where ASICODIGO=GRAASICODASIGN
		and GRAASICODINST=inst
		and ASICODINST=GRAASICODINST
		and GRAASICODMETOD=met 
		and ASICODMETOD=GRAASICODMETOD
		and GRAASICODGRADO=grado
		and GRAASICODASIGN=asig
		and GRAASIVIGENCIA=vigencia
		and ASIVIGENCIA=GRAASIVIGENCIA
		and GRAASIIH>0
		order by ASIPRIORIDAD desc,GRAASIIH desc,GRAASICODASIGN asc;
		--odenar por prioridad  desendente y luego ordenar por ih  
  BEGIN
  	doc:=0;
	esp:=0;
	cont:=0;
	i:=0;
	delete from ROT_ASIG_TEMP where ID=id;
	--traer jer 1- 7	
	SELECT nvl(max(G_JERCODIGO),0) into jerGra
		FROM G_JERARQUIA
		where G_JERTIPO=1
		and G_JERNIVEL=7
		and G_JERINST=inst
		and G_JERSEDE=sede
		and G_JERJORN=jor
		and G_JERMETOD=met
		and G_JERGRADO=grado;
	--traer jer 1- 8	
	SELECT nvl(max(G_JERCODIGO),0) into jerJor
		FROM G_JERARQUIA
		where G_JERTIPO=1
		and G_JERNIVEL=6
		and G_JERINST=inst
		and G_JERSEDE=sede
		and G_JERJORN=jor;
		
	FOR rec in cursor_asig LOOP	
	  	doc:=0;
		esp:=0;
		cont:=0;
		cat:=0;
		bloque:=0;
		--calcula categoria 
		if rec.ih_<=0 or rec.ih_=1 then
			cat:=0;
		else
			cat:=round(rec.ih_/horas);			
		end if;
		if rec.ih_<=0 then
			rec.ih_:=1;
		end if;
		--calcula docente		 
			SELECT count(ROTDAGDOCENTE) into cont
			from ROT_DOC_ASIG_GRADO
			where ROTDAGJERGRADO=jerGra 
			and ROTDAGASIGNATURA=rec.asig_
			and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=rec.ih_;
			if cont=0 then
				doc:=0;
			else
				if cont=1 then				   
					SELECT ROTDAGDOCENTE into doc
					from ROT_DOC_ASIG_GRADO
					where ROTDAGJERGRADO=jerGra 
					and ROTDAGASIGNATURA=rec.asig_
					and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=rec.ih_;
				else
					doc:=-1;
				end if;
			end if;
			--calcular espacio   
			cont:=0;
			if doc>0 then		
				SELECT count(RTEFDOESPACIO) into cont
				FROM ROT_ESPACIO_DOCENTE
				where RTEFDODOCENTE=doc
				and RTEFDOJERCODIGO=jerJor;
				if cont=0 then
				   esp:=0;
				else
					if cont=1 then
						SELECT nvl(max(RTEFDOESPACIO),0) into esp
						FROM ROT_ESPACIO_DOCENTE
						where RTEFDODOCENTE=doc
						and RTEFDOJERCODIGO=jerJor;
					else						
						esp:=-1;
					end if;
				end if;	
			end if;
			if esp=0 then
				SELECT count(ROTEAGESPACIO) into cont
				FROM ROT_ESPACIO_ASIG_GRADO
				where ROTEAGASIGNATURA=rec.asig_ 
				and ROTEAGJERGRADO=grado
				and ROTEAGJERJORNADA=jerJor;
				if cont=0 then
					esp:=0;
				else
					if cont=1 then
						SELECT max(ROTEAGESPACIO) into esp
						FROM ROT_ESPACIO_ASIG_GRADO
						where ROTEAGASIGNATURA=rec.asig_ 
						and ROTEAGJERGRADO=grado
						and ROTEAGJERJORNADA=jerJor;					
					else
						esp:=-1;
					end if;					
				end if;			
			end if;
		--buscar el bloque minimo 
		SELECT count(ROTFASBLQMINIMO) into bloque
		from ROT_ESTRUCTURA_GRADO,ROT_ESTRUCTURA,ROT_FIJAR_ASIGNATURA
		where RTESGR_STRCOD=ROTSTRCODIGO
		and ROTFASESTRUCTURA=ROTSTRCODIGO
		and RTESGRGRADO=ROTFASGRADO
		and ROTFASASIGNATURA=rec.asig_
		and RTESGRGRADO=grado
		and ROTSTRJERCODIGO=jerJor;
		if bloque>0 then
			SELECT nvl(ROTFASBLQMINIMO,0) into bloque
			from ROT_ESTRUCTURA_GRADO,ROT_ESTRUCTURA,ROT_FIJAR_ASIGNATURA
			where RTESGR_STRCOD=ROTSTRCODIGO
			and ROTFASESTRUCTURA=ROTSTRCODIGO
			and RTESGRGRADO=ROTFASGRADO
			and ROTFASASIGNATURA=rec.asig_
			and RTESGRGRADO=grado
			and ROTSTRJERCODIGO=jerJor;
		end if;	
		--insertar asignaturas
		INSERT INTO ROT_ASIG_TEMP (ID, ASIGNATURA, CATEGORIA, DOCENTE, ESPACIO,IH,ORDEN,BLOQUE) VALUES (id,rec.asig_,cat,doc,esp,rec.ih_,i,bloque);
		i:=i+1;		
	END LOOP;
	commit;
	EXCEPTION
	WHEN others THEN     
		close cursor_asig;
		RAISE_APPLICATION_ERROR (500, 'Error interno getAsignaturaActual');
	END getAsignaturaActual;

  PROCEDURE getDocEf(id in NUMERIC,inst in NUMERIC,sede in NUMERIC,jor in NUMERIC,met in NUMERIC,grado in NUMERIC,vigencia in NUMERIC,asignatura in numeric,docente in numeric,espacio in numeric,ih in numeric) IS
	jerGra number(12);
	jerJor number(12);
	doc number(12);
	esp number(12);
	cont number(2);
	band number(2);
	band2 number(2);
	tipo number(2);
	espacios number(2);
	i number(3);
	--carga academica de docente
	CURSOR cursor_doc IS
		SELECT ROTDAGDOCENTE as docente_
		from ROT_DOC_ASIG_GRADO
		where ROTDAGJERGRADO=jerGra 
		and ROTDAGASIGNATURA=asignatura
		and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=ih;
		--espacios asignados a los docentes
	CURSOR cursor_esp IS
		SELECT RTEFDOESPACIO as espacio_
		FROM ROT_ESPACIO_DOCENTE,ROT_DOC_ASIG_GRADO
		where RTEFDODOCENTE=docente
		and ROTDAGDOCENTE=RTEFDODOCENTE
		and RTEFDOJERCODIGO=jerJor
		and ROTDAGJERGRADO=jerGra
		and ROTDAGASIGNATURA=asignatura
		and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=ih;		
		--espacios asignados a las asignaturas-grados
	CURSOR cursor_esp2 IS
		SELECT ROTEAGESPACIO as espacio_
		FROM ROT_ESPACIO_ASIG_GRADO
		where ROTEAGASIGNATURA=asignatura 
		and ROTEAGJERGRADO=grado
		and ROTEAGJERJORNADA=jerJor;
		--espacios asignados a los docentes			
	CURSOR cur_ IS
		SELECT RTEFDOESPACIO as espacio_, RTEFDODOCENTE as espacio_2
		FROM ROT_ESPACIO_DOCENTE,ROT_DOC_ASIG_GRADO
		where RTEFDODOCENTE=doc
		and ROTDAGDOCENTE=RTEFDODOCENTE
		and RTEFDOJERCODIGO=jerJor
		and ROTDAGJERGRADO=jerGra
		and ROTDAGASIGNATURA=asignatura
		and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=ih;
	CURSOR espTodos IS
		SELECT ESPCODIGO FROM ESPACIO_FISICO,SEDE_JORNADA_ESPACIOFISICO
		where ESPCODINS=inst
		and ESPCODSEDE=sede
		and ESPESTADO=1		
		and SEDJORESPCODJOR=jor 
		and SEDJORESPCODINST=ESPCODINS
		and SEDJORESPCODSEDE=ESPCODSEDE
		and SEDJORESPCODESPACIO=ESPCODIGO
		and ESPCODIGO not in(
			SELECT ROTEAGESPACIO FROM ROT_ESPACIO_ASIG_GRADO
			where ROTEAGJERJORNADA=jerJor
			and ROTEAGEXCLUSIVO=2
			and ROTEAGJERGRADO=grado 
		)		
		order by ESPCODIGO;
  BEGIN
	cont:=0;
	band:=0;
	band2:=0;
	tipo:=0;
	espacios:=0;
	i:=0;
	delete from ROT_DOCEF_TEMP where ID=id;
	--traer jer 1- 8	
	SELECT nvl(max(G_JERCODIGO),0) into jerJor
		FROM G_JERARQUIA
		where G_JERTIPO=1
		and G_JERNIVEL=6
		and G_JERINST=inst
		and G_JERSEDE=sede
		and G_JERJORN=jor;
	--traer jer 1- 7	
	SELECT nvl(max(G_JERCODIGO),0) into jerGra
		FROM G_JERARQUIA
		where G_JERTIPO=1
		and G_JERNIVEL=7
		and G_JERINST=inst
		and G_JERSEDE=sede
		and G_JERJORN=jor
		and G_JERMETOD=met
		and G_JERGRADO=grado;
	--para lo de docente 
	if docente>0 then
	   band:=1;
	end if;
    if docente=0 then
	   band:=0;
	end if;   
 	if docente=-1 then
	  band:=-1;
	end if;
	--para lo de espacio fisico 
	cont:=0;
	if espacio>0 then
	   band2:=1;
	end if;
    if espacio=0 then
	   band2:=0;
	   espacios:=3;
	   --ESTO ES LO NUEVO PARA CALCULAR LOS ESPACIOS FISICOS 
	   --if docente=0 or docente=-1 then
	   if docente=0 then
			SELECT count(ESPCODIGO) into cont
			FROM ESPACIO_FISICO,SEDE_JORNADA_ESPACIOFISICO
			where ESPCODINS=inst
			and ESPCODSEDE=sede
			and ESPESTADO=1		
			and SEDJORESPCODJOR=jor 
			and SEDJORESPCODINST=ESPCODINS
			and SEDJORESPCODSEDE=ESPCODSEDE
			and SEDJORESPCODESPACIO=ESPCODIGO
			and ESPCODIGO not in(
				SELECT ROTEAGESPACIO FROM ROT_ESPACIO_ASIG_GRADO
				where ROTEAGJERJORNADA=jerJor
				and ROTEAGEXCLUSIVO=2
				and ROTEAGJERGRADO=grado 
			);		
			if (cont>0) then
			 	band2:=-1;
				espacios:=3;
			else
				espacios:=0;			 	
			end if;	   
	   end if;
	end if;
  	if espacio=-1 then
	 	band2:=-1;
		espacios:=0;
		if docente>0 then
			SELECT count(RTEFDOESPACIO) into cont
			FROM ROT_ESPACIO_DOCENTE,ROT_DOC_ASIG_GRADO
			where RTEFDODOCENTE=docente
			and ROTDAGDOCENTE=RTEFDODOCENTE
			and RTEFDOJERCODIGO=jerJor
			and ROTDAGJERGRADO=jerGra
			and ROTDAGASIGNATURA=asignatura
			and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=ih;
			if cont>1 then
				espacios:=1;
			end if;
		end if;
			if espacios=0 then
				espacios:=2;
			end if;	   
	end if;
	
	--1= -1,-1    
	--2= -1,0  
	--3= -1,>0  
	--4= 0 ,-1  
	--5= 0,0  
	--6= 0 ,>0  
	--7= >0 ,-1  
	--8= >0 ,0  
	--9= >0 ,>0  
	--iterar sobre las 9 POSIBILIDADES  
	i:=0;
	--TIPO UNO 
	if band=-1 and band2=-1 then		
		FOR rec in cursor_doc LOOP
			doc:=rec.docente_;
			FOR rec2 in cur_ LOOP
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,1,rec.docente_,rec2.espacio_,i);
				i:=i+1;
			END LOOP;
	    END LOOP;
		FOR rec in cursor_doc LOOP
		   	if espacios=1 then
			   FOR rec2 in cursor_esp LOOP
			   	   cont:=0;
				   SELECT count(*) into cont FROM ROT_DOCEF_TEMP a
				   where a.ID=id
				   and a.tipo=1 
				   and a.DOCENTE=rec.docente_
				   and a.ESPACIO=rec2.espacio_;
				   if cont=0 then
					   INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,1,rec.docente_,rec2.espacio_,i);
					   i:=i+1;
				   end if;
			    END LOOP;
			end if;	
		   	if espacios=2 then
			   FOR rec2 in cursor_esp2 LOOP
			   	   cont:=0;
				   SELECT count(*) into cont FROM ROT_DOCEF_TEMP a
				   where a.ID=id
				   and a.tipo=1 
				   and a.DOCENTE=rec.docente_
				   and a.ESPACIO=rec2.espacio_;
				   if cont=0 then
					INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,1,rec.docente_,rec2.espacio_,i);
					i:=i+1;
				   end if;
			    END LOOP; 
			end if;	
	    END LOOP; 
	end if;
	
	--TIPO DOS
	if band=-1 and band2=0 then
		FOR rec in cursor_doc LOOP
			if espacios=0 then
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,2,rec.docente_,0,i);
			end if;
			if espacios=3 then
			   FOR rec2 in espTodos LOOP
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,2,rec.docente_,rec2.espcodigo,i);
				i:=i+1;
			   END LOOP;
		   end if;
	    END LOOP;
	end if;
	--TIPO TRES
	if band=-1 and band2>0 then	
		FOR rec in cursor_doc LOOP
			INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN) VALUES (id,3,rec.docente_,espacio,i);
			i:=i+1;
	    END LOOP;
	end if;
	--TIPO CUATRO
	if band=0 and band2=-1 then
	   	if espacios=1 then
		   FOR rec in cursor_esp LOOP
		   	   INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,4,0,rec.espacio_,i);
				i:=i+1;
		   END LOOP;
		end if;	
	   	if espacios=2 then
		   FOR rec in cursor_esp2 LOOP
		   	   INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,4,0,rec.espacio_,i);
				i:=i+1;
		   END LOOP; 
		end if;	
	   	if espacios=3 then
		   FOR rec in espTodos LOOP
		   	   INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,4,0,rec.ESPCODIGO,i);
				i:=i+1;
		   END LOOP; 
		end if;	
	end if;
	--TIPO CINCO
	if band=0 and band2=0 then 
	   INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,5,0,0,i);
	end if;
	--TIPO SEIS
	if band=0 and band2>0 then 
	   INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,6,0,espacio,i);
	end if;
	--TIPO SIETE
	if band>0 and band2=-1 then
	   	if espacios=1 then
			FOR rec in cursor_esp LOOP
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,7,docente,rec.espacio_,i);			
				i:=i+1;
			END LOOP;
		end if;	
	   	if espacios=2 then
			FOR rec in cursor_esp2 LOOP
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES(id,7,docente,rec.espacio_,i);
				i:=i+1;
		    END LOOP;
		end if;	
	end if;
	--TIPO OCHO  
	if band>0 and band2=0 then
	   if espacios=0 then
	   	  INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,8,docente,0,i);
	   end if;
	   if espacios=3 then
		   FOR rec2 in espTodos LOOP
			   INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,8,docente,rec2.espcodigo,i);
				i:=i+1;
		   END LOOP;
	   end if;
	end if;
	--TIPO NUEVE  
	if band=1 and band2=1 then 
	   INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,9,docente,espacio,i);
	end if;
	commit;
	EXCEPTION  
	WHEN others THEN
		close cursor_doc;   
		close cursor_esp;
		close cursor_esp2;
		close cur_;
		close espTodos;
		RAISE_APPLICATION_ERROR (500, 'Error interno getDocEf');
  END getDocEf;
  
END PK_ROTACION;
/
commit;

--select * from user_errors

