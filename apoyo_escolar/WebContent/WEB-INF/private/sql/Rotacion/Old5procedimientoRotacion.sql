/*<TOAD_FILE_CHUNK>*/
CREATE OR REPLACE PACKAGE PK_ROTACION AS
	PROCEDURE getAsignatura(id in NUMERIC,inst in NUMERIC,sede in NUMERIC,jor in NUMERIC,met in NUMERIC,grado in NUMERIC,vigencia in NUMERIC,horas in numeric,indiceGrupo in numeric);
	PROCEDURE getAsignaturaActual(id in NUMERIC,inst in NUMERIC,sede in NUMERIC,jor in NUMERIC,met in NUMERIC,grado in NUMERIC,vigencia in NUMERIC,horas in numeric,asig in numeric,grupo in numeric);
	PROCEDURE getDocEf(id in NUMERIC,inst in NUMERIC,sede in NUMERIC,jor in NUMERIC,met in NUMERIC,grado in NUMERIC,vigencia in NUMERIC,asignatura in numeric,docente in numeric,espacio in numeric,ih in numeric,grupo in numeric);
END PK_ROTACION;
/
/*<TOAD_FILE_CHUNK>*/

CREATE OR REPLACE PACKAGE BODY PK_ROTACION AS
  PROCEDURE getAsignatura(id in NUMERIC,inst in NUMERIC,sede in NUMERIC,jor in NUMERIC,met in NUMERIC,grado in NUMERIC,vigencia in NUMERIC,horas in numeric,indiceGrupo in numeric) IS
	asig number(6);
	ih number(3);
	cat number(3);
	doc number(12);
	esp number(12);
	jerGra number(12);
	jerJor number(12);
	cont number(3);
	orden number(3);
	indiceCat5 number(3);
	indiceCat45 number(3);
	indiceCat4 number(3);
	indiceCat35 number(3);
	indiceCat3 number(3);
	indiceCat25 number(3);
	indiceCat2 number(3);
	indiceCat15 number(3);
	indiceCat1 number(3);
	indiceCat0 number(3);

	totalCat5 number(3);
	totalCat45 number(3);
	totalCat4 number(3);
	totalCat35 number(3);
	totalCat3 number(3);
	totalCat25 number(3);
	totalCat2 number(3);
	totalCat15 number(3);
	totalCat1 number(3);
	totalCat0 number(3);

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
			order by nvl(ASIPRIORIDAD,0) desc,GRAASIIH desc,GRAASICODASIGN asc;
  BEGIN
  	doc:=0;
	esp:=0;
	cont:=0;
	orden:=0;
	indiceCat5:=0;
	indiceCat4:=0;
	indiceCat3:=0;
	indiceCat2:=0;
	indiceCat1:=0;
	indiceCat45:=0;
	indiceCat35:=0;
	indiceCat25:=0;
	indiceCat15:=0;
	indiceCat0:=0;

	totalCat5:=0;
	totalCat4:=0;
	totalCat3:=0;
	totalCat2:=0;
	totalCat1:=0;
	totalCat45:=0;
	totalCat35:=0;
	totalCat25:=0;
	totalCat15:=0;
	totalCat0:=0;
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
		
	--abrir cursor para calcular el total por categoria
	FOR rec in cursor_asig LOOP
		cat:=0;
		if (rec.ih_<=0) then
			rec.ih_:=1;
		end if;
		--cat:=round(rec.ih_/horas); 
		if (rec.ih_=10) then 
		   cat:=5; 
		   totalCat5:=totalCat5+1;
		end if;
		if (rec.ih_=9) then 
		   cat:=45; 
		   totalCat45:=totalCat45+1;
		end if;
		if (rec.ih_=8) then 
		   cat:=4; 
		   totalCat4:=totalCat4+1;
		end if;
		if (rec.ih_=7) then 
		   cat:=35; 
		   totalCat35:=totalCat35+1;
		end if;
		if (rec.ih_=6) then 
		   cat:=3;
		   totalCat3:=totalCat3+1; 
		end if;
		if (rec.ih_=5) then 
		   cat:=25; 
		   totalCat25:=totalCat25+1;
		end if;
		if (rec.ih_=4) then 
		   cat:=2; 
		   totalCat2:=totalCat2+1;
		end if;
		if (rec.ih_=3) then 
		   cat:=15; 
		   totalCat15:=totalCat15+1;
		end if;
		if (rec.ih_=2) then 
		   cat:=1; 
		   totalCat1:=totalCat1+1;
		end if;
		if (rec.ih_=1) then 
		   cat:=0; 
		   totalCat0:=totalCat0+1;
		end if; 
	END LOOP;
  
	FOR rec in cursor_asig LOOP	
	  	doc:=0;
		esp:=0;
		cont:=0;
		cat:=0;
		bloque:=0;
		--calcula categoria  
		if (rec.ih_<=0) then
			rec.ih_:=1;
		end if;
		if (rec.ih_=10) then 
		   cat:=5; 
		end if;
		if (rec.ih_=9) then 
		   cat:=45; 
		end if;
		if (rec.ih_=8) then 
		   cat:=4; 
		end if;
		if (rec.ih_=7) then 
		   cat:=35; 
		end if;
		if (rec.ih_=6) then 
		   cat:=3;
		end if;
		if (rec.ih_=5) then 
		   cat:=25; 
		end if;
		if (rec.ih_=4) then 
		   cat:=2; 
		end if;
		if (rec.ih_=3) then 
		   cat:=15; 
		end if;
		if (rec.ih_=2) then 
		   cat:=1; 
		end if;
		if (rec.ih_=1) then 
		   cat:=0; 
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

		--poner orden de acuerdo a la categoria  
		if(cat=0) then 
				  indiceCat0:=indiceCat0+1;
				  if ((indiceCat0+indiceGrupo)>totalCat0) then
				  	 orden:=indiceCat0+indiceGrupo-totalCat0;
				  else
				  	 orden:=indiceCat0+indiceGrupo;	  	 
				  end if;
		end if;			
		if(cat=1) then 
				  indiceCat1:=indiceCat1+1;
				  if ((indiceCat1+indiceGrupo)>totalCat1) then
				  	 orden:=indiceCat1+indiceGrupo-totalCat1;
				  else
				  	 orden:=indiceCat1+indiceGrupo;	  	 
				  end if;
		end if;			
		if(cat=15) then 
				  indiceCat15:=indiceCat15+1;
				  if ((indiceCat15+indiceGrupo)>totalCat15) then
				  	 orden:=indiceCat15+indiceGrupo-totalCat15;
				  else
				  	 orden:=indiceCat15+indiceGrupo;	  	 
				  end if;
		end if;			
		if(cat=2) then 
				  indiceCat2:=indiceCat2+1; 
				  if ((indiceCat2+indiceGrupo)>totalCat2) then
				  	 orden:=indiceCat2+indiceGrupo-totalCat2;
				  else
				  	 orden:=indiceCat2+indiceGrupo;	  	 
				  end if;
		end if;			
		if(cat=25) then 
				  indiceCat25:=indiceCat25+1; 
				  if ((indiceCat25+indiceGrupo)>totalCat25) then
				  	 orden:=indiceCat25+indiceGrupo-totalCat25;
				  else
				  	 orden:=indiceCat25+indiceGrupo;
				  end if;
		end if;			
		if(cat=3) then 
				  indiceCat3:=indiceCat3+1; 
				  if ((indiceCat3+indiceGrupo)>totalCat3) then
				  	 orden:=indiceCat3+indiceGrupo-totalCat3;
				  else
				  	 orden:=indiceCat3+indiceGrupo;	  	 
				  end if;
		end if;			
		if(cat=35) then 
				  indiceCat35:=indiceCat35+1; 
				  if ((indiceCat35+indiceGrupo)>totalCat35) then
				  	 orden:=indiceCat35+indiceGrupo-totalCat35;
				  else
				  	 orden:=indiceCat35+indiceGrupo;	  	 
				  end if;
		end if;			
		if(cat=4) then 
				  indiceCat4:=indiceCat4+1; 
				  if ((indiceCat4+indiceGrupo)>totalCat4) then
				  	 orden:=indiceCat4+indiceGrupo-totalCat4;
				  else
				  	 orden:=indiceCat4+indiceGrupo;	  	 
				  end if;
		end if;			
		if(cat=45) then 
				  indiceCat45:=indiceCat45+1; 
				  if ((indiceCat45+indiceGrupo)>totalCat45) then
				  	 orden:=indiceCat45+indiceGrupo-totalCat45;
				  else
				  	 orden:=indiceCat45+indiceGrupo;	  	 
				  end if;
		end if;			
		if(cat=5) then 
				  indiceCat5:=indiceCat5+1; 
				  if ((indiceCat5+indiceGrupo)>totalCat5) then
				  	 orden:=indiceCat5+indiceGrupo-totalCat5;
				  else
				  	 orden:=indiceCat5+indiceGrupo;	  	 
				  end if;
		end if;			
		INSERT INTO ROT_ASIG_TEMP (ID, ASIGNATURA, CATEGORIA, DOCENTE, ESPACIO,IH,BLOQUE,ORDEN) VALUES (id,rec.asig_,cat,doc,esp,rec.ih_,bloque,orden);
	END LOOP;
	commit;
	EXCEPTION
	WHEN others THEN
		close cursor_asig;
		RAISE_APPLICATION_ERROR (500, 'Error interno getAsignatura');
	END getAsignatura;

  PROCEDURE getAsignaturaActual(id in NUMERIC,inst in NUMERIC,sede in NUMERIC,jor in NUMERIC,met in NUMERIC,grado in NUMERIC,vigencia in NUMERIC,horas in numeric,asig in numeric,grupo in numeric) IS
	ih number(3);
	cat number(3);
	doc number(12);
	esp number(12);
	jerGra number(12);
	jerJor number(12);
	cont number(3);
	cont2 number(3);
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
		order by nvl(ASIPRIORIDAD,0) desc,GRAASIIH desc,GRAASICODASIGN asc;
		--odenar por prioridad  desendente y luego ordenar por ih  
  BEGIN
  	doc:=0;
	esp:=0;
	cont:=0;
	cont2:=0;
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
		if (rec.ih_<=0) then
			rec.ih_:=1;
		end if;
		if (rec.ih_=10) then 
		   cat:=5; 
		end if;
		if (rec.ih_=9) then 
		   cat:=45; 
		end if;
		if (rec.ih_=8) then 
		   cat:=4; 
		end if;
		if (rec.ih_=7) then 
		   cat:=35; 
		end if;
		if (rec.ih_=6) then 
		   cat:=3;
		end if;
		if (rec.ih_=5) then 
		   cat:=25; 
		end if;
		if (rec.ih_=4) then 
		   cat:=2; 
		end if;
		if (rec.ih_=3) then 
		   cat:=15; 
		end if;
		if (rec.ih_=2) then 
		   cat:=1; 
		end if;
		if (rec.ih_=1) then 
		   cat:=0; 
		end if; 
		--calcula docente---------------------------------------------------------------------------
			--PRIMERO SABER SI HAY ALGO EN DOC-ASIG-GRA-GRU 
			--SI NO TIENE ENTONCES CONSULTAR DOC-ASIG-GRA   
			SELECT count(ROTDAGGDOCENTE) into cont
			FROM ROT_DOC_ASIG_GRADO_GRUPO,ROT_DOC_ASIG_GRADO
			where ROTDAGGJERGRADO=jerGra 
			and ROTDAGGASIGNATURA=rec.asig_
			and ROTDAGJERGRADO=ROTDAGGJERGRADO 
			and ROTDAGASIGNATURA=ROTDAGGASIGNATURA
			AND ROTDAGGDOCENTE=ROTDAGDOCENTE
			AND ROTDAGGGRUPO=grupo
			and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=rec.ih_;
			
			if cont>0 then --tiene al menos un docente asociado 
				if cont=1 then
					SELECT ROTDAGGDOCENTE into doc
					FROM ROT_DOC_ASIG_GRADO_GRUPO,ROT_DOC_ASIG_GRADO
					where ROTDAGGJERGRADO=jerGra 
					and ROTDAGGASIGNATURA=rec.asig_
					and ROTDAGJERGRADO=ROTDAGGJERGRADO 
					and ROTDAGASIGNATURA=ROTDAGGASIGNATURA
					AND ROTDAGGDOCENTE=ROTDAGDOCENTE
					AND ROTDAGGGRUPO=grupo
					and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=rec.ih_;
				else
					doc:=-11;
				end if;
			else -- se usan todos los docentes de carga academica 
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
			end if;
				
			--calcular espacio --------------------------------------------------------------------    
			cont:=0;
			if doc>0 then  --hay docente
			   --BUSCARLO EN DOCENTES POR ESPACIO    		
				SELECT count(RTEFDOESPACIO) into cont
				FROM ROT_ESPACIO_DOCENTE
				where RTEFDODOCENTE=doc
				and RTEFDOJERCODIGO=jerJor;
				if cont=0 then
				   esp:=0;
				else
					--revisar esp- asig-grado antes
					SELECT count(ROTEAGESPACIO) into cont2
					FROM ROT_ESPACIO_ASIG_GRADO
					where ROTEAGASIGNATURA=rec.asig_ 
					and ROTEAGJERGRADO=grado
					and ROTEAGJERJORNADA=jerJor;
					if cont2>0 then
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
			end if;
			if esp=0 then
			--BUSCARLO PRIMERO EN ESP-ASIG-GRADO  
				SELECT count(ROTEAGESPACIO) into cont
				FROM ROT_ESPACIO_ASIG_GRADO
				where ROTEAGASIGNATURA=rec.asig_ 
				and ROTEAGJERGRADO=grado
				and ROTEAGJERJORNADA=jerJor;
				if cont>0 then  --TIENE ESPACIO PARA ESA ASIGNATURA GRADO 
					if cont=1 then
						SELECT max(ROTEAGESPACIO) into esp
						FROM ROT_ESPACIO_ASIG_GRADO
						where ROTEAGASIGNATURA=rec.asig_ 
						and ROTEAGJERGRADO=grado
						and ROTEAGJERJORNADA=jerJor;					
					else
						esp:=-1;
					end if;
				else
					--NO TIENE ESP ASIG GRADO ENOTNCES BUSCA ESPACIOS PARA ESE GRADO 
					SELECT count(ROTESPGRAESPACIO) into cont
					FROM ROT_ESPACIO_GRADO					
					where ROTESPGRAJERGRADO=jerGra;
					if cont>0 then 
						if cont=1 then
							SELECT max(ROTESPGRAESPACIO) into esp
							FROM ROT_ESPACIO_GRADO					
							where ROTESPGRAJERGRADO=jerGra;
						else
							esp:=-11;
						end if;
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
		if grado=7 and grupo=4 and asig=501 then 
		INSERT INTO TEMP (ID, ID2, ID3) VALUES (cat,doc,esp);
		end if;
		INSERT INTO ROT_ASIG_TEMP (ID, ASIGNATURA, CATEGORIA, DOCENTE, ESPACIO,IH,ORDEN,BLOQUE) VALUES (id,rec.asig_,cat,doc,esp,rec.ih_,i,bloque);
		i:=i+1;		
	END LOOP;
	commit;
	EXCEPTION
	WHEN others THEN     
		close cursor_asig;
		RAISE_APPLICATION_ERROR (500, 'Error interno getAsignaturaActual');
	END getAsignaturaActual;

  PROCEDURE getDocEf(id in NUMERIC,inst in NUMERIC,sede in NUMERIC,jor in NUMERIC,met in NUMERIC,grado in NUMERIC,vigencia in NUMERIC,asignatura in numeric,docente in numeric,espacio in numeric,ih in numeric,grupo in numeric) IS
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
	--carga academica de docentes por grado    
	CURSOR cursor_doc IS
		SELECT ROTDAGDOCENTE as docente_
		from ROT_DOC_ASIG_GRADO
		where ROTDAGJERGRADO=jerGra 
		and ROTDAGASIGNATURA=asignatura
		and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=ih;
	--carga academica de docentes por grado-grupo     
	CURSOR cursor_docGru IS
		SELECT ROTDAGGDOCENTE as docente_
		from ROT_DOC_ASIG_GRADO_GRUPO,ROT_DOC_ASIG_GRADO
		where ROTDAGJERGRADO=jerGra 
		and ROTDAGASIGNATURA=asignatura
		and ROTDAGJERGRADO=ROTDAGGJERGRADO 
		and ROTDAGASIGNATURA=ROTDAGGASIGNATURA
		AND ROTDAGGDOCENTE=ROTDAGDOCENTE
		AND ROTDAGGGRUPO=grupo
		and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=ih;
	--espacios asignados a un docente-asignatura-grado     
	CURSOR cursor_esp IS
		SELECT RTEFDOESPACIO as espacio_
		FROM ROT_ESPACIO_DOCENTE,ROT_DOC_ASIG_GRADO
		where RTEFDODOCENTE=docente
		and ROTDAGDOCENTE=RTEFDODOCENTE
		and RTEFDOJERCODIGO=jerJor
		and ROTDAGJERGRADO=jerGra
		and ROTDAGASIGNATURA=asignatura
		and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=ih;		
	--espacios asignados a un docente-asignatura-grado-grupo      
	CURSOR cursor_espGru IS
		SELECT RTEFDOESPACIO as espacio_
		FROM ROT_ESPACIO_DOCENTE,ROT_DOC_ASIG_GRADO,ROT_DOC_ASIG_GRADO_GRUPO
		where RTEFDODOCENTE=docente
		and ROTDAGDOCENTE=RTEFDODOCENTE
		and RTEFDOJERCODIGO=jerJor
		and ROTDAGJERGRADO=jerGra
		and ROTDAGASIGNATURA=asignatura
		and ROTDAGJERGRADO=ROTDAGGJERGRADO 
		and ROTDAGASIGNATURA=ROTDAGGASIGNATURA
		and ROTDAGGDOCENTE=ROTDAGDOCENTE
		AND ROTDAGGGRUPO=grupo
		and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=ih;		
	--espacios asignados a las asignaturas-grados    
	CURSOR cursor_esp2 IS
		SELECT ROTEAGESPACIO as espacio_
		FROM ROT_ESPACIO_ASIG_GRADO
		where ROTEAGASIGNATURA=asignatura 
		and ROTEAGJERGRADO=grado
		and ROTEAGJERJORNADA=jerJor;
	--espacios asignados a los grados  
	CURSOR cursor_esp3 IS
		SELECT ROTESPGRAESPACIO as espacio_ 
		FROM ROT_ESPACIO_GRADO
		where ROTESPGRAJERGRADO=jerGra;	
	--espacios asignados a los docentes	por grado asig 		
	CURSOR cur_ IS
		SELECT RTEFDOESPACIO as espacio_, RTEFDODOCENTE as espacio_2
		FROM ROT_ESPACIO_DOCENTE,ROT_DOC_ASIG_GRADO
		where RTEFDODOCENTE=doc
		and ROTDAGDOCENTE=RTEFDODOCENTE
		and RTEFDOJERCODIGO=jerJor
		and ROTDAGJERGRADO=jerGra
		and ROTDAGASIGNATURA=asignatura
		and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=ih;
	--espacios asignados a los docentes	por grado grupo asig 		
	CURSOR cur_Grupo IS
		SELECT RTEFDOESPACIO as espacio_, RTEFDODOCENTE as espacio_2
		FROM ROT_ESPACIO_DOCENTE,ROT_DOC_ASIG_GRADO,ROT_DOC_ASIG_GRADO_GRUPO
		where RTEFDODOCENTE=doc
		and ROTDAGDOCENTE=RTEFDODOCENTE
		and RTEFDOJERCODIGO=jerJor
		and ROTDAGJERGRADO=jerGra
		and ROTDAGASIGNATURA=asignatura
		and ROTDAGGJERGRADO=ROTDAGJERGRADO
		and ROTDAGGDOCENTE=RTEFDODOCENTE
		and ROTDAGGASIGNATURA=ROTDAGASIGNATURA
		and ROTDAGGGRUPO=grupo
		and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=ih;
		--todos los espacios fisicos   
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
		
	--para lo de docente----------------------------------------------------------   
	--CASO QUE YA HAY DOCENTE  
	if docente>0 then
	   band:=1;
	end if;
	--CASO QUE NO HAY DOCENTE  
    if docente=0 then
	   band:=0;
	end if;   
	--CASO DONDE HAY MAS DE UN DOCENTE POR GRADO-ASIGNATURA    
 	if docente=-1 then
	  band:=-1;
	end if;
	--CASO DONDE HAY MAS DE UN DOCENTE POR GRADO-ASIGNATURA-GRUPO    
 	if docente=-11 then
	  band:=-11;
	end if;
	
	--para lo de espacio fisico------------------------------------------------------
	  
	--CASO EN EL QUE YA HAY ESPACIO FISICO  
	if espacio>0 then
	   band2:=1;
	end if;
	--CASO EN EL QUE NO HAY ESPACIO  
    if espacio=0 then
	   band2:=0;
	   espacios:=3;--todos los espacios  
	   --CALCULAR LOS ESPACIOS FISICOS SI NO HAY DOCENTE  
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
				espacios:=3;--todos los espacios 
			else
				espacios:=0;--ningun espacio 		 	
			end if;	   
	   end if;
	end if;
	--CASO EN EL QUE HAY MUCHOS ESPACIOS POR DOCENTE O POR GRADO-ASIGNATURA    
  	if espacio=-1 then
	 	band2:=-1;
		espacios:=0;--ningun espacio 
		if docente>0 then --hay docente por defecto   
		   --BUSCAR ESPACIOS DEL DOCENTE-ASIGNATURA-GRADO    
			SELECT count(RTEFDOESPACIO) into cont
			FROM ROT_ESPACIO_DOCENTE,ROT_DOC_ASIG_GRADO
			where RTEFDODOCENTE=docente
			and ROTDAGDOCENTE=RTEFDODOCENTE
			and RTEFDOJERCODIGO=jerJor
			and ROTDAGJERGRADO=jerGra
			and ROTDAGASIGNATURA=asignatura
			and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=ih;
			if cont>=1 then --hay espacios para ese docente 
				espacios:=1;--traer esp x doc-asig-gra  
			end if;
		end if;
		if espacios=0 then 
			espacios:=2; --traer espacios X asignatura-grado 
		end if;	   
	end if;
	--CASO EN EL QUE HAY MUCHOS ESPACIOS POR GRADO 
  	if espacio=-11 then
	 	band2:=-11;
		espacios:=0;--ningun espacio 
		if docente>0 then --hay docente por defecto  
		   --BUSCAR ESPACIOS DEL DOCENTE ASIG-GRADO-GRUPO     
			SELECT count(RTEFDOESPACIO) into cont
			FROM ROT_ESPACIO_DOCENTE,ROT_DOC_ASIG_GRADO,ROT_DOC_ASIG_GRADO_GRUPO
			where RTEFDODOCENTE=docente
			and ROTDAGDOCENTE=RTEFDODOCENTE
			and RTEFDOJERCODIGO=jerJor
			and ROTDAGJERGRADO=jerGra
			and ROTDAGASIGNATURA=asignatura
			and ROTDAGGJERGRADO=ROTDAGJERGRADO
			and ROTDAGGDOCENTE=RTEFDODOCENTE
			and ROTDAGGASIGNATURA=ROTDAGASIGNATURA
			and ROTDAGGGRUPO=grupo
			and (nvl(ROTDAGIHTOTAL,0)- nvl(ROTDAGIHREAL,0)-nvl(ROTDAGIHPROP,0))>=ih;
			if cont>=1 then --hay espacios para ese docente 
				espacios:=11;--traer esp x doc-asig-gra  
			end if;
		end if;
		if espacios=0 then
			espacios:=12; --traer espacios por grado  
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
	
	--10 = -11, -11  (SE PONE TIPO 1)   
	--11 = -11, 0    (SE PONE TIPO 2) 
	--12 = -11, >0   (SE PONE TIPO 3) 
	--13 = 0, -11    (SE PONE TIPO 4)  
	--14 = >0, -11   (SE PONE TIPO 7)     
	--iterar sobre las 9 POSIBILIDADES  
	i:=0;
	--TIPO UNO 
	if band=-1 and band2=-1 then		
		FOR rec in cursor_doc LOOP  --DOC x ASIG-GRA   
			doc:=rec.docente_;
			FOR rec2 in cur_ LOOP --ESP x DOC-ASIG - GRADO donde DOC es del for anterior 
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,1,rec.docente_,rec2.espacio_,i);
				i:=i+1;
			END LOOP;
	    END LOOP;
		FOR rec in cursor_doc LOOP --DOC x ASIG-GRA   
		   	if espacios=1 then
			   FOR rec2 in cursor_esp LOOP--ESP x DOC-ASIG-GRA   
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
			   FOR rec2 in cursor_esp2 LOOP--ESP x ASIG-GRADO 
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
	
	if band=-11 and band2=-11 then		
		FOR rec in cursor_docGru LOOP  --DOC x ASIG-GRA   
			doc:=rec.docente_;
			FOR rec2 in cur_Grupo LOOP --ESP x DOC-ASIG - GRADO donde DOC es del for anterior 
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,1,rec.docente_,rec2.espacio_,i);
				i:=i+1;
			END LOOP;
	    END LOOP;
		FOR rec in cursor_docGru LOOP --DOC x ASIG-GRA 
		   	if espacios=1 then
			   FOR rec2 in cursor_esp LOOP--ESP x DOC-ASIG-GRA   
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
		   	if espacios=11 then
			   FOR rec2 in cursor_espGru LOOP--ESP x DOC-ASIG-GRA   
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
			   FOR rec2 in cursor_esp2 LOOP--ESP x ASIG-GRADO 
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
		   	if espacios=12 then
			   FOR rec2 in cursor_esp3 LOOP--ESP x GRADO   
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
	
	if band=-1 and band2=-11 then		
		FOR rec in cursor_doc LOOP  --DOC x ASIG-GRA   
			doc:=rec.docente_;
			FOR rec2 in cur_Grupo LOOP --ESP x DOC-ASIG - GRADO donde DOC es del for anterior 
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,1,rec.docente_,rec2.espacio_,i);
				i:=i+1;
			END LOOP;
	    END LOOP;
		FOR rec in cursor_doc LOOP --DOC x ASIG-GRA   
		   	if espacios=1 then
			   FOR rec2 in cursor_esp LOOP--ESP x DOC-ASIG-GRA   
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
		   	if espacios=11 then
			   FOR rec2 in cursor_espGru LOOP--ESP x DOC-ASIG-GRA   
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
			   FOR rec2 in cursor_esp2 LOOP--ESP x ASIG-GRADO 
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
		   	if espacios=12 then
			   FOR rec2 in cursor_esp3 LOOP--ESP x GRADO   
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
		FOR rec in cursor_doc LOOP --DOC x ASIG-GRA 
			if espacios=0 then
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,2,rec.docente_,0,i);
			end if;
			if espacios=3 then
			   FOR rec2 in espTodos LOOP --todos los esp de la SED-JOR  
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,2,rec.docente_,rec2.espcodigo,i);
				i:=i+1;
			   END LOOP;
		   end if;
	    END LOOP;
	end if;
	if band=-11 and band2=0 then
		FOR rec in cursor_docGru LOOP --DOC x ASIG-GRA 
			if espacios=0 then
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,2,rec.docente_,0,i);
			end if;
			if espacios=3 then
			   FOR rec2 in espTodos LOOP --todos los esp de la SED-JOR  
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,2,rec.docente_,rec2.espcodigo,i);
				i:=i+1;
			   END LOOP;
		   end if;
	    END LOOP;
	end if;
	--TIPO TRES  
	if band=-1 and band2>0 then	
		FOR rec in cursor_doc LOOP --DOC x ASIG-GRA  
			INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN) VALUES (id,3,rec.docente_,espacio,i);
			i:=i+1;
	    END LOOP;
	end if;

	if band=-11 and band2>0 then	
		FOR rec in cursor_docGru LOOP --DOC x ASIG-GRA  
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
		   FOR rec in espTodos LOOP  --todos los espacios de la SED-JOR 
		   	   INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,4,0,rec.ESPCODIGO,i);
				i:=i+1;
		   END LOOP; 
		end if;	
	end if;
	
	if band=0 and band2=-11 then
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
		   FOR rec in espTodos LOOP  --todos los espacios de la SED-JOR 
		   	   INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,4,0,rec.ESPCODIGO,i);
				i:=i+1;
		   END LOOP; 
		end if;	
	   	if espacios=11 then
		   FOR rec in cursor_espGru LOOP
		   	   INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,4,0,rec.espacio_,i);
				i:=i+1;
		   END LOOP;
		end if;	
	   	if espacios=12 then
		   FOR rec in cursor_esp3 LOOP
		   	   INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,4,0,rec.espacio_,i);
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

	if band>0 and band2=-11 then
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
	   	if espacios=11 then
			FOR rec in cursor_espGru LOOP
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,7,docente,rec.espacio_,i);			
				i:=i+1;
			END LOOP;
		end if;	
	   	if espacios=12 then
			FOR rec in cursor_esp3 LOOP
				INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES(id,7,docente,rec.espacio_,i);
				i:=i+1;
		    END LOOP;
		end if;	
	end if;
	--TIPO OCHO  
	if band>0 and band2=0 then
	   if espacios=0 then --no hay espacio 
	   	  INSERT INTO ROT_DOCEF_TEMP(ID,TIPO,DOCENTE,ESPACIO,ORDEN)VALUES (id,8,docente,0,i);
	   end if;
	   if espacios=3 then --todos los espacios de 
		   FOR rec2 in espTodos LOOP --ESP x SED-JOR   
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
/*<TOAD_FILE_CHUNK>*/
commit;

--select * from user_errors

